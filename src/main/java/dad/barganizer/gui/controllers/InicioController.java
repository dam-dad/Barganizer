package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.db.BarganizerDB;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.gui.models.InicioModel;
import dad.barganizer.thread.HiloEjecutador;
import eu.hansolo.tilesfx.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InicioController implements Initializable {

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

	@FXML
	private JFXTreeTableView<?> comandasTable;

	@FXML
	private Button generarTicketButton;

	@FXML
	private Label totalComandaLabel;

	public InicioController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		bebidasFlow.setOnMouseClicked(e -> {

			if (model.getTileBebidaSeleccionada() != null) {
				model.getTileBebidaSeleccionada().setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
			}

		});

		mesasFlow.setOnMouseClicked(e -> {

			if (model.getTileMesaSeleccionada() != null) {
				model.getTileMesaSeleccionada().setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
			}

		});

		model.tileMesaSeleccionadaProperty().addListener((o, ov, nv) -> {
			System.out.println("OV: " + ov + " --- NV: "+ nv);
			if (ov != null && nv != null) {
				Mesa ref = (Mesa) nv.getReferencia();
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);

			}
			nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);

		});

		model.tileBebidaSeleccionadaProperty().addListener((o, ov, nv) -> {
			System.out.println("TILE-BEBIDASELEC-: OV: " + ov + " --- NV:" + nv);
			// Si no había ningún tile de bebida seleccionado previamente
			if (ov == null && nv != null) {
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);
			}

			// Si se cambia de tile de bebida seleccionado
			if (ov != null && nv != null) {
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);
			}

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
			ObservableList<Plato> res = tareas.getInicializarBebidasTask().getValue();
			model.setListaBebidas(res);

			for (Plato bebida : res) {
				bebidasFlow.getChildren().add(new ImageTile(bebida));
			}

			/* Controlamos los nodos almacenados en las bebidas para asignarle a cada uno su método
			 * onMouseClicked, que nos ayudará a añadir las bebidas a la comanda de una mesa previamente seleccionada
			 * en el tabpane de mesas */
			ObservableList<Node> l = bebidasFlow.getChildren();
			for (Node node : l) {
				node.setOnMouseClicked(ev -> {
					ImageTile imageTileClickeado = (ImageTile) ev.getSource();
					Plato bebidaSeleccionada = (Plato) imageTileClickeado.getReferencia();
					
					
					model.setTileBebidaSeleccionada(imageTileClickeado);
					if (ev.getClickCount() >= 2) {
						if (model.getTileMesaSeleccionada() == null) {
							App.warning("Advertencia", "Mesa no seleccionada", "Debe seleccionar una mesa antes de añadir un producto en la comanda");
						} else {
							Mesa mesaSeleccionada = (Mesa)model.getTileMesaSeleccionada().getReferencia();
//							FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(), bebidaSeleccionada, mesaSeleccionada, 1);
						}
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
					mesasFlow.getChildren().add(new ImageTile(mesa));
			}
			
			/* Obtenemso */
			ObservableList<Node> l = mesasFlow.getChildren();
			for (Node node : l) {
				node.setOnMouseClicked(ev -> {
					ImageTile imageTileClickeado = (ImageTile) ev.getSource();
					Mesa seleccionada = (Mesa) imageTileClickeado.getReferencia();
					System.out.println(seleccionada.getNumero());
					
					model.setTileMesaSeleccionada(imageTileClickeado);
					
				});
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
		new HiloEjecutador(App.semaforo, tareas.getInicializarBebidasTask()).start();
		new HiloEjecutador(App.semaforo, tareas.getInicializarMesasTask()).start();
		new HiloEjecutador(App.semaforo, tareas.getInicializarCartaTask()).start();

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

		new HiloEjecutador(App.semaforo, tareas.getObtenerPlatosCartaTask()).start();
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

		new HiloEjecutador(App.semaforo, tareas.getObtenerEntrantesTask()).start();
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

		new HiloEjecutador(App.semaforo, tareas.getObtenerPostresTask()).start();
	}

	@FXML
	void onGenerarTicketAction(ActionEvent event) {

	}

}
