package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import com.jfoenix.controls.JFXButton;

import dad.barganizer.App;
import dad.barganizer.db.HibernateUtil;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.gui.models.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	// MODEL
	LoginModel model = new LoginModel();

	// CONTROLLER
	MainController mainController;

	// VISTA
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

		model.nombreProperty().bindBidirectional(nombreText.textProperty());
		model.claveProperty().bindBidirectional(psswdText.textProperty());

	}

	@FXML
	void onAccederAction(ActionEvent event) {

		boolean acceder = checkLogin();

		if (acceder) {
			App.info("LOGIN", "LOGIN COMPLETADO CON ÉXITO ", "ACCESO A BARGANIZER.");

			try {
				mainController = new MainController();

				mainController.getModel().setEmpleado(model.getEmpleado());

				Stage stage = new Stage();
				stage.setTitle("BARGANIZER");
				stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/barganizer.PNG")));
				stage.setScene(new Scene(mainController.getView()));
				stage.getScene().getStylesheets().setAll("/css/mainView.css");

				Stage stageLogin = (Stage) accederButton.getScene().getWindow();
				stageLogin.close();

				stage.showAndWait();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else {
			App.error("LOGIN", "ERROR EN EL LOGIN", "Asegúrese de introducir los datos de verificación correctamente.");
			System.out.println("LOGIN FALLIDO");
		}

	}

	@FXML
	void onCerrarAction(ActionEvent event) {

		javafx.application.Platform.exit();
	}

	public boolean checkLogin() {

		boolean validLogin = false;
		Session ses = HibernateUtil.getSessionFactory().openSession();

		try {

			ses.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Empleado> empleadosList = ses.createQuery(
					"FROM Empleado WHERE nombre = '" + model.getNombre() + "' AND pass = '" + model.getClave() + "'")
					.list();

			if (!empleadosList.isEmpty()) {
				validLogin = true;
				model.setEmpleado(empleadosList.get(0));
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
