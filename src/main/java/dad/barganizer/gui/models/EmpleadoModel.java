package dad.barganizer.gui.models;

import java.time.LocalDate;

import dad.barganizer.beansprop.Sexo;
import dad.barganizer.db.beans.Empleado;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;

public class EmpleadoModel {

	private StringProperty nombre;
	private StringProperty apellidos;
	private ObjectProperty<Sexo> genero;
	private ObjectProperty<LocalDate> nacimiento;
	private ObjectProperty<LocalDate> ingreso;
	private ObjectProperty<Image> foto;
	private ObjectProperty<Empleado> empleado;
	private ObjectProperty<Empleado> seleccionado;
	private ListProperty<Empleado> lista;
	private StringProperty password;
	
	public EmpleadoModel(){
		
		nombre = new SimpleStringProperty();
		apellidos = new SimpleStringProperty();
		genero = new SimpleObjectProperty<>();
		nacimiento = new SimpleObjectProperty<>();
		ingreso = new SimpleObjectProperty<>();
		foto = new SimpleObjectProperty<>();
		empleado = new SimpleObjectProperty<>();
		seleccionado = new SimpleObjectProperty<>();
		lista = new SimpleListProperty<>(FXCollections.observableArrayList());
		password = new SimpleStringProperty();
	
		
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public final String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	

	public final StringProperty apellidosProperty() {
		return this.apellidos;
	}
	

	public final String getApellidos() {
		return this.apellidosProperty().get();
	}
	

	public final void setApellidos(final String apellidos) {
		this.apellidosProperty().set(apellidos);
	}
	

	public final ObjectProperty<Sexo> generoProperty() {
		return this.genero;
	}
	

	public final Sexo getGenero() {
		return this.generoProperty().get();
	}
	

	public final void setGenero(final Sexo genero) {
		this.generoProperty().set(genero);
	}
	

	public final ObjectProperty<LocalDate> nacimientoProperty() {
		return this.nacimiento;
	}
	

	public final LocalDate getNacimiento() {
		return this.nacimientoProperty().get();
	}
	

	public final void setNacimiento(final LocalDate nacimiento) {
		this.nacimientoProperty().set(nacimiento);
	}
	

	public final ObjectProperty<LocalDate> ingresoProperty() {
		return this.ingreso;
	}
	

	public final LocalDate getIngreso() {
		return this.ingresoProperty().get();
	}
	

	public final void setIngreso(final LocalDate ingreso) {
		this.ingresoProperty().set(ingreso);
	}
	

	public final ObjectProperty<Image> fotoProperty() {
		return this.foto;
	}
	

	public final Image getFoto() {
		return this.fotoProperty().get();
	}
	

	public final void setFoto(final Image foto) {
		this.fotoProperty().set(foto);
	}
	

	public final ObjectProperty<Empleado> empleadoProperty() {
		return this.empleado;
	}
	

	public final Empleado getEmpleado() {
		return this.empleadoProperty().get();
	}
	

	public final void setEmpleado(final Empleado empleado) {
		this.empleadoProperty().set(empleado);
	}
	

	public final ObjectProperty<Empleado> seleccionadoProperty() {
		return this.seleccionado;
	}
	

	public final Empleado getSeleccionado() {
		return this.seleccionadoProperty().get();
	}
	

	public final void setSeleccionado(final Empleado seleccionado) {
		this.seleccionadoProperty().set(seleccionado);
	}
	

	public final ListProperty<Empleado> listaProperty() {
		return this.lista;
	}
	

	public final ObservableList<Empleado> getLista() {
		return this.listaProperty().get();
	}
	

	public final void setLista(final ObservableList<Empleado> lista) {
		this.listaProperty().set(lista);
	}
	

	public final StringProperty passwordProperty() {
		return this.password;
	}
	

	public final String getPassword() {
		return this.passwordProperty().get();
	}
	

	public final void setPassword(final String password) {
		this.passwordProperty().set(password);
	}
	

	
	

	
	
}
