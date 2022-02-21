package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.barganizer.beansprop.CartaProp;
import dad.barganizer.db.beans.TipoPlato;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ModificarPlatoController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<CartaProp> cartaCombo;

    @FXML
    private ImageView imgPlatoView;

    @FXML
    private Button modificarButton;

    @FXML
    private TextField nombrePlatoText;

    @FXML
    private TextField precioText;

    @FXML
    private ComboBox<TipoPlato> tipoCombo;

    @FXML
    private GridPane view;
    
    public ModificarPlatoController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarPlatoView.fxml"));
    	loader.setController(this);
    	loader.load();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
