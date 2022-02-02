package dad.barganizer.gui.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AgregarEmpleadoController implements Initializable{

	@FXML
    private Button anadirButton;

    @FXML
    private TextField apellidosText;

    @FXML
    private Button cancelarButton;

    @FXML
    private TextField generoText;

    @FXML
    private ImageView imageView;

    @FXML
    private DatePicker ingresoDate;

    @FXML
    private DatePicker nacimientoDate;

    @FXML
    private TextField nombreText;

    @FXML
    private Button quitarButton;

    @FXML
    private GridPane view;

    public GridPane getView() {
		return view;
	}
    
    public AgregarEmpleadoController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarEmpleadoView.fxml"));
    	loader.setController(this);
    	loader.load();
    }
    
    @FXML
    void OnAnadirAction(ActionEvent event) {

    }

    @FXML
    void OnCancelarButton(ActionEvent event) {

    }

    @FXML
    void OnQuitarButton(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
