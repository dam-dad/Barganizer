package dad.barganizer.gui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import dad.barganizer.App;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.TipoPlato;
import dad.barganizer.gui.models.AddPlatoModel;
import dad.barganizer.thread.HiloEjecutador;
import dad.barganizer.validators.DoubleValidator;
import dad.barganizer.validators.NombrePlatoValidator;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;

public class AddPlatoController implements Initializable {

	// Model
	private AddPlatoModel model = new AddPlatoModel();

	@FXML
	private Button addPlatoButton;

	@FXML
	private Button cambiarFotoButton;

	@FXML
	private Button cancelButton;

	@FXML
	private ComboBox<Carta> cartaCombo;

	@FXML
	private ImageView imgPlatoView;

	@FXML
	private TextField nombrePlatoText;

	@FXML
	private TextField precioText;

	@FXML
	private ComboBox<TipoPlato> tipoCombo;

	@FXML
	private GridPane view;

	public AddPlatoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddPlatoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nombrePlatoText.textProperty().bindBidirectional(model.nombreProperty());
//		precioText.textProperty().bindBidirectional(model.precioProperty(), new NumberStringConverter());
		model.precioProperty().bind(model.precioValueProperty());
		model.cartaProperty().bind(cartaCombo.valueProperty());
		model.tipoProperty().bind(tipoCombo.valueProperty());
		imgPlatoView.imageProperty().bindBidirectional(model.fotoProperty());
		tipoCombo.itemsProperty().bind(model.listaTiposProperty());
		cartaCombo.itemsProperty().bind(model.listaCartasProperty());
		
		
		model.cartaSeleccionadaProperty().addListener((o, ov, nv) -> {
			System.out.println("ADDPLATOCONTROLLER CAMBIO --- OV: "+ ov + "---NV: " + nv);
			if (nv != null) {
				cartaCombo.getSelectionModel().select(nv);
			}
		});
		
		
		tipoCombo.itemsProperty().addListener((o, ov, nv) -> {
			tipoCombo.getSelectionModel().select(0);
		});
		
		ValidationSupport support = new ValidationSupport();
        support.registerValidator(precioText, true, new DoubleValidator());
        support.registerValidator(nombrePlatoText, true, new NombrePlatoValidator());
        addPlatoButton.disableProperty().bind(support.invalidProperty());
	}

	@FXML
	void onAddPlatoAction(ActionEvent event) {

		insertarPlatoTask.setOnSucceeded(e -> {
			App.info("Éxito", "Plato insertado", "Se ha insertado el plato correctamente");

			Stage stg = (Stage) addPlatoButton.getScene().getWindow();
			stg.close();
		});

		insertarPlatoTask.setOnFailed(e -> {
			App.error("Error", "Inserción de plato", "Se produjo un error durante la inserción de platos. Detalles: "
					+ e.getSource().getException().getMessage());
		});
		
		

		new HiloEjecutador(App.semaforo, insertarPlatoTask).start();

	}

	@FXML
	void onCambiarFotoAction(ActionEvent event) {
		try {
			Stage stageChoser = new Stage();
			FileChooser fileChoser = new FileChooser();
			stageChoser.initOwner(App.primaryStage);
			fileChoser.setTitle("Abrir agenda...");
			fileChoser.getExtensionFilters().addAll(new ExtensionFilter("Todos los archivos", "*.*"),
					new ExtensionFilter("Todos las imágenes", "*.jpg, *.png, *.bmp"));

			File imagen = fileChoser.showOpenDialog(stageChoser);
			
			if (imagen != null) {
				FileInputStream fis = new FileInputStream(imagen);
				if (fis != null) {
					model.setBytesFoto(fis.readAllBytes());
					fis.close();
					model.setFoto(new Image(imagen.toURI().toURL().toExternalForm()));
				}

			} else {
				InputStream is = getClass().getResourceAsStream("/images/platounknown.png");
				model.setBytesFoto(is.readAllBytes());
				model.setFoto(new Image(getClass().getResourceAsStream("/images/platounknown.png")));
			}

		} catch (FileNotFoundException e) {
			App.error("Error", "Error cambiando la imagen",
					"Se ha producido un error cambiando la imagen. Detalles: " + e.getMessage());

		} catch (IOException e) {
			App.error("Error", "Error cambiando la imagen",
					"Se ha producido un error cambiando la imagen. Detalles: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	void onCancelAction(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}



	public GridPane getView() {
		return view;
	}

	private Task<Void> insertarPlatoTask = new Task<Void>() {

		@Override
		protected Void call() throws Exception {
			FuncionesDB.insertarPlato(App.getBARGANIZERDB().getSes(), model.getNombre(), model.getTipo(),
					Double.parseDouble(precioText.getText()), model.getBytesFoto(), model.getCarta());

			return null;
		}
	};

	public AddPlatoModel getModel() {
		return model;
	}
	
	public ComboBox<Carta> getCartaCombo() {
		return cartaCombo;
	}
}
