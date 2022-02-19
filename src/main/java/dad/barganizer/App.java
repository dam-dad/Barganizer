package dad.barganizer;

import dad.barganizer.db.BarganizerDB;
import dad.barganizer.gui.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	MainController mainController;
	
	private static BarganizerDB BARGANIZERDB = new BarganizerDB();
	
	
	@Override
	public void init() throws Exception {
		super.init();
		mainController = new MainController();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 
		
		Scene scene = new Scene(mainController.getView());
		scene.getStylesheets().setAll("/css/mainView.css");
		
		
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
	
	
}
