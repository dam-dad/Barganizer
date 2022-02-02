package dad.barganizer.gui.models;
import java.time.LocalDate;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class AgregarEmpleadoModel {
	
	private StringProperty nombre;

	private StringProperty apellidos;
	
	private StringProperty genero;

	private ObjectProperty<LocalDate> fechaNacimiento;
	
	private ObjectProperty<LocalDate> fechaIngreso;

	private ObjectProperty<Image> foto;

	public AgregarEmpleadoModel() {
		nombre = new SimpleStringProperty();
		apellidos = new SimpleStringProperty();
		genero = new SimpleStringProperty();
		fechaNacimiento = new SimpleObjectProperty<>();
		fechaIngreso = new SimpleObjectProperty<>();
		foto = new SimpleObjectProperty<>();
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
	

	public final StringProperty generoProperty() {
		return this.genero;
	}
	

	public final String getGenero() {
		return this.generoProperty().get();
	}
	

	public final void setGenero(final String genero) {
		this.generoProperty().set(genero);
	}
	

	public final ObjectProperty<LocalDate> fechaNacimientoProperty() {
		return this.fechaNacimiento;
	}
	

	public final LocalDate getFechaNacimiento() {
		return this.fechaNacimientoProperty().get();
	}
	

	public final void setFechaNacimiento(final LocalDate fechaNacimiento) {
		this.fechaNacimientoProperty().set(fechaNacimiento);
	}
	

	public final ObjectProperty<LocalDate> fechaIngresoProperty() {
		return this.fechaIngreso;
	}
	

	public final LocalDate getFechaIngreso() {
		return this.fechaIngresoProperty().get();
	}
	

	public final void setFechaIngreso(final LocalDate fechaIngreso) {
		this.fechaIngresoProperty().set(fechaIngreso);
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
	
}
