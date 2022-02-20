package dad.barganizer.gui.controllers;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import dad.barganizer.App;
import dad.barganizer.beansprop.EmpleadoProp;
import dad.barganizer.beansprop.Sexo;
import dad.barganizer.db.BarganizerTasks;
import dad.barganizer.db.FuncionesDB;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.gui.models.EmpleadoModel;

import javafx.fxml.Initializable;

public class EmpleadoController implements Initializable{
	
	private EmpleadoModel model = new EmpleadoModel();
	
	
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
	
	  	@FXML
	    private Button anadirButton;

	    @FXML
	    private TextField apellidosText;

	    @FXML
	    private BorderPane borderDerecho;

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
		model.nombreProperty().bindBidirectional(nombreText.textProperty());
		model.apellidosProperty().bindBidirectional(apellidosText.textProperty());
		model.generoProperty().bindBidirectional(generoCombo.valueProperty());
		model.nacimientoProperty().bindBidirectional(nacimientoFecha.valueProperty());
		model.ingresoProperty().bindBidirectional(ingresoFecha.valueProperty());
		model.passwordProperty().bindBidirectional(passwordText.textProperty());
		generoCombo.getItems().addAll(Sexo.values());
		
		model.empleadoProperty().bind(listaEmpleados.itemsProperty());
		model.seleccionadoProperty().bind(listaEmpleados.getSelectionModel().selectedItemProperty());
		
		
		listaEmpleados.itemsProperty().bind(model.listaProperty());
		model.empleadoProperty().bind(model.seleccionadoProperty());
		model.seleccionadoProperty().bind(listaEmpleados.getSelectionModel().selectedItemProperty());
		*/
		
		
		empleado.addListener(this::onEmpleadoChanged);
		seleccionado.addListener(this::onSeleccionadoChanged);
		
		
		borderDerecho.setDisable(true);
	}
	
	private void onSeleccionadoChanged(ObservableValue<? extends EmpleadoProp> o, EmpleadoProp ov, EmpleadoProp nv) {

		if (nv == null) {

			borderDerecho.setDisable(true);
		}
		else {
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
			model.nombreProperty().unbindBidirectional(ov.nombreProperty());
			model.apellidosProperty().unbindBidirectional(ov.apellidoProperty());
			model.generoProperty().unbindBidirectional(ov.generoProperty());
			model.nacimientoProperty().unbindBidirectional(ov.nacimientoProperty());
			model.ingresoProperty().unbindBidirectional(ov.ingresoProperty());
			model.passwordProperty().unbindBidirectional(ov.passwordProperty());
			model.fotoProperty().unbindBidirectional(ov.fotoProperty());
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
			model.nombreProperty().bindBidirectional(nv.nombreProperty());
			model.apellidosProperty().bindBidirectional(nv.apellidoProperty());
			model.generoProperty().bindBidirectional(nv.generoProperty());
			model.nacimientoProperty().bindBidirectional(nv.nacimientoProperty());
			model.ingresoProperty().bindBidirectional(nv.ingresoProperty());
			model.fotoProperty().bindBidirectional(nv.fotoProperty());
			*/
		}
	}
	
	  @FXML
	    void OnActionAnadir(ActionEvent event) {

	    	EmpleadoProp empleado = new EmpleadoProp();
	    	empleado.setNombre("Nombre");
	    	empleado.setApellido("Apellido");
	    	lista.add(empleado);
	    }

	    @FXML
	    void OnEliminarAction(ActionEvent event) {
	    	
	    	try {
	    		FuncionesDB.eliminarEmpleado(App.getBARGANIZERDB().getSes(), (Empleado)(listaEmpleados.getSelectionModel().getSelectedItems()));
	    		App.info("COMPLETADO", "Borrado completado", "Se ha eliminado la mesa con éxito");
	    		
	    		listaEmpleados.refresh();
	    		
	    		listarEmpleados();
	    		
	    		
	    	}catch (Exception e) {
	    		App.error("Error", "Error al modificar", "Debe tener un empleado seleccionado.");
			}

	    	EmpleadoProp seleccionado = listaEmpleados.getSelectionModel().getSelectedItem();
	    	

			if (seleccionado == null) {

				Alert alertaError = new Alert(AlertType.ERROR);
				alertaError.setTitle("ERROR");
				alertaError.setHeaderText("No hay ningún empleado seleccionado.");
				alertaError.showAndWait();

			}

			else {

				Alert alertaConfirm = new Alert(AlertType.CONFIRMATION);
				alertaConfirm.setTitle("Eliminar empleado");
				alertaConfirm.setHeaderText(
						"Se va a eliminar el alumno: " + seleccionado.getNombre() + " " + seleccionado.getApellido());
				Optional<ButtonType> opcion = alertaConfirm.showAndWait();

				if (opcion.get().equals(ButtonType.OK)) {

					lista.remove(seleccionado);
				}

			}

	    }

	    private void listarEmpleados() {
			
	    	BarganizerTasks tarea = new BarganizerTasks();
	    	
	    	tarea.getObtenerEmpleadosTask().setOnSucceeded(e -> {
	    		ObservableList<EmpleadoProp> emp = tarea.getObtenerEmpleadosTask().getValue();
	    		lista.setValue(emp);
	    		
	    		for(EmpleadoProp empleado : emp) {
//		    		lista.getValue().add(new EmpleadoProp(empleado));
		    	}
		    	
	    	}
	    	
	    
	    	
	    	
	    			
	    			);
			
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
					//stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/contacts-icon-32x32.png")));
					alertaError.setTitle("Error");
					alertaError.setHeaderText("Error al cargar la imagen.");
					alertaError.setContentText("Error: " + e.getMessage());
				}
			}
	    }
	
}
