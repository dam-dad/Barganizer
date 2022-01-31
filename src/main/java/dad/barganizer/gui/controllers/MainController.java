package dad.barganizer.gui.controllers;

import java.io.IOException;

import dad.barganizer.gui.models.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController {

	private MainModel model = new MainModel();
	
    @FXML
    private Label laberlEjemplo;

    @FXML
    private VBox view;
    
    public MainController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
    
    public VBox getView() {
		return view;
	}

    public MainModel getModel() {
		return model;
	}
}

