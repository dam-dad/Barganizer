package dad.barganizer.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import dad.barganizer.App;
import dad.barganizer.beansprop.EmpleadoProp;
import dad.barganizer.beansprop.Sexo;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.HibernateUtil;
import dad.barganizer.thread.HiloEjecutador;
import javafx.fxml.Initializable;

public class EmpleadoController implements Initializable {

	private ObjectProperty<EmpleadoProp> seleccionado = new SimpleObjectProperty<>();
	private ListProperty<EmpleadoProp> lista = new SimpleListProperty<>(FXCollections.observableArrayList());

	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty apellidos = new SimpleStringProperty();
	private ObjectProperty<Sexo> genero = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> nacimiento = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> ingreso = new SimpleObjectProperty<>();
	private ObjectProperty<Image> foto = new SimpleObjectProperty<>();
	private StringProperty password = new SimpleStringProperty();
	private ObjectProperty<EmpleadoProp> empleado = new SimpleObjectProperty<>();
	private ObjectProperty<Image> nuevaFoto = new SimpleObjectProperty<>();
	private ObjectProperty<EmpleadoProp> empleadoMod = new SimpleObjectProperty<EmpleadoProp>();
	private ObjectProperty<byte[]> bytesFotoRecogida = new SimpleObjectProperty<>();

	@FXML
	private Button anadirButton;

	@FXML
	private TextField apellidosText;

	@FXML
	private BorderPane borderDerecho;

	@FXML
	private Button modificarButton;

	@FXML
	private Button cambiarImagenButton;

	@FXML
	private Button eliminarButton;

	@FXML
	private ComboBox<Sexo> generoCombo;

	@FXML
	private ImageView imageView;

	@FXML
	private DatePicker ingresoFecha;

	@FXML
	private ListView<EmpleadoProp> listaEmpleados;

	@FXML
	private DatePicker nacimientoFecha;

	@FXML
	private TextField nombreText;

	@FXML
	private PasswordField passwordText;

	@FXML
	private VBox view;

	private ObjectProperty<Image> NO_PHOTO;

	public VBox getView() {
		return view;
	}

	public EmpleadoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EmpleadoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		eliminarButton.disableProperty().bind(Bindings.when(seleccionado.isNull()).then(true).otherwise(false));

		NO_PHOTO = new SimpleObjectProperty<Image>(new Image(getClass().getResourceAsStream("/images/prueba.PNG")));

		foto.addListener((o, ov, nv) -> {
			foto.setValue((Objects.isNull(nv) ? NO_PHOTO.get() : nv));
		});

		nombreText.textProperty().bindBidirectional(nombre);
		apellidosText.textProperty().bindBidirectional(apellidos);
		generoCombo.valueProperty().bindBidirectional(genero);

		nacimientoFecha.valueProperty().bindBidirectional(nacimiento);
		nacimientoFecha.valueProperty().bindBidirectional(nacimiento);
		ingresoFecha.valueProperty().bindBidirectional(ingreso);
		passwordText.textProperty().bindBidirectional(password);
		imageView.imageProperty().bindBidirectional(foto);
		generoCombo.getItems().addAll(Sexo.values());

		listaEmpleados.itemsProperty().bind(lista);
		empleado.bind(seleccionado);
		seleccionado.bind(listaEmpleados.getSelectionModel().selectedItemProperty());

		empleado.addListener(this::onEmpleadoChanged);
		seleccionado.addListener(this::onSeleccionadoChanged);

		lista.addListener((o, ov, nv) -> {
			System.out.println(ov);
			System.out.println(nv);
		});

		borderDerecho.setDisable(true);

		BarganizerTasks tareas = new BarganizerTasks();
		tareas.getObtenerEmpleadosTask().setOnSucceeded(e -> {
			System.out.println(tareas.getObtenerEmpleadosTask().getValue());
			lista.setAll(tareas.getObtenerEmpleadosTask().getValue());
		});

