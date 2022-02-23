package dad.barganizer.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.beansprop.CartaProp;
import dad.barganizer.beansprop.PlatoProp;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.gui.models.CartaModel;
import dad.barganizer.thread.HiloEjecutador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CartaController implements Initializable {

	private ModificarPlatoController modificarPlatoController;
	private AddPlatoController addPlatoController;
	private Stage modificarPlatoStage;
	private Stage addPlatoStage;
	private AddCartaController addCartaController;
	private Stage addCartaStage;

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
	private HBox platosButtonBox;

	@FXML
	private ImageView addPlatoImg;

	@FXML
	private ImageView delPlatoImg;

	public CartaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CartaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Bindings y Listeners
		cartasList.itemsProperty().bind(model.listaProperty());

		/* Listener para detectar cambios en la propiedad de carta seleccionada */
		model.cartaSeleccionadaProperty().addListener((o, ov, nv) -> {
			System.out.println("CARTA SELECCIONADA --- " + ov + "---NV: " + nv);
			if (nv == null) {
				model.setCartaCheckSeleccionada(false);
			} else {
				model.setCartaCheckSeleccionada(true);
			}

		});

		/* Listener para detectar cambios en la lista */
		model.listaProperty().addListener((o, ov, nv) -> {
			System.out.println("LISTAPROPERTY --- " + ov + "---NV: " + nv);
			
			if (model.getIndiceAnterior() > -1 && cartasList.getSelectionModel().getSelectedIndex() == -1) {
				cartasList.getSelectionModel().select(model.getIndiceAnterior());
			}
			if (model.getCartaSeleccionada() != null) {
				for (CartaProp cartaProp : nv) {
					if (cartaProp.getNombre().equals(model.getCartaSeleccionada().getNombre())) {
						platosFlow.getChildren().clear();
						for (Plato p : cartaProp.getListaPlatos()) {
							platosFlow.getChildren().add(new ImageTile(p));
						}
					}
				}

				prepararNodosPlatos();
			}

		});

		/*
		 * Controlamos los cambios en el índice de la lista para así decidir de qué
		 * forma tratar el mostrado de tiles.
		 */
		cartasList.getSelectionModel().selectedIndexProperty().addListener((o, ov, nv) -> {
			System.out.println("CARTASLIST SELECTION MODEL SELECTED INDEX OV: --- " + ov + "---NV: " + nv);
			if (nv.intValue() > -1) {
				model.setImageTileClickeado(null); // Siempre que se cambie de lista, se deseleccionará el último tile
													// clickeado
				// Cambiamos la carta seleccionada a la indicada en el índice
				model.setCartaSeleccionada(
						cartasList.getItems().get(cartasList.getSelectionModel().getSelectedIndex()));
				model.setCartaCheckSeleccionada(true); // Propertie que asegura que la carta está seleccionada

				// Guardamos una referencia al índice clickeado
				model.setIndiceAnterior(nv.intValue());

				// Refrescamos los tiles según la carta seleccionada

				platosFlow.getChildren().clear(); // Limpiamos los nodos de platos
				platosBox.getChildren().clear();
				platosBox.getChildren().addAll(platosFlow, platosButtonBox);
				// Almacenamos los platos de cada carta en el flowpane para ser mostrados con
				// Tiles
				for (Plato plato : cartasList.getItems().get(nv.intValue()).getListaPlatos()) {
					platosFlow.getChildren().add(new ImageTile(plato));
				}

				prepararNodosPlatos();

			} else {
				// Si no hay carta seleccionada, limpiamos los nodos
				model.setCartaSeleccionada(null);
				model.setCartaCheckSeleccionada(false);
				platosBox.getChildren().clear();
				// Restablecemos la label y el panel de botones
				platosBox.getChildren().addAll(defaultLabel, platosButtonBox);

				// El índice anterior se mantendrá, en caso de que un cambio en el índice de la
				// carta sea
				// debido a una operación de refresco poder actuar y controlar el mostrado de
				// tiles
				// ante esas situaciones
			}

		});

		platosBox.setOnMouseClicked(e -> {
			if (model.getImageTileClickeado() != null) {
				model.getImageTileClickeado().setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				model.setImageTileClickeado(null);
				model.setImageTileCheckSeleccionada(false);
			}
		});

		platosFlow.setOnMouseClicked(e -> {
			if (model.getImageTileClickeado() != null) {
				model.getImageTileClickeado().setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				model.setImageTileClickeado(null);
				model.setImageTileCheckSeleccionada(false);
			}
		});

		model.imageTileClickeadoProperty().addListener((o, ov, nv) -> {
			if (nv != null) {
				model.setImageTileCheckSeleccionada(true);
			}
			if (ov != null && nv != null) {
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);

			}
			if (nv == null) {
				model.setImageTileCheckSeleccionada(false);
			}
		});

		// Iconos de los botones
		addCartaImgView.setImage(new Image(getClass().getResourceAsStream("/images/carta/CartaAdd.png")));
		delCartaImgView.setImage(new Image(getClass().getResourceAsStream("/images/carta/CartaDel.png")));
		addPlatoImg.setImage(new Image(getClass().getResourceAsStream("/images/plato/AddPlato.png")));
		delPlatoImg.setImage(new Image(getClass().getResourceAsStream("/images/plato/DelPlato.png")));

		addPlatoButton.disableProperty().bind(model.cartaCheckSeleccionadaProperty().not());
		delPlatoButton.disableProperty().bind(
				model.cartaCheckSeleccionadaProperty().not().or(model.imageTileCheckSeleccionadaProperty().not()));
		delCartaButton.disableProperty().bind(model.cartaCheckSeleccionadaProperty().not());
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

	}

	public BorderPane getView() {
		return view;
	}

	void onListarNodosAction(ActionEvent e) {
		prepararNodosPlatos();
	}

	private void prepararNodosPlatos() {
		ObservableList<Node> l = platosFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();
				model.setImageTileClickeado(imageTileClickeado);
				model.setPlatoSeleccionado(new PlatoProp((Plato) imageTileClickeado.getReferencia()));
				imageTileClickeado.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);
				System.out.println(((Plato) imageTileClickeado.getReferencia()).getNombre());
				if (ev.getClickCount() >= 2) {
					// Si se hace doble click en un plato, se mostrará el formulario de modificación
					System.out.println("Doble click");

					try {
						modificarPlatoController = new ModificarPlatoController();
						modificarPlatoController.getModel()
								.setPlatoModificar(new PlatoProp((Plato) imageTileClickeado.getReferencia()));
						modificarPlatoStage = new Stage();
						modificarPlatoStage.setTitle("Barganizer - Modificar plato");
						modificarPlatoStage.setScene(new Scene(modificarPlatoController.getView()));
						modificarPlatoStage.getIcons().add(App.primaryStage.getIcons().get(0));
						modificarPlatoStage.getScene().getStylesheets().setAll("/css/mainView.css");
						modificarPlatoStage.initOwner(App.primaryStage);
						modificarPlatoStage.initModality(Modality.APPLICATION_MODAL);
						modificarPlatoStage.showAndWait();

						// Una vez cerrada la modificación de platos, se actualizará la lista de los
						// mismos
						imageTileClickeado.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
						redeclararTask();
						inicializarCartaTask.setOnSucceeded(e -> {
							model.setLista(inicializarCartaTask.getValue());
						});

						new HiloEjecutador(App.semaforo, inicializarCartaTask).start();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});
		}
	}

	private void redeclararTask() {
		inicializarCartaTask = new Task<ObservableList<CartaProp>>() {

			@Override
			protected ObservableList<CartaProp> call() throws Exception {
				List<Carta> listaCartas = FuncionesDB.listarCarta(App.getBARGANIZERDB().getSes());
				List<CartaProp> listaProps = new ArrayList<>();
				for (Carta carta : listaCartas) {
					listaProps.add(new CartaProp(carta));
				}
				return FXCollections.observableArrayList(listaProps);
			}
		};

		borrarPlatoTask = new Task<Void>() {

			protected Void call() throws Exception {
				FuncionesDB.eliminarPlato(App.getBARGANIZERDB().getSes(),
						(Plato) model.getImageTileClickeado().getReferencia());

				return null;
			};
		};

		borrarCartaTask = new Task<Void>() {
			protected Void call() throws Exception {
				FuncionesDB.eliminarPlatosCarta(App.getBARGANIZERDB().getSes(),
						model.getCartaSeleccionada().getReferencia());
				FuncionesDB.eliminarCarta(App.getBARGANIZERDB().getSes(), model.getCartaSeleccionada().getReferencia());

				return null;
			};
		};
	}

	private Task<ObservableList<CartaProp>> inicializarCartaTask = new Task<ObservableList<CartaProp>>() {

		@Override
		protected ObservableList<CartaProp> call() throws Exception {
			List<Carta> listaCartas = FuncionesDB.listarCarta(App.getBARGANIZERDB().getSes());
			List<CartaProp> listaProps = new ArrayList<>();
			for (Carta carta : listaCartas) {
				listaProps.add(new CartaProp(carta));
			}
			return FXCollections.observableArrayList(listaProps);
		}
	};

	private Task<Void> borrarPlatoTask = new Task<Void>() {

		protected Void call() throws Exception {
			FuncionesDB.eliminarPlato(App.getBARGANIZERDB().getSes(),
					(Plato) model.getImageTileClickeado().getReferencia());

			return null;
		};
	};

	private Task<Void> borrarCartaTask = new Task<Void>() {
		protected Void call() throws Exception {
			FuncionesDB.eliminarPlatosCarta(App.getBARGANIZERDB().getSes(),
					model.getCartaSeleccionada().getReferencia());
			FuncionesDB.eliminarCarta(App.getBARGANIZERDB().getSes(), model.getCartaSeleccionada().getReferencia());

			return null;
		};
	};

	@FXML
	void onAddCartaAction(ActionEvent event) {

		try {
			addCartaController = new AddCartaController();
			addCartaStage = new Stage();
			addCartaStage.setTitle("Barganizer - Añadir carta");
			addCartaStage.setScene(new Scene(addCartaController.getView()));
			addCartaStage.getIcons().add(App.primaryStage.getIcons().get(0));
			addCartaStage.getScene().getStylesheets().setAll("/css/mainView.css");
			addCartaStage.initOwner(App.primaryStage);
			addCartaStage.initModality(Modality.APPLICATION_MODAL);
			addCartaStage.showAndWait();

			// Refresco de la lista
			BarganizerTasks tareas = new BarganizerTasks();
			
			tareas.getInicializarCartaPropTask().setOnSucceeded(e -> {
				
				ObservableList<CartaProp> l = tareas.getInicializarCartaPropTask().getValue();
				System.out.println("INICIALIZARCARTAPROPTASK: " + l);
				model.setLista(l);
			});
//			redeclararTask();
//			inicializarCartaTask.setOnSucceeded(e -> {
//				model.setLista(inicializarCartaTask.getValue());
//			});

			new HiloEjecutador(App.semaforo, tareas.getInicializarCartaPropTask()).start();

		} catch (IOException e) {
			App.error("Error", "Excepción cargando controlador", "Detalles: " + e.getMessage());
		}

	}

	@FXML
	void onDelCartaAction(ActionEvent event) {
		if (cartasList.getSelectionModel().getSelectedIndex() > -1 && model.getCartaSeleccionada() != null) {
			if (App.confirm("Confirmación borrado de carta", "¿Seguro que desea eliminar esta carta?",
					"Esto eliminará también sus platos relacionados. ¿Continuar?")) {

				borrarCartaTask.setOnSucceeded(e -> {
					App.info("Éxito", "Carta eliminada", "Se ha eliminado la carta correctamente");

					
				});

				redeclararTask();
				inicializarCartaTask.setOnSucceeded(ev -> {
					model.setLista(inicializarCartaTask.getValue());
					model.setCartaSeleccionada(null);
					model.setImageTileClickeado(null);
					model.setIndiceAnterior(-1);
				});
				new HiloEjecutador(App.semaforo, borrarCartaTask).start();
				new HiloEjecutador(App.semaforo, inicializarCartaTask).start();

				prepararNodosPlatos();

			}

		} else {
			App.warning("Advertencia", "Carta no seleccionada",
					"Debe seleccionar una carta previamente para poder ser eliminada");
		}
	}

	@FXML
	void onAddPlatoAction(ActionEvent event) {
		try {
			addPlatoController = new AddPlatoController();
			addPlatoController.getModel().setCartaSeleccionada(cartasList.getItems().get(cartasList.getSelectionModel().getSelectedIndex()).getReferencia());
			BarganizerTasks tareas = new BarganizerTasks();

			tareas.getObtenerTiposPlatoTask().setOnSucceeded(e -> {
				List<Carta> listaAdd = new ArrayList<>();
				for (CartaProp cp : model.getLista()) {
					listaAdd.add(cp.getReferencia());
				}
				addPlatoController.getModel().setListaCartas(FXCollections.observableArrayList(listaAdd));
				addPlatoController.getModel().setListaTipos(tareas.getObtenerTiposPlatoTask().getValue());
			});

			new HiloEjecutador(App.semaforo, tareas.getObtenerTiposPlatoTask()).start();

			addPlatoStage = new Stage();
			addPlatoStage.setTitle("Barganizer - Añadir plato");
			addPlatoStage.setScene(new Scene(addPlatoController.getView()));
			addPlatoStage.getIcons().add(App.primaryStage.getIcons().get(0));
			addPlatoStage.getScene().getStylesheets().setAll("/css/mainView.css");
			addPlatoStage.initOwner(App.primaryStage);
			addPlatoStage.initModality(Modality.APPLICATION_MODAL);
			addPlatoStage.showAndWait();
			
			// Al añadirse el plato se deberá refrescar la lista
			redeclararTask();
			inicializarCartaTask.setOnSucceeded(e -> {
				model.setLista(inicializarCartaTask.getValue());
				cartasList.getSelectionModel().select(model.getCartaSeleccionada());
			});

			new HiloEjecutador(App.semaforo, inicializarCartaTask).start();
			
		} catch (IOException e) {
			App.error("Error - Excepción", "Error cargando controlador", "Detalles: " + e.getMessage());
		}

	}

	@FXML
	void onDelPlatoAction(ActionEvent event) {

		if (model.getImageTileClickeado() != null) {
			// Si se clickeó en un plato, procedemos a su eliminación
			if (App.confirm("Barganzier - Borrar plato",
					"Eliminar plato " + ((Plato) model.getImageTileClickeado().getReferencia()).getNombre(),
					"¿Estás seguro?")) {

				// Obtenemos el índice de la carta de la lista para posteriormente seleccionarla
				// después del borrado
				model.setIndiceAnterior(cartasList.getSelectionModel().getSelectedIndex());
				redeclararTask();
				borrarPlatoTask.setOnSucceeded(e -> {
					model.setImageTileClickeado(null);
					model.setPlatoSeleccionado(null);
					model.setCartaSeleccionada(model.getLista().get(model.getIndiceAnterior()));
					
				});

				borrarPlatoTask.setOnFailed(e -> {
					App.error("Error", "Eliminación de plato",
							"Se ha producido un error eliminando el plato. Es posible que se encuentre activo en una comanda.\nDetalles: "
									+ e.getSource().getException().getMessage());
				});

				inicializarCartaTask.setOnSucceeded(e -> {

					ObservableList<CartaProp> res = inicializarCartaTask.getValue();
					System.out.println("INICIALIZACIÓN CARTA POST-BORRADO: LISTA: " + res);
					model.setLista(res);
//					App.info("Éxito", "Plato eliminado", "Se ha eliminado el plato con éxito");
				});

				inicializarCartaTask.setOnFailed(e -> {
					App.error("Error", "Error re-listando la carta",
							"Se ha producido un error re-listando la carta. Detalles: " + e.getSource().getMessage());
				});

				new HiloEjecutador(App.semaforo, borrarPlatoTask).start();
				// Al borrar el plato ejecutamos de nuevo la tarea que actualiza la carta

				new HiloEjecutador(App.semaforo, inicializarCartaTask).start();

			}

		}
	}

}
