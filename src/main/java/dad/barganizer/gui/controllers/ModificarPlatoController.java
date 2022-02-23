package dad.barganizer.gui.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;

import dad.barganizer.App;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.db.beans.TipoPlato;
import dad.barganizer.gui.models.ModificarPlatoModel;
import dad.barganizer.thread.HiloEjecutador;
import dad.barganizer.validators.DoubleValidator;
import dad.barganizer.validators.NombrePlatoValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ModificarPlatoController implements Initializable {

	private ModificarPlatoModel model = new ModificarPlatoModel();

	@FXML
	private Button cancelButton;

	@FXML
	private ComboBox<Carta> cartaCombo;

	@FXML
	private ImageView imgPlatoView;

	@FXML
	private Button modificarButton;

	@FXML
	private TextField nombrePlatoText;

	@FXML
	private TextField precioText;

	@FXML
	private ComboBox<TipoPlato> tipoCombo;

	@FXML
	private Button cambiarFotoButton;

	@FXML
	private GridPane view;

	public ModificarPlatoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarPlatoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		model.platoModificarProperty().addListener((o, ov, nv) -> {
			bindAll();

		});

		nombrePlatoText.textProperty().addListener((o, ov, nv) -> {
			System.out.println("Textfield --- OV: " + ov + "--- NV: " + nv);
		});
		
		cartaCombo.itemsProperty().addListener((o, ov, nv) -> {
			System.out.println("CartaCOMBO - - - OV: " + ov + "--- NV: " + nv);
		});

		
		
		tipoCombo.itemsProperty().bind(model.listaTiposProperty());
		cartaCombo.itemsProperty().bind(model.listaCartasProperty());

		
		
		ValidationSupport support = new ValidationSupport();
        support.registerValidator(precioText, true, new DoubleValidator());
        support.registerValidator(nombrePlatoText, true, new NombrePlatoValidator());
        modificarButton.disableProperty().bind(support.invalidProperty());
		
		onActualizarListaAction(null);
	}

	public GridPane getView() {
		return view;
	}

	public ModificarPlatoModel getModel() {
		return model;
	}

	private void bindAll() {
		nombrePlatoText.textProperty().bindBidirectional(model.getPlatoModificar().nombreProperty());

		precioText.setText(""+model.getPlatoModificar().getPrecio());
//		precioText.textProperty().bindBidirectional(model.getPlatoModificar().precioProperty(),
//				new NumberStringConverter());
		
		cartaCombo.valueProperty().bindBidirectional(model.getPlatoModificar().getCarta().referenciaProperty());
		tipoCombo.valueProperty().bindBidirectional(model.getPlatoModificar().tipoProperty());
		imgPlatoView.imageProperty().bindBidirectional(model.getPlatoModificar().fotoProperty());

	}


	@FXML
	void onCancelAction(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
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
					model.getPlatoModificar().setBytesfoto(fis.readAllBytes());
					fis.close();
					model.getPlatoModificar().setFoto(new Image(imagen.toURI().toURL().toExternalForm()));
				}
			} else {
				InputStream is = getClass().getResourceAsStream("/images/platounknown.png");
				model.getPlatoModificar().setBytesfoto(is.readAllBytes());
				model.getPlatoModificar().setFoto(new Image(getClass().getResourceAsStream("/images/platounknown.png")));
			}

		} catch (FileNotFoundException e) {
			App.error("Error", "Error cambiando la imagen",
					"Se ha producido un error cambiando la imagen. Detalles: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			App.error("Error", "Error cambiando la imagen",
					"Se ha producido un error cambiando la imagen. Detalles: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private Task<Void> actualizarPlatoTask = new Task<Void>() {

		@Override
		protected Void call() throws Exception {
			Plato nuevo = model.getPlatoModificar().getReferencia();
			nuevo.setNombre(model.getPlatoModificar().getNombre());
			
			nuevo.setFoto(model.getPlatoModificar().getBytesfoto());
			nuevo.setTipoPlato(model.getPlatoModificar().getTipo());
			nuevo.setPrecio(Double.parseDouble(precioText.getText()));
			
			nuevo.setCarta(cartaCombo.getSelectionModel().getSelectedItem());
			
			System.out.println("ActualizarPlatoTask: " + nuevo);
			System.out.println("Index cartacombo:" + cartaCombo.getSelectionModel().getSelectedIndex());
			FuncionesDB.modificarPlato(App.getBARGANIZERDB().getSes(), nuevo);
			
			return null;
		}
	};

	@FXML
	void onModificarPlatoAction(ActionEvent event) {

		actualizarPlatoTask.setOnSucceeded(e -> {
			actualizarPlatoTask.getValue();
			System.out.println("Plato actualizado");
			App.info("Actualizado", "Plato actualizado", "El plato ha sido actualizado satisfactoriamente");
			
			Stage stage = (Stage) modificarButton.getScene().getWindow();
			stage.close();
//			onActualizarListaAction(null);

		});

		actualizarPlatoTask.setOnFailed(e -> {
			App.error("Error", "Error actualizando plato",
					"Se ha producido un error durante la actualización del plato. Detalles: "
							+ e.getSource().getException().getMessage());
		});

		new HiloEjecutador(App.semaforo, actualizarPlatoTask).start();
	}
	
	void onActualizarListaAction(ActionEvent event) {
		BarganizerTasks tareas = new BarganizerTasks();

		tareas.getObtenerTiposPlatoTask().setOnSucceeded(e -> {
			ObservableList<TipoPlato> lista = tareas.getObtenerTiposPlatoTask().getValue();
			model.setListaTipos(lista);
		});

		tareas.getInicializarCartaTask().setOnSucceeded(e -> {
			ObservableList<Carta> lista = tareas.getInicializarCartaTask().getValue();

			model.setListaCartas(FXCollections.observableArrayList(lista));

		});

		new HiloEjecutador(App.semaforo, tareas.getObtenerTiposPlatoTask()).start();
		new HiloEjecutador(App.semaforo, tareas.getInicializarCartaTask()).start();
	}
}