		tareas.getObtenerEmpleadosTask().setOnFailed(e -> {
			System.err.println(tareas.getObtenerEmpleadosTask().getValue());
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(App.semaforo, tareas.getObtenerEmpleadosTask()).start();

	}

	private void onSeleccionadoChanged(ObservableValue<? extends EmpleadoProp> o, EmpleadoProp ov, EmpleadoProp nv) {

		if (nv == null) {

			borderDerecho.setDisable(true);
		} else {
			borderDerecho.setDisable(false);
		}

	}

	private void onEmpleadoChanged(ObservableValue<? extends EmpleadoProp> o, EmpleadoProp ov, EmpleadoProp nv) {

		if (ov != null) {

			nombre.unbindBidirectional(ov.nombreProperty());
			apellidos.unbindBidirectional(ov.apellidoProperty());
			genero.unbindBidirectional(ov.generoProperty());
			nacimiento.unbindBidirectional(ov.nacimientoProperty());
			ingreso.unbindBidirectional(ov.ingresoProperty());
			password.unbindBidirectional(ov.passwordProperty());
			foto.unbindBidirectional(ov.fotoProperty());

		}

		if (nv != null) {

			nombre.bindBidirectional(nv.nombreProperty());
			apellidos.bindBidirectional(nv.apellidoProperty());
			genero.bindBidirectional(nv.generoProperty());
			nacimiento.bindBidirectional(nv.nacimientoProperty());
			ingreso.bindBidirectional(nv.ingresoProperty());
			password.bindBidirectional(nv.passwordProperty());
			foto.bindBidirectional(nv.fotoProperty());
		}
	}

	@SuppressWarnings("unused")
	private byte[] convertToBytes(ImageView image) throws IOException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
			out.writeObject(image);
			return bos.toByteArray();
		}
	}

	@FXML
	void OnActionAnadir(ActionEvent event) {

		try {

			InputStream fnew = getClass().getResourceAsStream("/images/unknown_person.jpg");

			FuncionesDB.insertarEmpleado(HibernateUtil.getSessionFactory().openSession(), "Nuevo empleado", "Apellidos",
					"Hombre", LocalDate.now(), LocalDate.now(), fnew.readAllBytes(), "");

			App.info("Completado", "Inserción completado", "Se ha completado la inserción con éxito");

			listaEmpleados.getItems().clear();

			listarEmpleados();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@FXML
	void OnEliminarAction(ActionEvent event) {

		if (seleccionado != null) {

			if (App.confirm("BORRAR", "PROCESO DE BORRADO", "¿Desea borrar el empleado?")) {

				FuncionesDB.eliminarEmpleado(App.getBARGANIZERDB().getSes(), seleccionado.getValue());

				App.info("COMPLETADO", "Borrado completado", "Se ha eliminado al empleado con éxito");
			}

		} else {
			App.error("Error", "Error al borrar", "Debe tener una mesa seleccionada.");
		}

		listaEmpleados.getItems().clear();

		listarEmpleados();

		App.info("Completado", "Borrado completado", "Se ha completado el borrado con éxito");

	}

	@FXML
	void onModificarButton(ActionEvent event) {

		if (seleccionado != null) {
			EmpleadoProp empleado = new EmpleadoProp();
			empleado.setId(seleccionado.getValue().getId());
			empleado.setNombre(nombre.getValue());
			empleado.setApellido(apellidos.getValue());
			empleado.setGenero(generoCombo.getValue());
			empleado.setNacimiento(nacimiento.getValue());
			empleado.setIngreso(ingreso.getValue());
			empleado.setPassword(password.getValue());
			empleado.setFoto(foto.getValue());
			empleado.setBytesFoto(bytesFotoRecogida.get());
			Task<Void> insertarEmpleadoTask = new Task<Void>() {

				@Override
				protected Void call() throws Exception {

					FuncionesDB.modificarEmpleado(App.getBARGANIZERDB().getSes(), empleado);

					return null;
				}
			};

			insertarEmpleadoTask.setOnSucceeded(e -> {
				App.info("Completado", "Modificación completada", "Se ha completado la modificación con éxito");
			});

			insertarEmpleadoTask.setOnFailed(e -> {
				App.info("Error", "Modificación no completada", "No se pudo modificar el empleado");
			});

			new HiloEjecutador(App.semaforo, insertarEmpleadoTask).start();

		}
	}

	private void listarEmpleados() {

		BarganizerTasks tareas = new BarganizerTasks();
		tareas.getObtenerEmpleadosTask().setOnSucceeded(e -> {
			lista.setAll(tareas.getObtenerEmpleadosTask().getValue());
		});

		tareas.getObtenerEmpleadosTask().setOnFailed(e -> {
			e.getSource().getException().printStackTrace();
		});

		new HiloEjecutador(App.semaforo, tareas.getObtenerEmpleadosTask()).start();
	}

	@FXML
	void onCambiarImagen(ActionEvent event) {

		Stage stageChoser = new Stage();
		FileChooser fileChoser = new FileChooser();
		stageChoser.initOwner(App.primaryStage);
		fileChoser.setTitle("Abrir imagen...");
		fileChoser.getExtensionFilters().addAll(new ExtensionFilter("Todos los archivos", "*.*"),
				new ExtensionFilter("Todos las imágenes", "*.jpg, *.png, *.bmp"));

		File imagen = fileChoser.showOpenDialog(stageChoser);

		if (imagen != null) {

			try {
				FileInputStream fis = new FileInputStream(imagen);
				setBytesFotoRecogida(fis.readAllBytes());
				Image foto = new Image(imagen.toURI().toURL().toExternalForm());
				imageView.setImage(foto);
				fis.close();

			} catch (Exception e) {

				Alert alertaError = new Alert(AlertType.ERROR);
				alertaError.initOwner(App.primaryStage);
				alertaError.setTitle("Error");
				alertaError.setHeaderText("Error al cargar la imagen.");
				alertaError.setContentText("Error: " + e.getMessage());
			}
		}
	}

	public final ObjectProperty<Image> nuevaFotoProperty() {
		return this.nuevaFoto;
	}

	public final Image getNuevaFoto() {
		return this.nuevaFotoProperty().get();
	}

	public final void setNuevaFoto(final Image nuevaFoto) {
		this.nuevaFotoProperty().set(nuevaFoto);
	}

	public final ObjectProperty<EmpleadoProp> empleadoModProperty() {
		return this.empleadoMod;
	}

	public final EmpleadoProp getEmpleadoMod() {
		return this.empleadoModProperty().get();
	}

	public final void setEmpleadoMod(final EmpleadoProp empleadoMod) {
		this.empleadoModProperty().set(empleadoMod);
	}

	public final ObjectProperty<byte[]> bytesFotoRecogidaProperty() {
		return this.bytesFotoRecogida;
	}

	public final byte[] getBytesFotoRecogida() {
		return this.bytesFotoRecogidaProperty().get();
	}

	public final void setBytesFotoRecogida(final byte[] bytesFotoRecogida) {
		this.bytesFotoRecogidaProperty().set(bytesFotoRecogida);
	}

}
