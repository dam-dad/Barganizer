package dad.barganizer.gui.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import dad.barganizer.App;
import dad.barganizer.ImageTile;
import dad.barganizer.beansprop.ComandaProp;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Comanda;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.gui.models.InicioModel;
import dad.barganizer.reports.ComandasDataSource;
import dad.barganizer.thread.HiloEjecutador;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class InicioController implements Initializable {

	private static final String JRXML_TICKET_MODEL = "/report_models/Ticket.jrxml";

	// Models
	private InicioModel model = new InicioModel();

	@FXML
	private FlowPane bebidasFlow;

	@FXML
	private Tab bebidasTab;

	@FXML
	private JFXComboBox<Carta> cartaCombo;

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

	@FXML
	private TableView<ComandaProp> comandasTable;

	@FXML
	private TableColumn<ComandaProp, String> platoColumn;

	@FXML
	private TableColumn<ComandaProp, Number> precioColumn;

	@FXML
	private TableColumn<ComandaProp, Number> cantidadColumn;

	@FXML
	private TableColumn<ComandaProp, Void> accionesColumn;

	@FXML
	private Button generarTicketButton;

	@FXML
	private Label totalComandaLabel;

	public InicioController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/** Eventos onMouseClicked de los FlowPanes **/
		bebidasFlow.setOnMouseClicked(e -> {

			if (model.getTilePlatoSeleccionado() != null) {
				model.getTilePlatoSeleccionado().setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				model.getTilePlatoSeleccionado().setActive(false);
				model.setTilePlatoSeleccionado(null);
			}

		});

		mesasFlow.setOnMouseClicked(e -> {

			if (model.getTileMesaSeleccionada() != null) {
				model.getTileMesaSeleccionada().setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				model.setTileMesaSeleccionada(null);
				model.setComandasMesa(null);
			}

		});

		platosFlow.setOnMouseClicked(e -> {
			if (model.getTilePlatoSeleccionado() != null) {
				model.getTilePlatoSeleccionado().setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				model.getTilePlatoSeleccionado().setActive(false);
				model.setTilePlatoSeleccionado(null);
			}
		});

		/* Listeners de los tipos de tiles seleccionables del modelo */

		model.tileMesaSeleccionadaProperty().addListener((o, ov, nv) -> {
			System.out.println("TileMesaSeleccionada --- OV: " + ov + " --- NV: " + nv);
			if (ov != null && nv != null) {
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
			}

			if (nv != null) {
				Mesa ref = (Mesa) nv.getReferencia();
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);

				redeclararTasks();

				new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();
			}

			if (nv == null) {
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				totalComandaLabel.setText("Precio total: 0.0€");
			}

		});

		model.tilePlatoSeleccionadoProperty().addListener((o, ov, nv) -> {
			System.out.println("TILE-PLATO-SELEC-: OV: " + ov + " --- NV:" + nv);
			// Si no había ningún tile de plato seleccionado previamente
			if (ov == null && nv != null) {
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);
			}

			// Si se cambia de tile de plato seleccionado
			if (ov != null && nv != null) {
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);
			}

		});

		model.comandasMesaProperty().addListener((o, ov, nv) -> {
			System.out.println("Comandas mesa --- OV: " + ov + "--- NV: " + nv);
		});

		cartaCombo.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

			if (nv != null) {
				listarPlatosDesdeCarta(nv);
				listarEntrantesDesdeCarta(nv);
				listarPostresDesdeCarta(nv);
			}

		});

		// Tabla de comandas
		// Cell Values
		platoColumn.setCellValueFactory(v -> v.getValue().nombrePlatoProperty());
		precioColumn.setCellValueFactory(v -> v.getValue().precioUnidadProperty());
		cantidadColumn.setCellValueFactory(v -> v.getValue().cantidadProperty());

		// Botón en una columna
		addButtonColumn();

		comandasTable.itemsProperty().bind(model.comandasMesaProperty());
		BooleanExpression expr = Bindings
				.when(model.comandasMesaProperty().isNull().or(model.comandasMesaProperty().emptyProperty())).then(true)
				.otherwise(false);

		generarTicketButton.disableProperty().bind(expr);

		inicializarEnBackground();

	}

	public VBox getView() {
		return view;
	}

	private void inicializarEnBackground() {

		// BarganizerTasks nos ayudará con la ejecución de tareas en segundo plano
		BarganizerTasks tareas = new BarganizerTasks();

		/*
		 * Obtenemos la tarea definida como "Inicializar bebidas" y asignamos sus
		 * acciones cuando se ejecuta correctamente y cuando falla.
		 */
		tareas.getInicializarBebidasTask().setOnSucceeded(e -> {
			ObservableList<Plato> res = tareas.getInicializarBebidasTask().getValue();
			model.setListaBebidas(res);

			for (Plato bebida : res) {
				bebidasFlow.getChildren().add(new ImageTile(bebida));
			}

			/*
			 * Controlamos los nodos almacenados en las bebidas para asignarle a cada uno su
			 * método onMouseClicked, que nos ayudará a añadir las bebidas a la comanda de
			 * una mesa previamente seleccionada en el tabpane de mesas.
			 * 
			 * Realizaremos el mismo procedimiento con el resto de tipos de platos
			 */
			prepararNodosBebidas();

		});

		tareas.getInicializarBebidasTask().setOnFailed(e -> {
			System.err.println("Inicialización de bebidas fallida: ");
			e.getSource().getException().printStackTrace();
		});

		// Declaración de métodos onSucceed y onFailed de la tarea de inicialización de
		// mesas
		tareas.getObtenerMesasActivasTask().setOnSucceeded(e -> {
			ObservableList<Mesa> res = tareas.getObtenerMesasActivasTask().getValue();
			model.setListaMesas(res);

			for (Mesa mesa : res) {
				mesasFlow.getChildren().add(new ImageTile(mesa));
			}

			/* Obtenemos los nodos del flowpane de mesas */
			ObservableList<Node> l = mesasFlow.getChildren();
			for (Node node : l) {
				node.setOnMouseClicked(ev -> {
					ImageTile imageTileClickeado = (ImageTile) ev.getSource();
					Mesa seleccionada = (Mesa) imageTileClickeado.getReferencia();
					System.out.println("Mesa seleccionada: " + seleccionada.getNumero());

					model.setTileMesaSeleccionada(imageTileClickeado);

				});
			}
		});

		tareas.getInicializarMesasTask().setOnFailed(e -> {
			System.err.println("Inicialización de mesas fallida: ");
			e.getSource().getException().printStackTrace();
		});

		// Carta
		tareas.getInicializarCartaTask().setOnSucceeded(e -> {
			ObservableList<Carta> res = tareas.getInicializarCartaTask().getValue();
			model.setListaCartas(res);

			for (Carta carta : res) {
				cartaCombo.getItems().add(carta);
			}

			cartaCombo.getSelectionModel().select(0);

		});

		// Ejecución de tareas

//		new Thread(tareas.getInicializarBebidasTask()).start();
//		new Thread(tareas.getInicializarMesasTask()).start();
//		new Thread(tareas.getInicializarCartaTask()).start();

		// Hilo ejecutador de tareas
		new HiloEjecutador(App.semaforo, tareas.getInicializarBebidasTask()).start();
		new HiloEjecutador(App.semaforo, tareas.getObtenerMesasActivasTask()).start();
		new HiloEjecutador(App.semaforo, tareas.getInicializarCartaTask()).start();

	}

	/**
	 * Este método se encargará de listar los platos según el tipo de carta
	 * seleccionada
	 **/
	private void listarPlatosDesdeCarta(Carta c) {
		BarganizerTasks tareas = new BarganizerTasks(c);

		tareas.getObtenerPlatosCartaTask().setOnSucceeded(e -> {
			ObservableList<Plato> res = tareas.getObtenerPlatosCartaTask().getValue();

			model.setListaPlatos(res);
			platosFlow.getChildren().clear();
			for (Plato plato : res) {
				platosFlow.getChildren().add(new ImageTile(plato));
			}

			prepararNodosPlatos();
		});

		tareas.getObtenerPlatosCartaTask().setOnFailed(e -> {
			System.err.println("Error inicializando los platos de la carta.");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(App.semaforo, tareas.getObtenerPlatosCartaTask()).start();
	}

	/**
	 * Este método se encargará de listar los entrantes según la carta seleccionada
	 **/
	private void listarEntrantesDesdeCarta(Carta c) {
		BarganizerTasks tareas = new BarganizerTasks(c);

		tareas.getObtenerEntrantesTask().setOnSucceeded(e -> {

			ObservableList<Plato> res = tareas.getObtenerEntrantesTask().getValue();

			model.setListaEntrantes(res);
			entrantesFlow.getChildren().clear();

			for (Plato plato : res) {
				entrantesFlow.getChildren().add(new ImageTile(plato));
			}

			prepararNodosEntrantes();
		});

		tareas.getObtenerEntrantesTask().setOnFailed(e -> {
			System.err.println("Error obteniendo entrantes desde carta: ");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(App.semaforo, tareas.getObtenerEntrantesTask()).start();
	}

	/**
	 * Este método se encargará de listar los postres según la carta seleccionada
	 **/
	private void listarPostresDesdeCarta(Carta c) {
		BarganizerTasks tareas = new BarganizerTasks(c);

		tareas.getObtenerPostresTask().setOnSucceeded(e -> {
			ObservableList<Plato> res = tareas.getObtenerPostresTask().getValue();

			model.setListaPostres(res);
			postresFlow.getChildren().clear();

			for (Plato plato : res) {
				postresFlow.getChildren().add(new ImageTile(plato));
			}

			prepararNodosPostres();
		});

		tareas.getObtenerPostresTask().setOnFailed(e -> {
			System.err.println("Error obteniendo postres desde carta: ");
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(App.semaforo, tareas.getObtenerPostresTask()).start();
	}

	@FXML
	void onGenerarTicketAction(ActionEvent event) {

		// Sólo Windows
		String rutaUsuario = System.getenv("USERPROFILE");
		System.out.println(rutaUsuario);
		File directorioReportes = new File(rutaUsuario + "\\tickets");

		// Si el directorio donde se generarán los reportes por defecto ya está
		// creado...
		if (directorioReportes.exists()) {

			JasperReport report;
			try {
				// Compilamos el informe
				report = JasperCompileManager
						.compileReport(InicioController.class.getResourceAsStream(JRXML_TICKET_MODEL));

				// Inicialización de un mapa de parámetros para el informe
				Map<String, Object> parameters = new HashMap<String, Object>();

				// Generación del informe (combinamos el informe compilado con los datos)
				JasperPrint print = JasperFillManager.fillReport(report, parameters,
						new ComandasDataSource(model.getComandasMesa()));

				// exporta el informe a un fichero PDF
				System.out.println(LocalDateTime.now());
				String rutaGuardado = directorioReportes.getAbsolutePath() + "\\Ticket"
						+ LocalDateTime.now().toString().replace('-', '_').replace(':', '_').replace('.', '_') + ".pdf";
				JasperExportManager.exportReportToPdfFile(print, rutaGuardado);

				// Abre el archivo PDF generado con el programa predeterminado del sistema
				Desktop.getDesktop().open(new File(rutaGuardado));

				// Se limpia la comanda relacionada a esa mesa
				eliminarComandasMesaTask.setOnSucceeded(e -> {
					model.setTileMesaSeleccionada(null);
					model.getComandasMesa().clear();
				});

				new HiloEjecutador(App.semaforo, eliminarComandasMesaTask).start();

			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("ez");
				e.printStackTrace();
			}

		} else {
			directorioReportes.mkdir();
		}

	}

	private Task<ObservableList<ComandaProp>> obtenerComandasMesaTask = new Task<ObservableList<ComandaProp>>() {

		@Override
		protected ObservableList<ComandaProp> call() throws Exception {

			List<Comanda> listaComandas = FuncionesDB.listarComandasMesa(App.getBARGANIZERDB().getSes(),
					(Mesa) model.getTileMesaSeleccionada().getReferencia());

			List<ComandaProp> listaProps = new ArrayList<>();

			for (Comanda comanda : listaComandas) {
				listaProps.add(new ComandaProp(comanda));
			}

			return FXCollections.observableArrayList(listaProps);
		}
	};

	private Task<ObservableList<ComandaProp>> actualizarComandasMesaTask = new Task<ObservableList<ComandaProp>>() {

		@Override
		protected ObservableList<ComandaProp> call() throws Exception {

			FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(),
					(Mesa) model.getTileMesaSeleccionada().getReferencia(),
					(Plato) model.getTilePlatoSeleccionado().getReferencia(), 1);

			List<Comanda> listaComandas = FuncionesDB.listarComandasMesa(App.getBARGANIZERDB().getSes(),
					(Mesa) model.getTileMesaSeleccionada().getReferencia());

			List<ComandaProp> listaProps = new ArrayList<>();

			for (Comanda comanda : listaComandas) {
				listaProps.add(new ComandaProp(comanda));
			}

			return FXCollections.observableArrayList(listaProps);
		}
	};

	private Task<Void> eliminarComandasMesaTask = new Task<Void>() {
		protected Void call() throws Exception {
			FuncionesDB.eliminarComandasMesa(App.getBARGANIZERDB().getSes(),
					(Mesa) model.getTileMesaSeleccionada().getReferencia());

			return null;
		};
	};

	private Task<Void> insertarComandaMesa = new Task<Void>() {

		@Override
		protected Void call() throws Exception {
			FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(),
					(Mesa) model.getTileMesaSeleccionada().getReferencia(),
					(Plato) model.getTilePlatoSeleccionado().getReferencia(), 1);
			return null;
		};
	};

	private void redeclararTasks() {
		actualizarComandasMesaTask = new Task<ObservableList<ComandaProp>>() {

			@Override
			protected ObservableList<ComandaProp> call() throws Exception {
				App.getBARGANIZERDB().resetSesion();
				List<Comanda> listaComandas = FuncionesDB.listarComandasMesa(App.getBARGANIZERDB().getSes(),
						(Mesa) model.getTileMesaSeleccionada().getReferencia());

				List<ComandaProp> listaProps = new ArrayList<>();

				for (Comanda comanda : listaComandas) {
					listaProps.add(new ComandaProp(comanda));
				}

				return FXCollections.observableArrayList(listaProps);
			}
		};

		actualizarComandasMesaTask.setOnSucceeded(e -> {

			model.setComandasMesa(actualizarComandasMesaTask.getValue());
			double preciototal = 0;

			for (ComandaProp c : model.getComandasMesa()) {
				preciototal += (c.getCantidad() * c.getPrecioUnidad());
			}

			totalComandaLabel.setText("Precio total: " + preciototal + "€");
		});

		insertarComandaMesa = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(),
						(Mesa) model.getTileMesaSeleccionada().getReferencia(),
						(Plato) model.getTilePlatoSeleccionado().getReferencia(), 1);
				return null;
			};
		};

		eliminarComandasMesaTask = new Task<Void>() {
			protected Void call() throws Exception {
				FuncionesDB.eliminarComandasMesa(App.getBARGANIZERDB().getSes(),
						(Mesa) model.getTileMesaSeleccionada().getReferencia());

				return null;
			};
		};

	}

	private void addButtonColumn() {
		Callback<TableColumn<ComandaProp, Void>, TableCell<ComandaProp, Void>> cellFactory = new Callback<TableColumn<ComandaProp, Void>, TableCell<ComandaProp, Void>>() {

			@Override
			public TableCell<ComandaProp, Void> call(TableColumn<ComandaProp, Void> param) {
				final TableCell<ComandaProp, Void> celda = new TableCell<ComandaProp, Void>() {

					private Button quitarButton = new Button("Quitar");

					{

						quitarButton.setOnAction(e -> {
							ComandaProp comanda = getTableView().getItems().get(getIndex());

							// Acciones a realizar al clickear el botón
							FuncionesDB.eliminarComanda(App.getBARGANIZERDB().getSes(), comanda.getReferencia());
							redeclararTasks();

							new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();
						});

					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(quitarButton);
						}
					}
				};

				return celda;
			}
		};
		accionesColumn.setCellFactory(cellFactory);
	}

	private void prepararNodosBebidas() {
		ObservableList<Node> l = bebidasFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();
				Plato bebidaSeleccionada = (Plato) imageTileClickeado.getReferencia();
				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
						System.out.println("Doble click");
						Mesa mesaSeleccionada = (Mesa) model.getTileMesaSeleccionada().getReferencia();
//							FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(), mesaSeleccionada,
//									bebidaSeleccionada, 1);

						/* Actualizar la lista de comandas con la nueva comanda añadida */

						redeclararTasks();
						new HiloEjecutador(App.semaforo, insertarComandaMesa).start();

						new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

					}
				}

			});
		}
	}

	private void prepararNodosEntrantes() {
		ObservableList<Node> l = entrantesFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();
				Plato entranteSeleccionado = (Plato) imageTileClickeado.getReferencia();
				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
						System.out.println("Doble click");
						Mesa mesaSeleccionada = (Mesa) model.getTileMesaSeleccionada().getReferencia();
//							FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(), mesaSeleccionada,
//									bebidaSeleccionada, 1);

						/* Actualizar la lista de comandas con la nueva comanda añadida */

						redeclararTasks();
						new HiloEjecutador(App.semaforo, insertarComandaMesa).start();

						new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

					}
				}

			});
		}
	}

	private void prepararNodosPostres() {
		ObservableList<Node> l = postresFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();
				Plato postreSeleccionado = (Plato) imageTileClickeado.getReferencia();
				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
						System.out.println("Doble click");
						Mesa mesaSeleccionada = (Mesa) model.getTileMesaSeleccionada().getReferencia();
//							FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(), mesaSeleccionada,
//									bebidaSeleccionada, 1);

						/* Actualizar la lista de comandas con la nueva comanda añadida */

						redeclararTasks();
						new HiloEjecutador(App.semaforo, insertarComandaMesa).start();

						new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

					}
				}

			});
		}
	}

	private void prepararNodosPlatos() {
		ObservableList<Node> l = platosFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();
				Plato platoSeleccionado = (Plato) imageTileClickeado.getReferencia();
				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
						System.out.println("Doble click");
						Mesa mesaSeleccionada = (Mesa) model.getTileMesaSeleccionada().getReferencia();
//							FuncionesDB.insertarComanda(App.getBARGANIZERDB().getSes(), mesaSeleccionada,
//									bebidaSeleccionada, 1);

						/* Actualizar la lista de comandas con la nueva comanda añadida */

						redeclararTasks();
						new HiloEjecutador(App.semaforo, insertarComandaMesa).start();

						new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

					}
				}

			});
		}
	}

}
