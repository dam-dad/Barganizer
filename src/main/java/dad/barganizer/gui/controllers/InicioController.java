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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * InicioController es el controlador cuya vista se incrusta en el controlador
 * principal de la aplicación (MainController), y se encarga de proporcionarle
 * al usuario una interfaz 'easy-to-use' en la cual interactúa con los registros
 * de la base de datos, los cuales serán representados en forma de Tiles con la
 * ayuda de TilesFX.
 * 
 * Desde InicioController se podrán añadir platos a las mesas activas (mesas
 * ocupadas en el restaurante) y gestionar sus comandas. Se podrá añadir y
 * quitar elementos de la misma y generar un ticket en base al consumo de los
 * clientes. Una vez generado el ticket, la mesa se declara como inactiva y
 * desaparece de su FlowPane correspondiente.
 * 
 * Será posible filtrar los platos por carta, según la que desee el usuario. (A
 * excepción de las bebidas y los postres, que son considerados 'estáticos' y
 * por lo tanto podrán ser visualizados en todos los tipos de carta)
 * 
 * Se muestran los platos según su tipo, divididos en pestañas.
 **/
public class InicioController implements Initializable {

	/** Constante que define la ruta del modelo JRXML **/
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
		App.getBARGANIZERDB().resetSesion();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/*
		 * Eventos onMouseClicked de los FlowPanes, que nos ayudarán a deseleccionar los
		 * tiles que representan los platos si el usuario clickea en cualquier lugar de
		 * su FlowPane que no sea un nodo Tile
		 */
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

		/*
		 * Listener de los tipos de tiles seleccionables de las mesas, que nos ayudará a
		 * apuntar la selección de un tile de mesa a su correspondiente nuevo valor de
		 * selección.
		 */

		model.tileMesaSeleccionadaProperty().addListener((o, ov, nv) -> {
			if (ov != null && nv != null) {
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
			}

			if (nv != null) {
				nv.setBackgroundColor(ImageTile.TILE_SELECTED_COLOR);

				redeclararTasks();

				new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

			}

			if (nv == null) {
				ov.setBackgroundColor(ImageTile.TILE_DEFAULT_COLOR);
				totalComandaLabel.setText("Precio total: 0.0€");
			}

		});
		/*
		 * Listener de los tipos de tiles seleccionables de los platos, que nos ayudará
		 * a apuntar la selección de un tile de mesa a su correspondiente nuevo valor de
		 * selección.
		 */
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

	/**
	 * Este método es ejecutado en la inicialización del controlador, y nos ayudará
	 * a inicializar los valores de todos los flowpanes que contendrán los tiles que
	 * representan cada entidad de la base de datos. Nos apoyamos en una clase
	 * BarganizerTasks que nos brinda tareas de uso recurrente de la aplicación.
	 * Cada tare
	 **/
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

