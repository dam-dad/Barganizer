package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.db.BarganizerDB;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.gui.models.InicioModel;
import eu.hansolo.tilesfx.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InicioController implements Initializable {

	// Models
	private InicioModel model = new InicioModel();

	@FXML
	private FlowPane bebidasFlow;

	@FXML
	private Tab bebidasTab;

	@FXML
	private JFXComboBox<?> cartaCombo;

	@FXML
	private FlowPane entrantesFlow;

	@FXML
	private Tab entrantesTab;

	@FXML
	private FlowPane mesasFlow;

	@FXML
	private Tab mesasTab;

	@FXML
	private FlowPane platosFlow;

	@FXML
	private Tab platosTab;

	@FXML
	private FlowPane postresFlow;

	@FXML
	private Tab postresTab;

	@FXML
	private VBox view;

	public InicioController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		model.listaBebidasProperty().addListener((o, ov, nv) -> {
			System.out.println("OV: " + ov + " --- NV: " + nv);
		});

		inicializarEnBackground();

		// Listener

	}

	public VBox getView() {
		return view;
	}

	private void inicializarEnBackground() {

		// BarganizerTasks nos ayudará con la ejecución de tareas en segundo plano
		BarganizerTasks tareas = new BarganizerTasks();

		/*
		 * Obtenemos la tarea definida como "Inicializar bebidas" y asignamos sus
		 * acciones cuando se ejecuta correctamente y cuando falla.
		 */
		tareas.getInicializarBebidasTask().setOnSucceeded(e -> {
			ObservableList<Bebida> res = tareas.getInicializarBebidasTask().getValue();
			model.setListaBebidas(res);

			for (Bebida bebida : res) {
				bebidasFlow.getChildren().add(new ImageTile(bebida.getFoto(), bebida.getNombre()).getTile());
			}
		});

		tareas.getInicializarBebidasTask().setOnFailed(e -> {
			System.err.println("Inicialización de bebidas fallida: ");
			e.getSource().getException().printStackTrace();
		});

		// Declaración de métodos onSucceed y onFailed de la tarea de inicialización de
		// mesas
		tareas.getInicializarMesasTask().setOnSucceeded(e -> {
			ObservableList<Mesa> res = tareas.getInicializarMesasTask().getValue();
			model.setListaMesas(res);

			for (Mesa mesa : res) {
				try {
					mesasFlow.getChildren().add(
							new ImageTile(getClass().getResourceAsStream("/images/mesa.png").readAllBytes(), ""+(Integer.parseInt("" + mesa.getNumero())), "Personas: " + mesa.getCantPersonas())
									.getTile());
				} catch (IOException e1) {
					System.err.println("Error IO");
					e1.printStackTrace();
				}
			}
		});

		tareas.getInicializarMesasTask().setOnFailed(e -> {
			System.err.println("Inicialización de mesas fallida: ");
			e.getSource().getException().printStackTrace();
		});

		// Ejecución de tareas
		
		new Thread(tareas.getInicializarBebidasTask()).start();
		new Thread(tareas.getInicializarMesasTask()).start();

	}

}
