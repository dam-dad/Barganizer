package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.barganizer.App;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.gui.models.AddCartaModel;
import dad.barganizer.thread.HiloEjecutador;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddCartaController implements Initializable {

	private AddCartaModel model = new AddCartaModel();

	@FXML
	private Button addButton;

	@FXML
	private HBox buttonsBox;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField nombreText;

	@FXML
	private VBox view;

	@FXML
	void onAddAction(ActionEvent event) {

		insertarCartaTask.setOnSucceeded(e -> {
			App.info("Éxito", "Carta insertada", "Se ha insertado la carta " + model.getNombre() + " correctamente");
			App.getBARGANIZERDB().resetSesion();
			Stage stage = (Stage) addButton.getScene().getWindow();
			stage.close();
		});

		insertarCartaTask.setOnFailed(e -> {
			App.error("Error", "Carta no insertada",
					"La carta no pudo ser insertada. Detalles: " + e.getSource().getException().getMessage());
		});

		new HiloEjecutador(App.semaforo, insertarCartaTask).start();

	}

	@FXML
	void onCancelAction(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public AddCartaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddCartaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nombreText.textProperty().bindBidirectional(model.nombreProperty());

	}

	public VBox getView() {
		return view;
	}

	private Task<Void> insertarCartaTask = new Task<Void>() {

		@Override
		protected Void call() throws Exception {
			FuncionesDB.insertarCarta(App.getBARGANIZERDB().getSes(), model.getNombre());

			return null;
		}
	};

	public void redeclararTask() {
		insertarCartaTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				FuncionesDB.insertarCarta(App.getBARGANIZERDB().getSes(), model.getNombre());

				return null;
			}
		};
	}

	public AddCartaModel getModel() {
		return model;
	}
}
