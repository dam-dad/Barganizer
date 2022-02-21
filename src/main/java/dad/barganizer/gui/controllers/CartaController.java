package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.beansprop.CartaProp;
import dad.barganizer.beansprop.PlatoProp;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.gui.models.CartaModel;
import dad.barganizer.thread.HiloEjecutador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class CartaController implements Initializable {

	private CartaModel model = new CartaModel();

	@FXML
	private Button addCartaButton;

	@FXML
	private ImageView addCartaImgView;

	@FXML
	private JFXListView<CartaProp> cartasList;

	@FXML
	private Label defaultLabel;

	@FXML
	private Button delCartaButton;

	@FXML
	private ImageView delCartaImgView;

	@FXML
	private VBox platosBox;

	@FXML
	private FlowPane platosFlow;

	@FXML
	private BorderPane view;
	
	@FXML
	private Button addPlatoButton;
	
	@FXML
	private Button delPlatoButton;
	
	@FXML
	private ImageView addPlatoImg;
	
	@FXML
	private ImageView delPlatoImg;

	@FXML
	void onAddCartaAction(ActionEvent event) {

	}

	@FXML
	void onDelCartaAction(ActionEvent event) {

	}
	
	@FXML
	void onAddPlatoAction(ActionEvent event) {
		
	}
	
	@FXML
	void onDelPlatoAction(ActionEvent event) {
		
	}
	
	public CartaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CartaView.fxml"));
		loader.setController(this);
		loader.load();
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Bindings y Listeners
		cartasList.itemsProperty().bind(model.listaProperty());

		model.cartaSeleccionadaProperty().addListener((o, ov, nv) -> {
			if (nv == null) {
				platosBox.getChildren().remove(platosFlow);
				platosBox.getChildren().add(defaultLabel);
			}
			if (nv != null) {
				platosFlow.getChildren().clear(); // Limpiamos los nodos de platos
				platosBox.getChildren().remove(defaultLabel); // Quitamos la label de no-seleccionado del VBox
				// Almacenamos los platos de cada carta en el flowpane para ser mostrados con
				// Tiles
				for (Plato plato : nv.getListaPlatos()) {
					platosFlow.getChildren().add(new ImageTile(plato));
				}
			}

		});

		cartasList.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			if (nv != null) {
				model.setCartaSeleccionada(nv);
				prepararNodosPlatos();
			}
		});

		// Tareas de inicialización. Las usaremos para inicializar las cartas de la
		// lista.
		BarganizerTasks tareas = new BarganizerTasks();

		tareas.getInicializarCartaTask().setOnSucceeded(e -> {
			ObservableList<Carta> lista = tareas.getInicializarCartaTask().getValue();

			ObservableList<CartaProp> props = FXCollections.observableArrayList();
			for (Carta carta : lista) {
				props.add(new CartaProp(carta));
			}

			model.setLista(props);
		});

		new HiloEjecutador(App.semaforo, tareas.getInicializarCartaTask()).start();
		
		// Iconos de los botones
		addCartaImgView.setImage(new Image(getClass().getResourceAsStream("/images/carta/CartaAdd.png")));
		delCartaImgView.setImage(new Image(getClass().getResourceAsStream("/images/carta/CartaDel.png")));
		addPlatoImg.setImage(new Image(getClass().getResourceAsStream("/images/plato/AddPlato.png")));
		delPlatoImg.setImage(new Image(getClass().getResourceAsStream("/images/plato/DelPlato.png")));
	}
	
	public BorderPane getView() {
		return view;
	}
	
	private void prepararNodosPlatos() {
		ObservableList<Node> l = platosFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();
				
				model.setPlatoSeleccionado(new PlatoProp((Plato)imageTileClickeado.getReferencia()));
				
				if (ev.getClickCount() >= 2) {
					// Si se hace doble click en un plato, se mostrará el formulario de modificación
					System.out.println("Doble click");
					System.out.println(((Plato)imageTileClickeado.getReferencia()).getNombre());
				}

			});
		}
	}

}
