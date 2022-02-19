package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class MesasController implements Initializable {
	
	
	// VISTA  
	
    @FXML
    private JFXButton añadirButton;

    @FXML
    private FlowPane mesasFlow;

    @FXML
    private JFXButton modificarButton;

    @FXML
    private JFXButton quitarButton;

    @FXML
    private BorderPane root;

    
	public BorderPane getView() {
		return root;
	}
	
	
    public MesasController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MesasView.fxml"));
    	loader.setController(this);
    	loader.load();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	}
	
	
    @FXML
    void onAñadirAction(ActionEvent event) {

    }

    @FXML
    void onModificarAction(ActionEvent event) {

    }

    @FXML
    void onQuitarAction(ActionEvent event) {

    }

}
