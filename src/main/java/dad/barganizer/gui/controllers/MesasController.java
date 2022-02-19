package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import com.jfoenix.controls.JFXButton;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.gui.models.MesasModel;
import dad.barganizer.thread.HiloEjecutador;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class MesasController implements Initializable {
	

	// MODEL

	MesasModel model = new MesasModel();

	// VISTA

	@FXML
	private JFXButton añadirButton;

	@FXML
	private FlowPane mesasFlow;

	@FXML
	private JFXButton modificarButton;

	@FXML
	private JFXButton quitarButton;

	@FXML
	private BorderPane root;

	public BorderPane getView() {
		return root;
	}

	public MesasController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MesasView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		BarganizerTasks tareas = new BarganizerTasks();

		tareas.getInicializarMesasTask().setOnSucceeded(e -> {
			ObservableList<Mesa> res = tareas.getInicializarMesasTask().getValue();
			model.setListaMesas(res);

			for (Mesa mesa : res) {
				mesasFlow.getChildren().add(new ImageTile(mesa));
			}
		});

		tareas.getInicializarMesasTask().setOnFailed(e -> {
			System.err.println("Inicialización de mesas fallida: ");
			e.getSource().getException().printStackTrace();
		});
		
		new HiloEjecutador(App.semaforo, tareas.getInicializarMesasTask()).start();

	}

	@FXML
	void onAñadirAction(ActionEvent event) {

	}

	@FXML
	void onModificarAction(ActionEvent event) {

	}

	@FXML
	void onQuitarAction(ActionEvent event) {

	}

}
