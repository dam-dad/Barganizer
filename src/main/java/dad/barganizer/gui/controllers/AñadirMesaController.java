package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import dad.barganizer.App;
import dad.barganizer.db.BarganizerDB;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.gui.models.AñadirMesaModel;
import dad.barganizer.validators.IntegerValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class AñadirMesaController implements Initializable {

	// MODEL

	private AñadirMesaModel model = new AñadirMesaModel();

	// VISTA

	@FXML
	private JFXCheckBox activaCheck;

	@FXML
	private JFXButton añadirButton;

	@FXML
	private JFXButton cancelarButton;

	@FXML
	private TextField cantidadText;

	@FXML
	private VBox root;

	public VBox getView() {
		return root;
	}

	public AñadirMesaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AñadirMesaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cantidadText.textProperty().bindBidirectional(model.cantidadProperty(), new NumberStringConverter());

		activaCheck.selectedProperty().bindBidirectional(model.activaProperty());

		ValidationSupport support = new ValidationSupport();
		support.registerValidator(cantidadText, true, new IntegerValidator());
		añadirButton.disableProperty().bind(support.invalidProperty());
	}

	@FXML
	void onAñadirAction(ActionEvent event) {

		try {

			FuncionesDB.insertarMesa(App.getBARGANIZERDB().getSes(), model.getCantidad(), model.isActiva());

			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.showAndWait();
			
			Stage stage = (Stage) añadirButton.getScene().getWindow();
			stage.close();

		} catch (Exception e) {

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Error: " + e.getMessage());
			alerta.showAndWait();
		}

	}

	@FXML
	void onCancelarAction(ActionEvent event) {

		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();
	}

	public AñadirMesaModel getModel() {
		return model;
	}
}
