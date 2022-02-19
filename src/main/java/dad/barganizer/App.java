package dad.barganizer;

import java.util.Optional;
import java.util.concurrent.Semaphore;

import dad.barganizer.db.BarganizerDB;
import dad.barganizer.gui.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

	MainController mainController;
	public static Semaphore semaforo = new Semaphore(1);
	public static Stage primaryStage;
	
	private static BarganizerDB BARGANIZERDB = new BarganizerDB();
	
	
	@Override
	public void init() throws Exception {
		super.init();
		mainController = new MainController();
		Font fuente = Font.loadFont(getClass().getResourceAsStream("/Styles/Merienda-Regular.ttf"), 16);
		Font fuenteNegrita = Font.loadFont(getClass().getResourceAsStream("/Styles/Merienda-Bold.ttf"), 16);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 
		
		Scene scene = new Scene(mainController.getView());
		scene.getStylesheets().setAll("/css/mainView.css");
		

		App.primaryStage = primaryStage;

		primaryStage.setTitle("Barganizer");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/barganizer.png")));
		primaryStage.setScene(scene);
//		primaryStage.setMaximized(true);
		primaryStage.show();

	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		BARGANIZERDB.cerrar();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static BarganizerDB getBARGANIZERDB() {
		return BARGANIZERDB;
	}
	
	/** Método que se utilizará para mostrar los errores a través de una alerta **/
	public static void error(String title, String header, String content) {
		Alert error = new Alert(AlertType.ERROR);
		Stage errorStage = (Stage)error.getDialogPane().getScene().getWindow();
//		errorStage.getIcons().add(new Image(App.class.getResourceAsStream("")));
		error.setTitle(title);
		error.setHeaderText(header);
		error.setContentText(content);
		error.showAndWait();
	}
	
	/** Método que se utilizará para mostrar la información a través de una alerta **/
	public static void info(String title, String header, String content) {
		Alert info = new Alert(AlertType.INFORMATION);
		Stage infoStage = (Stage)info.getDialogPane().getScene().getWindow();
//		infoStage.getIcons().add(new Image(App.class.getResourceAsStream("")));
		info.setTitle(title);
		info.setHeaderText(header);
		info.setContentText(content);
		info.showAndWait();
	}
	
	/** Método que se utilizará para mostrar un diálogo de confirmación a través de una alerta **/
	public static boolean confirm(String title, String header, String content) {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		Stage confirmStage = (Stage)confirm.getDialogPane().getScene().getWindow();
//		confirmStage.getIcons().add(new Image(App.class.getResourceAsStream("")));
		confirm.setTitle(title);
		confirm.setHeaderText(header);
		confirm.setContentText(content);
		Optional<ButtonType> result = confirm.showAndWait();
		return (result.get() == ButtonType.YES);
	}
	
	/** Método que se utilizará para mostrar una advertencia **/
	public static void warning(String title, String header, String content) {
		Alert warning = new Alert(AlertType.WARNING);
		Stage warningStage = (Stage)warning.getDialogPane().getScene().getWindow();
		
		warning.setTitle(title);
		warning.setHeaderText(header);
		warning.setContentText(content);
		warning.showAndWait();
	}
	
}
