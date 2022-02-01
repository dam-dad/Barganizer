package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InicioController implements Initializable {

    @FXML
    private FlowPane bebidasFlow;

    @FXML
    private Tab bebidasTab;

    @FXML
    private JFXComboBox<?> cartaCombo;

    @FXML
    private FlowPane entrantesFlow;

    @FXML
    private Tab entrantesTab;

    @FXML
    private FlowPane mesasFlow;

    @FXML
    private Tab mesasTab;

    @FXML
    private FlowPane platosFlow;

    @FXML
    private Tab platosTab;

    @FXML
    private FlowPane postresFlow;

    @FXML
    private Tab postresTab;

    @FXML
    private VBox view;

    public InicioController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public VBox getView() {
		return view;
	}

}

