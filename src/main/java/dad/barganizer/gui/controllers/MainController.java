package dad.barganizer.gui.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dad.barganizer.gui.models.MainModel;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TextSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador que gestiona la ventana principal tras el login del empleado.
 * Será el encargado de gestionar las llamadas a los demás controllers asignados
 * a cada pestaña/botón del menú principal. Controlará el cierre de cesión. *
 */
public class MainController implements Initializable {

	public static final double TILE_WIDTH = 300;
	public static final double TILE_HEIGHT = 150;

	private Tile clockTile;

	// CONTROLADORES
	private InicioController inicioController = new InicioController();
	private MesasController mesasController;
	private EmpleadoController empleadoController;
	private ReservasController reservasController = new ReservasController();
	private CartaController cartaController;
	private LoginController loginController;

	// MODEL
	private MainModel model = new MainModel();

	// VISTA

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

		model.empleadoProperty().addListener((o, ov, nv) -> {

			if (nv != null) {
				model.setNombreEmpleado(model.getEmpleado().getNombre());
				model.setFoto((model.getEmpleado().getFoto() != null)
						? new Image(new ByteArrayInputStream(model.getEmpleado().getFoto()))
						: new Image(getClass().getResourceAsStream("/images/unknown_person.jpg")));

				empleadoImageView.imageProperty().bind(model.fotoProperty());
				empleadoLabel.textProperty().bind(model.nombreEmpleadoProperty());
			}

		});

		clockTile = TileBuilder.create().skinType(SkinType.CLOCK).textSize(TextSize.BIGGER).title("RELOJ BARGANIZER")
				.prefSize(TILE_WIDTH, TILE_HEIGHT).text("Hora local canaria").dateVisible(true).locale(Locale.US)
				.running(true).build();

		widgetBox.getChildren().addAll(clockTile);

		empleadoImageView.setImage(new Image(getClass().getResourceAsStream("/images/unknown_person.jpg")));
		root.setCenter(inicioController.getView());

	}
	/**
	 * Botón que llama al controlador de gestión de Carta
	 * @param event
	 */
	@FXML
	void onCartasAction(ActionEvent event) {
		try {
			cartaController = new CartaController();
			root.setCenter(cartaController.getView());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Label que al clickear cierra la sesión del usuario
	 * @param event
	 */
	@FXML
	void onCerrarSesionAction(MouseEvent event) {

		Stage stage = (Stage) inicioButton.getScene().getWindow();
		stage.close();

		try {
			loginController = new LoginController();

			stage.setTitle("BARGANIZER");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/barganizer.PNG")));
			stage.setScene(new Scene(loginController.getView()));
			stage.getScene().getStylesheets().setAll("/css/mainView.css");

			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Botón que llama al controlador de gestión de Empleado
	 * @param event
	 */
	@FXML
	void onEmpleadosAction(ActionEvent event) {
		try {
			empleadoController = new EmpleadoController();
			root.setCenter(empleadoController.getView());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Botón que llama al controlador de gestión de Inicio
	 * @param event
	 */
	@FXML
	void onInicioAction(ActionEvent event) {
		try {
			inicioController = new InicioController();
			root.setCenter(inicioController.getView());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Botón que llama al controlador de gestión de Mesa
	 * @param event
	 */
	@FXML
	void onMesasAction(ActionEvent event) {
		try {
			mesasController = new MesasController();
			root.setCenter(mesasController.getView());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Botón que llama al controlador de gestión de Reserva
	 * @param event
	 */
	@FXML
	void onReservasAction(ActionEvent event) {

		root.setCenter(reservasController.getView());

	}

	public MainModel getModel() {
		return model;
	}

}
