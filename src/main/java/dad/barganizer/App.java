package dad.barganizer;

import dad.barganizer.gui.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private MainController mainController;
	
	@Override
	public void init() throws Exception {
		super.init();
		mainController = new MainController();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene escena = new Scene(mainController.getView());
		
		primaryStage.setTitle("Barganizer");
		primaryStage.setScene(escena);
		primaryStage.show();

	}
	
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}

}
