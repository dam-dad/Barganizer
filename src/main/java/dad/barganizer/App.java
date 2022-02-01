package dad.barganizer;

import dad.barganizer.gui.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	MainController mainController;
	
	@Override
	public void init() throws Exception {
		super.init();
		
		mainController = new MainController();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 
		
		Scene scene = new Scene(mainController.getView());
		
		primaryStage.setTitle("Barganizer");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/barganizer.png")));
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