	/**
	 * Este método se encarga de generar los tickets y de guardarlos por defecto en
	 * el perfil del usuario. El nombre de cada ticket corresponde a su fecha y hora
	 * en el que fue generado. El modelo del ticket se genera gracias a la plantilla
	 * de JasperReports.
	 **/
	@FXML
	void onGenerarTicketAction(ActionEvent event) {

		// Sólo Windows. Los tickets se guardarán en el directorio del usuario, en la
		// carpeta tickets.
		String rutaUsuario = System.getenv("USERPROFILE");
		File directorioReportes = new File(rutaUsuario + "\\tickets");

		// Si el directorio donde se generarán los reportes por defecto ya está
		// creado...

		if (!directorioReportes.exists()) {
			directorioReportes.mkdir();
		}

		JasperReport report;
		try {
			// Compilamos el informe
			report = JasperCompileManager.compileReport(InicioController.class.getResourceAsStream(JRXML_TICKET_MODEL));

			// Inicialización de un mapa de parámetros para el informe
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("logoizq", getClass().getResourceAsStream("/images/barganizer.png"));
			parameters.put("logoder", getClass().getResourceAsStream("/images/plato/plato.png"));

			// Generación del informe (combinamos el informe compilado con los datos)
			JasperPrint print = JasperFillManager.fillReport(report, parameters,
					new ComandasDataSource(model.getComandasMesa()));

			// exporta el informe a un fichero PDF
			String rutaGuardado = directorioReportes.getAbsolutePath() + "\\Ticket"
					+ LocalDateTime.now().toString().replace('-', '_').replace(':', '_').replace('.', '_') + ".pdf";
			JasperExportManager.exportReportToPdfFile(print, rutaGuardado);

			// Abre el archivo PDF generado con el programa predeterminado del sistema
			Desktop.getDesktop().open(new File(rutaGuardado));
			redeclararTasks();
			Task<ObservableList<Mesa>> obtenerMesasActivasTask = new Task<ObservableList<Mesa>>() {

				@Override
				protected ObservableList<Mesa> call() throws Exception {

					List<Mesa> listaMesas = FuncionesDB.listarMesasActivas(App.getBARGANIZERDB().getSes());

					return FXCollections.observableArrayList(listaMesas);
				}
			};

			// Se limpia la comanda relacionada a esa mesa
			eliminarComandasMesaTask.setOnSucceeded(e -> {
				((Mesa) model.getTileMesaSeleccionada().getReferencia()).setActiva(false);
			});

			modificarMesaTask.setOnSucceeded(e -> {
				model.setTileMesaSeleccionada(null);
				model.getComandasMesa().clear();
			});

			obtenerMesasActivasTask.setOnSucceeded(e -> {
				ObservableList<Mesa> res = obtenerMesasActivasTask.getValue();
				mesasFlow.getChildren().clear();
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

			new HiloEjecutador(App.semaforo, eliminarComandasMesaTask).start();
			new HiloEjecutador(App.semaforo, modificarMesaTask).start();
			new HiloEjecutador(App.semaforo, obtenerMesasActivasTask).start();

		} catch (JRException e) {
			App.error("Error", "Error Jasper",
					"Se ha producido un error de tipo JRException. Detalles: " + e.getMessage());
		} catch (Exception e) {
			App.error("Error", "Excepción", "Se ha producido una excepción. Detalles: " + e.getMessage());
		}
	}

	/**
	 * Tarea que nos permite modificar la mesa seleccionada, a través de la
	 * referencia a su objeto mesa en el tile de mesa seleccionada.
	 **/
	private Task<Void> modificarMesaTask = new Task<Void>() {

		@Override
		protected Void call() throws Exception {

			FuncionesDB.modificarMesa(App.getBARGANIZERDB().getSes(),
					(Mesa) model.getTileMesaSeleccionada().getReferencia());
			return null;
		}
	};

	/**
	 * Tarea que nos permite actualizar la lista de comandas, utilizando como
	 * referencia la mesa seleccionada y el plato seleccionado
	 **/
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

	/**
	 * Este método se encarga de eliminar las comandas de una mesa, pasándole la
	 * mesa seleccionada como parámetro.
	 **/
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

	/**
	 * Este método ejecuta una tarea que elimina una comanda de una mesa, utilizando
	 * como parámetro el indice de la comanda a eliminar
	 **/
	private Task<Void> eliminarComandaIndexTask = new Task<Void>() {
		protected Void call() throws Exception {
			FuncionesDB.eliminarComanda(App.getBARGANIZERDB().getSes(), model.getComandaIndex().getReferencia());
			return null;
		};
	};

	/**
	 * Este método se encarga de redeclarar las tareas, ya que su uso al ser tan
	 * recurrente en la aplicación, es esperable que cualquiera de ellas se
	 * encuentre en estado de finalizada si repetimos su llamada, por lo que
	 * requerimos de ejecutar la llamada de este método para reutilizar una tarea.
	 **/
	private void redeclararTasks() {

		modificarMesaTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				FuncionesDB.modificarMesa(App.getBARGANIZERDB().getSes(),
						(Mesa) model.getTileMesaSeleccionada().getReferencia());
				return null;
			}
		};
		actualizarComandasMesaTask = new Task<ObservableList<ComandaProp>>() {

			@Override
			protected ObservableList<ComandaProp> call() throws Exception {

				List<Comanda> listaComandas = FuncionesDB.listarComandasMesa(App.getBARGANIZERDB().getSes(),
						(Mesa) model.getTileMesaSeleccionada().getReferencia());

				List<ComandaProp> listaProps = new ArrayList<>();

				for (Comanda cmn : listaComandas) {
					listaProps.add(new ComandaProp(cmn));
				}

				return FXCollections.observableArrayList(listaProps);
			}
		};

		actualizarComandasMesaTask.setOnSucceeded(e -> {
			ObservableList<ComandaProp> l = actualizarComandasMesaTask.getValue();
			System.out.println("ACTUALIZAR COMANDAS TASK: " + l);

			model.setComandasMesa(l);
			double preciototal = 0;

			if (model.getComandasMesa() != null) {
				for (ComandaProp c : model.getComandasMesa()) {
					preciototal += (c.getCantidad() * c.getPrecioUnidad());
				}

				String.format("Precio Total: %.2f€", preciototal);
				totalComandaLabel.setText(String.format("Precio Total: %.2f€", preciototal));
			}

		});

		actualizarComandasMesaTask.setOnFailed(e -> {
			System.out.println(e.getSource().toString());
			System.out.println(
					"Error en la tarea de comandas mesa task. Detalles: " + e.getSource().getException().getMessage());
			e.getSource().getException().printStackTrace();
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

		eliminarComandaIndexTask = new Task<Void>() {
			protected Void call() throws Exception {
				FuncionesDB.eliminarComanda(App.getBARGANIZERDB().getSes(), model.getComandaIndex().getReferencia());
				return null;
			};
		};

	}

	/**
	 * Este método nos ayudará a añadir una columna en la tabla, y en sus celdas se
	 * representará un botor que ayudará al usuario a restar cantidades de un
	 * producto de la comanda o quitarlos de la misma.
	 **/
	private void addButtonColumn() {
		Callback<TableColumn<ComandaProp, Void>, TableCell<ComandaProp, Void>> cellFactory = new Callback<TableColumn<ComandaProp, Void>, TableCell<ComandaProp, Void>>() {

			@Override
			public TableCell<ComandaProp, Void> call(TableColumn<ComandaProp, Void> param) {
				final TableCell<ComandaProp, Void> celda = new TableCell<ComandaProp, Void>() {

					private Button quitarButton = new Button();

					{
						ImageView imgViewQuitar = new ImageView(
								new Image(getClass().getResourceAsStream("/images/minus.png")));
						imgViewQuitar.setFitHeight(40);
						imgViewQuitar.setFitWidth(40);
						quitarButton.setGraphic(imgViewQuitar);
						quitarButton.setOnAction(e -> {
							ComandaProp comanda = getTableView().getItems().get(getIndex());
							model.setComandaIndex(comanda);
							// Acciones a realizar al clickear el botón
							redeclararTasks(); // Re-declaración de tareas, en caso de encontrarse en estado utilizado

							new HiloEjecutador(App.semaforo, eliminarComandaIndexTask).start();
							System.out.println("Ejecutando actualización de comandas desde botón de quitar");
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

	/**
	 * Este método se encarga de listar los nodos almacenados en su flow y
	 * establecer su método onMouseClicked, y así determinar con exactitud qué tipo
	 * de tile de plato fue seleccionado. En la aplicación, definimos la lógica de
	 * tal manera que el usuario deberá realizar un doble click sobre el tile para
	 * añadirlo a la comanda. En este caso, se define el mostrado de bebidas
	 **/
	private void prepararNodosBebidas() {
		ObservableList<Node> l = bebidasFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();

				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
						/* Actualizar la lista de comandas con la nueva comanda añadida */
						redeclararTasks();
						new HiloEjecutador(App.semaforo, insertarComandaMesa).start();
						new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

					}
				}

			});
		}
	}

	/**
	 * Este método se encarga de listar los nodos almacenados en su flow y
	 * establecer su método onMouseClicked, y así determinar con exactitud qué tipo
	 * de tile de plato fue seleccionado. En la aplicación, definimos la lógica de
	 * tal manera que el usuario deberá realizar un doble click sobre el tile para
	 * añadirlo a la comanda. En este caso, se define el mostrado de bebidas
	 **/
	private void prepararNodosEntrantes() {
		ObservableList<Node> l = entrantesFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();

				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
						/* Actualizar la lista de comandas con la nueva comanda añadida */
						redeclararTasks();
						new HiloEjecutador(App.semaforo, insertarComandaMesa).start();
						new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

					}
				}

			});
		}
	}

	/**
	 * Este método se encarga de listar los nodos almacenados en su flow y
	 * establecer su método onMouseClicked, y así determinar con exactitud qué tipo
	 * de tile de plato fue seleccionado. En la aplicación, definimos la lógica de
	 * tal manera que el usuario deberá realizar un doble click sobre el tile para
	 * añadirlo a la comanda. En este caso, se define el mostrado de bebidas
	 **/
	private void prepararNodosPostres() {
		ObservableList<Node> l = postresFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();
				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
						/* Actualizar la lista de comandas con la nueva comanda añadida */

						redeclararTasks();
						new HiloEjecutador(App.semaforo, insertarComandaMesa).start();
						new HiloEjecutador(App.semaforo, actualizarComandasMesaTask).start();

					}
				}

			});
		}
	}

	/**
	 * Este método se encarga de listar los nodos almacenados en su flow y
	 * establecer su método onMouseClicked, y así determinar con exactitud qué tipo
	 * de tile de plato fue seleccionado. En la aplicación, definimos la lógica de
	 * tal manera que el usuario deberá realizar un doble click sobre el tile para
	 * añadirlo a la comanda. En este caso, se define el mostrado de bebidas
	 **/
	private void prepararNodosPlatos() {
		ObservableList<Node> l = platosFlow.getChildren();
		for (Node node : l) {
			node.setOnMouseClicked(ev -> {
				ImageTile imageTileClickeado = (ImageTile) ev.getSource();

				model.setTilePlatoSeleccionado(imageTileClickeado);
				model.getTilePlatoSeleccionado().setActive(true);
				if (ev.getClickCount() >= 2) {
					if (model.getTileMesaSeleccionada() == null) {
						App.warning("Advertencia", "Mesa no seleccionada",
								"Debe seleccionar una mesa antes de añadir un producto en la comanda");
					} else {
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
