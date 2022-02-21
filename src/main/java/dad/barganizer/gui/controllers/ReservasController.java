package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dad.barganizer.App;
import dad.barganizer.beansprop.ReservaProp;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Reserva;
import dad.barganizer.gui.models.ReservasModel;
import dad.barganizer.thread.HiloEjecutador;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;

public class ReservasController implements Initializable {

	// MODEL

	ReservasModel model = new ReservasModel();
	AñadirReservaController añadirReservaController;

	// VISTA

	@FXML
	private Button añadirReservaButton;

	@FXML
	private Button eliminarReservaButton;

	@FXML
	private TextField empleadText;

	@FXML
	private TableColumn<ReservaProp, String> empleadoColumn;

	@FXML
	private TableColumn<ReservaProp, LocalDateTime> fechaColumn;

	@FXML
	private DatePicker fechaPicker;

	@FXML
	private TextField horaText;

	@FXML
	private TableColumn<ReservaProp, String> idColumn;

	@FXML
	private TableColumn<ReservaProp, String> mesaColumn;

	@FXML
	private TextField mesaText;

	@FXML
	private TableColumn<ReservaProp, String> personasColumn;

	@FXML
	private TextField personasText;

	@FXML
	private TableView<ReservaProp> reservasList;

	@FXML
	private VBox root;

	public VBox getView() {
		return root;
	}

	public ReservasController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReservasView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		ArrayList<ReservaProp> listaProp = new ArrayList<>();

		model.setLista(FXCollections.observableArrayList(listaProp));

		model.seleccionadaProperty().bind(reservasList.getSelectionModel().selectedItemProperty());
		
		listarReservas();

		reservasList.itemsProperty().bind(model.listaProperty());

		idColumn.setCellValueFactory(v -> v.getValue().idProperty());
		empleadoColumn.setCellValueFactory(v -> v.getValue().empleadoProperty());
		mesaColumn.setCellValueFactory(v -> v.getValue().mesaProperty());
		personasColumn.setCellValueFactory(v -> v.getValue().personasProperty());
		fechaColumn.setCellValueFactory(v -> v.getValue().fechaProperty());

		idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		empleadoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		mesaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		personasColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		fechaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));

	}

	@FXML
	void onAñadirReservaAction(ActionEvent event) {

		try {

			añadirReservaController = new AñadirReservaController();

		} catch (Exception e) {

			System.err.println("Error: " + e.getMessage());
		}

		Stage stage = new Stage();
		stage.setTitle("Añadir reserva");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/barganizer.PNG")));
		stage.setScene(new Scene(añadirReservaController.getView()));
		stage.getScene().getStylesheets().setAll("/css/mainView.css");

		// Lineas opcionales pero que permiten que al tener una ventana abierta, la otra
		// quede deshabilitada

		stage.initOwner(App.primaryStage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

		reservasList.getItems().clear();

		listarReservas();

	}

	@FXML
	void onEliminarReservaAction(ActionEvent event) {

		if (model.getSeleccionada() != null) {
			if (App.confirm("BORRAR", "PROCESO DE BORRADO", "¿Desea borrar la reserva?")) {

				FuncionesDB.eliminarReserva(App.getBARGANIZERDB().getSes(), model.getSeleccionada().getReferencia());

				App.info("Completado", "Borrado completado", "Se ha completado el borrado con éxito");
			}
		}

		else {

			App.error("Error", "Error al borrar", "Debe tener una reserva seleccionada.");
		}

		reservasList.getItems().clear();

		listarReservas();
	}

	public void listarReservas() {

		BarganizerTasks tareas = new BarganizerTasks();

		tareas.getInicializarReservasTask().setOnSucceeded(e -> {

			List<Reserva> lista = FuncionesDB.listarReservas(App.getBARGANIZERDB().getSes());

			ArrayList<ReservaProp> listaProp = new ArrayList<>();

			for (Reserva reserva : lista) {

				listaProp.add(new ReservaProp(reserva));
			}

			model.setLista(FXCollections.observableArrayList(listaProp));

		});

		tareas.getInicializarReservasTask().setOnFailed(e -> {
			System.err.println("Inicialización de Reservas fallida: ");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(App.semaforo, tareas.getInicializarReservasTask()).start();

	}

}
