package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import com.jfoenix.controls.JFXComboBox;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.db.BarganizerDB;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.gui.models.InicioModel;
import dad.barganizer.thread.HiloEjecutador;
import eu.hansolo.tilesfx.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InicioController implements Initializable {

	private Semaphore semaforo = new Semaphore(1); // Controla la cantidad de tareas que pueden ejecutarse
													// simultáneamente

	// Models
	private InicioModel model = new InicioModel();

	@FXML
	private FlowPane bebidasFlow;

	@FXML
	private Tab bebidasTab;

	@FXML
	private JFXComboBox<Carta> cartaCombo;

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

		cartaCombo.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

			if (nv != null) {
				listarPlatosDesdeCarta(nv);
				listarEntrantesDesdeCarta(nv);
				listarPostresDesdeCarta(nv);
			}

		});

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
				bebidasFlow.getChildren().add(new ImageTile(bebida));
			}

			ObservableList<Node> l = bebidasFlow.getChildren();
			System.out.println("Cantidad de bebidas: " + l.size());
			for (Node node : l) {
				node.setOnMouseClicked(ev -> {
					if (ev.getClickCount() >= 2) {
						ImageTile imageTileClickeado = (ImageTile) ev.getSource();
						System.out.println(((Bebida) imageTileClickeado.getReferencia()).getNombre());

					}
				});
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
//					mesasFlow.getChildren()
//							.add(new ImageTile(getClass().getResourceAsStream("/images/mesa.png").readAllBytes(),
//									"" + (Integer.parseInt("" + mesa.getNumero())),
//									"Personas: " + mesa.getCantPersonas()).getTile());
					mesasFlow.getChildren().add(new ImageTile(mesa));
			}
		});

		tareas.getInicializarMesasTask().setOnFailed(e -> {
			System.err.println("Inicialización de mesas fallida: ");
			e.getSource().getException().printStackTrace();
		});

		// Carta
		tareas.getInicializarCartaTask().setOnSucceeded(e -> {
			ObservableList<Carta> res = tareas.getInicializarCartaTask().getValue();
			model.setListaCartas(res);

			for (Carta carta : res) {
				cartaCombo.getItems().add(carta);
			}

			cartaCombo.getSelectionModel().select(0);

		});

		// Ejecución de tareas

//		new Thread(tareas.getInicializarBebidasTask()).start();
//		new Thread(tareas.getInicializarMesasTask()).start();
//		new Thread(tareas.getInicializarCartaTask()).start();

		// Hilo ejecutador de tareas
		new HiloEjecutador(semaforo, tareas.getInicializarBebidasTask()).start();
		new HiloEjecutador(semaforo, tareas.getInicializarMesasTask()).start();
		new HiloEjecutador(semaforo, tareas.getInicializarCartaTask()).start();

	}

	/**
	 * Este método se encargará de listar los platos según el tipo de carta
	 * seleccionada
	 **/
	private void listarPlatosDesdeCarta(Carta c) {
		BarganizerTasks tareas = new BarganizerTasks(c);

		tareas.getObtenerPlatosCartaTask().setOnSucceeded(e -> {
			ObservableList<Plato> res = tareas.getObtenerPlatosCartaTask().getValue();

			model.setListaPlatos(res);
			platosFlow.getChildren().clear();
			for (Plato plato : res) {
				platosFlow.getChildren().add(new ImageTile(plato));
			}
		});

		tareas.getObtenerPlatosCartaTask().setOnFailed(e -> {
			System.err.println("Error inicializando los platos de la carta.");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(semaforo, tareas.getObtenerPlatosCartaTask()).start();
	}

	/**
	 * Este método se encargará de listar los entrantes según la carta seleccionada
	 **/
	private void listarEntrantesDesdeCarta(Carta c) {
		BarganizerTasks tareas = new BarganizerTasks(c);

		tareas.getObtenerEntrantesTask().setOnSucceeded(e -> {

			ObservableList<Plato> res = tareas.getObtenerEntrantesTask().getValue();

			model.setListaEntrantes(res);
			entrantesFlow.getChildren().clear();

			for (Plato plato : res) {
				entrantesFlow.getChildren().add(new ImageTile(plato));
			}
		});

		tareas.getObtenerEntrantesTask().setOnFailed(e -> {
			System.err.println("Error obteniendo entrantes desde carta: ");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(semaforo, tareas.getObtenerEntrantesTask()).start();
	}

	/**
	 * Este método se encargará de listar los postres según la carta seleccionada
	 **/
	private void listarPostresDesdeCarta(Carta c) {
		BarganizerTasks tareas = new BarganizerTasks(c);

		tareas.getObtenerPostresTask().setOnSucceeded(e -> {
			ObservableList<Plato> res = tareas.getObtenerPostresTask().getValue();

			model.setListaPostres(res);
			postresFlow.getChildren().clear();

			for (Plato plato : res) {
				postresFlow.getChildren().add(new ImageTile(plato));
			}
		});

		tareas.getObtenerPostresTask().setOnFailed(e -> {
			System.err.println("Error obteniendo postres desde carta: ");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(semaforo, tareas.getObtenerPostresTask()).start();
	}

}
