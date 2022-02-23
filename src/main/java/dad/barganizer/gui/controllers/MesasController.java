package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.gui.models.MesasModel;
import dad.barganizer.thread.HiloEjecutador;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MesasController implements Initializable {

	// MODEL

	MesasModel model = new MesasModel();
	AñadirMesaController añadirMesaController;

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
	private VBox root;

	public VBox getView() {
		return root;
	}

	public MesasController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MesasView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		listarMesas();

		model.mesaSeleccionadaProperty().addListener((o, ov, nv) -> {
			if (ov != null && nv != null) {
				if (((Mesa) ov.getReferencia()).isActiva()) {
					ov.setBackgroundColor(Color.DARKGREEN);
				} else {
					ov.setBackgroundColor(Color.CRIMSON);
				}
			}
			if (nv != null) {
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_MESAS);
			}
		});

		mesasFlow.setOnMouseClicked(e -> {

			if (model.getMesaSeleccionada() != null) {
				if (((Mesa) model.getMesaSeleccionada().getReferencia()).isActiva()) {
					model.getMesaSeleccionada().setBackgroundColor(Color.DARKGREEN);
					model.setMesaSeleccionada(null);
				}

				else {
					model.getMesaSeleccionada().setBackgroundColor(Color.CRIMSON);
					model.setMesaSeleccionada(null);
				}

			}

		});

	}

	@FXML
	void onAñadirAction(ActionEvent event) {

		try {

			añadirMesaController = new AñadirMesaController();

		} catch (Exception e) {

			System.err.println("Error: " + e.getMessage());
		}

		Stage stage = new Stage();
		stage.setTitle("Añadir mesa");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/barganizer.PNG")));
		stage.setScene(new Scene(añadirMesaController.getView()));
		stage.getScene().getStylesheets().setAll("/css/mainView.css");

		// Lineas opcionales pero que permiten que al tener una ventana abierta, la otra
		// quede deshabilitada

		stage.initOwner(App.primaryStage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

		mesasFlow.getChildren().clear();

		listarMesas();

	}

	@FXML
	void onModificarAction(ActionEvent event) {

		try {

			ModificarMesaController modificarMesaController = new ModificarMesaController();
			Mesa mesa = (Mesa) (model.getMesaSeleccionada().getReferencia());

			modificarMesaController.seleccionado.set(mesa);
			modificarMesaController.setCantidadText(mesa.getCantPersonas());
			modificarMesaController.setIdLabel(String.valueOf(mesa.getNumero()));
			modificarMesaController.setActivaCheck(mesa.isActiva());

			Stage stage = new Stage();
			stage.setTitle("Modificar mesa");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/barganizer.PNG")));
			stage.setScene(new Scene(modificarMesaController.getView()));
			stage.getScene().getStylesheets().setAll("/css/mainView.css");

			// Lineas opcionales pero que permiten que al tener una ventana abierta, la otra
			// quede deshabilitada

			stage.initOwner(App.primaryStage);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.showAndWait();

			mesasFlow.getChildren().clear();

			listarMesas();

		} catch (Exception e) {

			App.error("Error", "Error al modificar", "Debe tener una mesa seleccionada.");
		}

	}

	@FXML
	void onQuitarAction(ActionEvent event) {

		if (model.getMesaSeleccionada() != null) {

			if (App.confirm("BORRAR", "PROCESO DE BORRADO", "¿Desea borrar la mesa?")) {

				try {
					FuncionesDB.eliminarMesa(App.getBARGANIZERDB().getSes(),
							(Mesa) (model.getMesaSeleccionada().getReferencia()));
					App.info("Completado", "Borrado completado", "Se ha completado el borrado con éxito");
				} catch (Exception e) {
					App.error("Error", "Error al borrar", "No puede borrar una mesa con una comanda activa.");
				}

			}
		}

		else {
			App.error("Error", "Error al borrar", "Debe tener una mesa seleccionada.");
		}

		mesasFlow.getChildren().clear();

		listarMesas();

		model.setMesaSeleccionada(null);

	}

	public void listarMesas() {

		BarganizerTasks tareas = new BarganizerTasks();

		tareas.getInicializarMesasTask().setOnSucceeded(e -> {
			ObservableList<Mesa> res = tareas.getInicializarMesasTask().getValue();
			model.setListaMesas(res);

			for (Mesa mesa : res) {

				if (mesa.isActiva()) {
					ImageTile img = new ImageTile(mesa);
					img.setBackgroundColor(Color.DARKGREEN);
					mesasFlow.getChildren().add(img);
				}

				else {

					ImageTile img = new ImageTile(mesa);
					img.setBackgroundColor(Color.CRIMSON);
					mesasFlow.getChildren().add(img);

				}

			}

			ObservableList<Node> l = mesasFlow.getChildren();

			for (Node node : l) {
				node.setOnMouseClicked(ev -> {
					ImageTile imageTileClickeado = (ImageTile) ev.getSource();
					Mesa seleccionada = (Mesa) imageTileClickeado.getReferencia();
					System.out.println(seleccionada.getNumero());

					model.setMesaSeleccionada(imageTileClickeado);

				});
			}
		});

		tareas.getInicializarMesasTask().setOnFailed(e -> {
			System.err.println("Inicialización de mesas fallida: ");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(App.semaforo, tareas.getInicializarMesasTask()).start();

	}

	public MesasModel getModel() {
		return model;
	}
}
