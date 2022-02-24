package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXButton;

import dad.barganizer.App;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.gui.models.AddReservaModel;
import dad.barganizer.validators.IntegerValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * Controler que nos permite añadir reservas recogiendo los datos pasados por la vista.
 *
 */
public class AddReservaController implements Initializable {

	AddReservaModel model = new AddReservaModel();

	@FXML
	private JFXButton addButton;

	@FXML
	private JFXButton eliminarButton;

	@FXML
	private TextField empleadText;

	@FXML
	private DatePicker fechaPicker;

	@FXML
	private TextField horaText;

	@FXML
	private TextField mesaText;

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

	public AddReservaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AñadirReservaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Validator<String> horaValidator = (control, value) -> {
			return ValidationResult.fromMessageIf(horaText, "La hora introducida no es válida.", Severity.ERROR,
					!Pattern.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]", value));
		};

		ValidationSupport support = new ValidationSupport();
		support.registerValidator(personasText, true, new IntegerValidator());
		support.registerValidator(horaText, true, horaValidator);

		addButton.disableProperty().bind(support.invalidProperty());

		mesaCombo.setItems(FXCollections.observableArrayList(FuncionesDB.listarMesas(App.getBARGANIZERDB().getSes())));
		empleadoCombo.setItems(
				FXCollections.observableArrayList(FuncionesDB.listarEmpleados(App.getBARGANIZERDB().getSes())));
		mesaCombo.getSelectionModel().select(0);
		empleadoCombo.getSelectionModel().select(0);

		model.empleadoSeleccionadoProperty().bind(empleadoCombo.getSelectionModel().selectedItemProperty());
		model.mesaSeleccionadaProperty().bind(mesaCombo.getSelectionModel().selectedItemProperty());
		model.personasProperty().bind(personasText.textProperty());
		model.horaProperty().bind(horaText.textProperty());
		model.fechaProperty().bind(fechaPicker.valueProperty());

	}

	/**
	 * 
	 * Botón que nos permite añadir los datos en la base de datos llamando a la función.
	 */
	@FXML
	void onAddAction(ActionEvent event) {

		try {
			String time = model.getHora();
			LocalTime hora = LocalTime.parse(time);

			FuncionesDB.insertarReserva(App.getBARGANIZERDB().getSes(), model.getMesaSeleccionada(),
					model.getEmpleadoSeleccionado(), model.getFecha().atTime(hora),
					Integer.parseInt(model.getPersonas()));

			App.info("Completado", "Inserción completado", "Se ha completado la inserción con éxito");

			Stage stage = (Stage) addButton.getScene().getWindow();
			stage.close();

		} catch (Exception e) {

			App.error("Error", "Error al añadir",
					"No se ha podido completar la inserción. Revise que los datos introducidos son correctos.");

		}
	}

	@FXML
	void onCancelarAction(ActionEvent event) {

		Stage stage = (Stage) eliminarButton.getScene().getWindow();
		stage.close();
	}
}
