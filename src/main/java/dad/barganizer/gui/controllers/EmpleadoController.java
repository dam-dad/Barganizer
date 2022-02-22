package dad.barganizer.gui.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.hibernate.Session;

import dad.barganizer.App;
import dad.barganizer.beansprop.EmpleadoProp;
import dad.barganizer.beansprop.Sexo;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.HibernateUtil;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.gui.models.EmpleadoModel;
import dad.barganizer.thread.HiloEjecutador;
import javafx.fxml.Initializable;

public class EmpleadoController implements Initializable {

	private EmpleadoModel model = new EmpleadoModel();

	private ObjectProperty<EmpleadoProp> seleccionado = new SimpleObjectProperty<>();
	private ListProperty<EmpleadoProp> lista = new SimpleListProperty<>(FXCollections.observableArrayList());

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty apellidos = new SimpleStringProperty();
	private ObjectProperty<Sexo> genero = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> nacimiento = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> ingreso = new SimpleObjectProperty<>();
	private ObjectProperty<Image> foto = new SimpleObjectProperty<>();
	private StringProperty password = new SimpleStringProperty();
	private ObjectProperty<EmpleadoProp> empleado = new SimpleObjectProperty<>();

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

		/*
		 * model.nombreProperty().bindBidirectional(nombreText.textProperty());
		 * model.apellidosProperty().bindBidirectional(apellidosText.textProperty());
		 * model.generoProperty().bindBidirectional(generoCombo.valueProperty());
		 * model.nacimientoProperty().bindBidirectional(nacimientoFecha.valueProperty())
		 * ; model.ingresoProperty().bindBidirectional(ingresoFecha.valueProperty());
		 * model.passwordProperty().bindBidirectional(passwordText.textProperty());
		 * generoCombo.getItems().addAll(Sexo.values());
		 * 
		 * model.empleadoProperty().bind(listaEmpleados.itemsProperty());
		 * model.seleccionadoProperty().bind(listaEmpleados.getSelectionModel().
		 * selectedItemProperty());
		 * 
		 * 
		 * listaEmpleados.itemsProperty().bind(model.listaProperty());
		 * model.empleadoProperty().bind(model.seleccionadoProperty());
		 * model.seleccionadoProperty().bind(listaEmpleados.getSelectionModel().
		 * selectedItemProperty());
		 */

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

			/*
			 * model.nombreProperty().unbindBidirectional(ov.nombreProperty());
			 * model.apellidosProperty().unbindBidirectional(ov.apellidoProperty());
			 * model.generoProperty().unbindBidirectional(ov.generoProperty());
			 * model.nacimientoProperty().unbindBidirectional(ov.nacimientoProperty());
			 * model.ingresoProperty().unbindBidirectional(ov.ingresoProperty());
			 * model.passwordProperty().unbindBidirectional(ov.passwordProperty());
			 * model.fotoProperty().unbindBidirectional(ov.fotoProperty());
			 */
		}

		if (nv != null) {

			nombre.bindBidirectional(nv.nombreProperty());
			apellidos.bindBidirectional(nv.apellidoProperty());
			genero.bindBidirectional(nv.generoProperty());
			nacimiento.bindBidirectional(nv.nacimientoProperty());
			ingreso.bindBidirectional(nv.ingresoProperty());
			password.bindBidirectional(nv.passwordProperty());
			foto.bindBidirectional(nv.fotoProperty());
			/*
			 * model.nombreProperty().bindBidirectional(nv.nombreProperty());
			 * model.apellidosProperty().bindBidirectional(nv.apellidoProperty());
			 * model.generoProperty().bindBidirectional(nv.generoProperty());
			 * model.nacimientoProperty().bindBidirectional(nv.nacimientoProperty());
			 * model.ingresoProperty().bindBidirectional(nv.ingresoProperty());
			 * model.fotoProperty().bindBidirectional(nv.fotoProperty());
			 */
		}
	}

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
			
			BufferedImage originalImage = ImageIO.read(fnew);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			byte[] imagen = baos.toByteArray();

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

	}

	@FXML
    void onModificarButton(ActionEvent event) {

		if (seleccionado != null) {
			EmpleadoProp empleado = null;
			empleado.setId(seleccionado.getValue().getId());
			empleado.setNombre(nombre.getValue());
			empleado.setApellido(apellidos.getValue());
			empleado.setGenero(generoCombo.getValue());
			empleado.setNacimiento(nacimiento.getValue());
			empleado.setIngreso(ingreso.getValue());
			empleado.setPassword(password.getValue());
			empleado.setFoto(imageView.getImage());
			
			FuncionesDB.modificarEmpleado(App.getBARGANIZERDB().getSes(), empleado);
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
		fileChoser.setTitle("Abrir agenda...");
		fileChoser.getExtensionFilters().addAll(new ExtensionFilter("Todos los archivos", "*.*"),
				new ExtensionFilter("Todos las imágenes", "*.jpg, *.png, *.bmp"));

		File imagen = fileChoser.showOpenDialog(stageChoser);

		if (imagen != null) {

			try {
				Image foto = new Image(imagen.toURI().toURL().toExternalForm());
				imageView.setImage(foto);

			} catch (Exception e) {

				Alert alertaError = new Alert(AlertType.ERROR);
				Stage stage = (Stage) alertaError.getDialogPane().getScene().getWindow();
				alertaError.initOwner(App.primaryStage);
				alertaError.setTitle("Error");
				alertaError.setHeaderText("Error al cargar la imagen.");
				alertaError.setContentText("Error: " + e.getMessage());
			}
		}
	}

}
