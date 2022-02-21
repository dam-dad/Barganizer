package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.barganizer.App;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Reserva;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModificarReservaController implements Initializable {
	
	public ObjectProperty<Reserva> seleccionado = new SimpleObjectProperty<>();

	@FXML
	private JFXButton añadirButton;

	@FXML
	private JFXButton eliminarButton;

	@FXML
	private DatePicker fechaPicker;

	@FXML
	private TextField horaText;

	@FXML
	private TextField personasText;

	@FXML
	private ComboBox<Mesa> mesaCombo;

	@FXML
	private ComboBox<Empleado> empleadoCombo;

	@FXML
	private VBox root;

	public VBox getView() {
		return root;
	}

	public ModificarReservaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AñadirReservaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void onAñadirAction(ActionEvent event) {
		

		try {
			
			String time = horaText.getText();
			LocalTime hora = LocalTime.parse(time);
			
			Reserva reserva = new Reserva();
			reserva.setId(seleccionado.get().getId());
			reserva.setMesaReserva(mesaCombo.getSelectionModel().getSelectedItem());
			reserva.setEmpleadoReserva(empleadoCombo.getSelectionModel().getSelectedItem());
			reserva.setCantPersonas(Integer.parseInt(personasText.getText()));
			reserva.setFecha(fechaPicker.getValue().atTime(hora));
			

			FuncionesDB.modificarReserva(App.getBARGANIZERDB().getSes(), reserva);
			
			App.info("Completado", "Modificación completada", "Se ha completado la modificación con éxito");
			
			Stage stage = (Stage) añadirButton.getScene().getWindow();
			stage.close();

		} catch (Exception e) {

			App.error("Error", "Error al modificar","No se ha podido completar la modificación.");
		}

	}

	@FXML
	void onCancelarAction(ActionEvent event) {

		Stage stage = (Stage) eliminarButton.getScene().getWindow();
		stage.close();

	}
	
	public DatePicker getFechaPicker() {
		return fechaPicker;
	}

	public void setFechaPicker(LocalDate fechaPicker) {
		this.fechaPicker.setValue(fechaPicker);
	}

	public TextField getHoraText() {
		return horaText;
	}

	public void setHoraText(String horaText) {
		this.horaText.setText(horaText);
	}

	public TextField getPersonasText() {
		return personasText;
	}

	public void setPersonasText(String personasText) {
		this.personasText.setText(personasText);
	}

	public ComboBox<Mesa> getMesaCombo() {
		return mesaCombo;
	}

	public void setMesaCombo(Mesa mesa) {
		this.mesaCombo.getSelectionModel().select(mesa);
	}

	public ComboBox<Empleado> getEmpleadoCombo() {
		return empleadoCombo;
	}

	public void setEmpleadoCombo(Empleado empleado) {
		this.empleadoCombo.getSelectionModel().select(empleado);
	}
	
	public void setAllMesaCombo(List<Mesa> lista) {
		this.mesaCombo.setItems(FXCollections.observableArrayList(lista));
	}

	public void setAllEmpleadosCombo(List<Empleado> lista) {
		this.empleadoCombo.setItems(FXCollections.observableArrayList(lista));
	}
}
