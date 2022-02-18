package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import dad.barganizer.ImageTile;
import dad.barganizer.db.BarganizerDB;
import dad.barganizer.db.beans.Bebida;
import eu.hansolo.tilesfx.Tile;
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

		BarganizerDB conexion = new BarganizerDB();
		conexion.getSes().beginTransaction();
		
		List<Bebida> listaBebidas = conexion.getSes().createQuery("FROM Bebida").list();
		
		
		conexion.getSes().getTransaction().commit();
		try {
			ImageTile imgTile = new ImageTile(getClass().getResourceAsStream("/images/unknown_person.jpg").readAllBytes(),
					"Ejemplo");
			ImageTile imgTile2 = new ImageTile(getClass().getResourceAsStream("/images/unknown_person.jpg").readAllBytes(),
					"Ejemplo");
			
			for (Bebida bebida : listaBebidas) {
				System.out.println(bebida.getNombre());
				bebidasFlow.getChildren().add(new ImageTile(bebida.getFoto(),
					bebida.getNombre()).getTile());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public VBox getView() {
		return view;
	}

}
