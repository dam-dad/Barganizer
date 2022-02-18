package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.db.BarganizerDB;
import dad.barganizer.db.beans.Bebida;
import dad.barganizer.gui.models.InicioModel;
import eu.hansolo.tilesfx.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class InicioController implements Initializable {

	// Models
	private InicioModel model = new InicioModel();
	
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

		model.listaBebidasProperty().addListener((o, ov, nv) -> {
			System.out.println("OV: " + ov + " --- NV: " + nv);
		});
		
		inicializarEnBackground();
		
		// Listener 
		

	}

	public VBox getView() {
		return view;
	}
	
	private void inicializarEnBackground() {
		
		Task<ObservableList<Bebida>> inicializarBebidas = new Task<ObservableList<Bebida>>() {
			
			@Override
			protected ObservableList<Bebida> call() throws Exception {
				App.getBARGANIZERDB().getSes().beginTransaction();
				List<Bebida> listaBebidas = App.getBARGANIZERDB().listarBebidas();
				App.getBARGANIZERDB().getSes().getTransaction().commit();
				
				return FXCollections.observableArrayList(listaBebidas);
			}
		};
		
		inicializarBebidas.setOnSucceeded(e -> {
			ObservableList<Bebida> res = inicializarBebidas.getValue();
			model.setListaBebidas(res);
			
			for (Bebida bebida : res) {
				bebidasFlow.getChildren().add(new ImageTile(bebida.getFoto(), bebida.getNombre()).getTile());
			}
		});
		
		inicializarBebidas.setOnFailed(e -> {
			System.err.println("Inicialización de bebidas fallida: " + e.getSource().getException());
		});
		
		
		
		new Thread(inicializarBebidas).start();
		
	}

}
