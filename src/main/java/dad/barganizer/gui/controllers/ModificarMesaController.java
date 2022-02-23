package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import dad.barganizer.App;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.validators.IntegerValidator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModificarMesaController implements Initializable {

	// MODEL

	public ObjectProperty<Mesa> seleccionado = new SimpleObjectProperty<>();

	@FXML
	private JFXCheckBox activaCheck;

	@FXML
	private JFXButton cancelarButton;

	@FXML
	private TextField cantidadText;

	@FXML
	private Label idLabel;

	@FXML
	private JFXButton modificarButton;

	@FXML
	private VBox root;

	public VBox getView() {
		return root;
	}

	public ModificarMesaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarMesaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ValidationSupport support = new ValidationSupport();
		support.registerValidator(cantidadText, true, new IntegerValidator());
		modificarButton.disableProperty().bind(support.invalidProperty());

	}

	@FXML
	void onCancelarAction(ActionEvent event) {

		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onModificarAction(ActionEvent event) {

		try {

			Mesa m = new Mesa();
			m.setNumero(seleccionado.get().getNumero());
			m.setCantPersonas(Integer.parseInt(cantidadText.getText()));
			m.setActiva(activaCheck.isSelected());

			FuncionesDB.modificarMesa(App.getBARGANIZERDB().getSes(), m);

			App.info("Completado", "Modificación completada", "Se ha completado la modificación con éxito");

			Stage stage = (Stage) modificarButton.getScene().getWindow();
			stage.close();

		} catch (Exception e) {

			App.error("Error", "Error al añadir", "No se ha podido completar la modificación.");
		}
	}

	public JFXCheckBox getActivaCheck() {
		return activaCheck;
	}

	public void setActivaCheck(boolean activaCheck) {
		this.activaCheck.setSelected(activaCheck);
	}

	public TextField getCantidadText() {
		return cantidadText;
	}

	public void setCantidadText(int cantidadText) {
		this.cantidadText.setText(String.valueOf(cantidadText));
	}

	public Label getIdLabel() {
		return idLabel;
	}

	public void setIdLabel(String idLabel) {
		this.idLabel.setText(idLabel);
		;
	}

}
