package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TextSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {
	
	public static final double TILE_WIDTH = 300;
    public static final double TILE_HEIGHT = 150;
    
    private Tile clockTile;
    
    // CONTROLADORES
    private InicioController inicioController = new InicioController();
    private MesasController mesasController = new MesasController();
    private EmpleadoController empleadoController = new EmpleadoController();
    private ReservasController reservasController = new ReservasController();
  
    
    //VISTA
    
    @FXML
    private JFXButton cartaButton;

    @FXML
    private Label cerrarSesionLabel;

    @FXML
    private JFXButton comandasButton;

    @FXML
    private ImageView empleadoImageView;

    @FXML
    private Label empleadoLabel;

    @FXML
    private JFXButton empleadosButton;

    @FXML
    private JFXButton inicioButton;

    @FXML
    private JFXButton mesaButton;

    @FXML
    private JFXButton reservasButton;

    @FXML
    private BorderPane root;

    @FXML
    private HBox satisfactionWidgetBox;

    @FXML
    private VBox widgetBox;
    
    public BorderPane getView() {
    	return root;
    }
    
    public MainController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
    	loader.setController(this);
    	loader.load();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		clockTile = TileBuilder.create()
                .skinType(SkinType.CLOCK).textSize(TextSize.BIGGER)
                .title("RELOJ BARGANIZER")
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .text("Hora local canaria")
                .dateVisible(true)
                .locale(Locale.US)
                .running(true)
                .build();
		
		widgetBox.getChildren().addAll(clockTile);
		
		empleadoImageView.setImage(new Image(getClass().getResourceAsStream("/images/unknown_person.jpg")));
		root.setCenter(inicioController.getView());

	}
	
    @FXML
    void onCartasAction(ActionEvent event) {

    }

    @FXML
    void onCerrarSesionAction(MouseEvent event) {

    }

    @FXML
    void onComandasAction(ActionEvent event) {

    }

    @FXML
    void onEmpleadosAction(ActionEvent event) {
    	
    	root.setCenter(empleadoController.getView());
    }

    @FXML
    void onInicioAction(ActionEvent event) {
    	try {
			inicioController = new InicioController();
			root.setCenter(inicioController.getView());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	

    }

    @FXML
    void onMesasAction(ActionEvent event) {

    	root.setCenter(mesasController.getView());
    }

    @FXML
    void onReservasAction(ActionEvent event) {
    	
    	root.setCenter(reservasController.getView());

    }

}
