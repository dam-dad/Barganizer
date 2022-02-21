package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import com.jfoenix.controls.JFXButton;

import dad.barganizer.App;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.gui.models.LoginModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginController implements Initializable {

	// MODEL
	LoginModel model = new LoginModel();

	@FXML
	private JFXButton accederButton;

	@FXML
	private JFXButton cerrarButton;

	@FXML
	private TextField nombreText;

	@FXML
	private PasswordField psswdText;

	@FXML
	private GridPane root;

	public GridPane getView() {
		return root;
	}

	public LoginController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		model.nombreProperty().bind(nombreText.textProperty());
		model.claveProperty().bind(psswdText.textProperty());

	}

	@FXML
	void onAccederAction(ActionEvent event) {

	}

	@FXML
	void onCerrarAction(ActionEvent event) {

		javafx.application.Platform.exit();
	}

	public boolean checkLogin() {

		boolean validLogin =  false;
		Session ses = App.getBARGANIZERDB().getSes();

		try {

			ses.beginTransaction();

			byte[] claveBytes = model.getClave().getBytes();

			List<Empleado> empleadosList = ses.createQuery(
					"FROM empleados WHERE nombre = '" + model.getNombre() + "' AND pass = '" + model.getClave() + "'")
					.list();

			if (!empleadosList.isEmpty()) {
				validLogin = true;
			}
			
			return validLogin;
		}

		catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
			return validLogin;
		}

	}

}
