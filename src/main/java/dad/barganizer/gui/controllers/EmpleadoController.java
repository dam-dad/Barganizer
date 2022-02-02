package dad.barganizer.gui.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class EmpleadoController implements Initializable {

	@FXML
    private Button anadirButton;

    @FXML
    private Button eliminarButton;

    @FXML
    private VBox view;

    @FXML
    void OnActionAnadir(ActionEvent event) {

    }
    
    @FXML
    void OnEliminarAction(ActionEvent event) {

    }
    
    public VBox getView() {
		return view;
	}
    
    public EmpleadoController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EmpleadoView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
