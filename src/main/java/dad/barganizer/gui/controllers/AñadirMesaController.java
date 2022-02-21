package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.controlsfx.validation.ValidationSupport;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.validators.IntegerValidator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AñadirMesaController implements Initializable {

	
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


		ValidationSupport support = new ValidationSupport();
		support.registerValidator(cantidadText, true, new IntegerValidator());
		añadirButton.disableProperty().bind(support.invalidProperty());
	}

	@FXML
	void onAñadirAction(ActionEvent event) {

		try {

			FuncionesDB.insertarMesa(App.getBARGANIZERDB().getSes(), Integer.parseInt(cantidadText.getText()), activaCheck.isSelected());
			
			App.info("Completado", "Inserción completado", "Se ha completado la inserción con éxito");
			
			Stage stage = (Stage) añadirButton.getScene().getWindow();
			stage.close();

		} catch (Exception e) {

			App.error("Error", "Error al añadir","No se ha podido completar la inserción");
		}

	}

	@FXML
	void onCancelarAction(ActionEvent event) {

		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();
	}

}
