package dad.barganizer.gui.models;

import dad.barganizer.db.beans.Empleado;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
/**
 * 
 * Model de properties usadas para visualizar el usuario conectado a la aplicación
 * mediante el login.
 *
 */
public class MainModel {

	ObjectProperty<Empleado> empleado = new SimpleObjectProperty<>();
	StringProperty nombreEmpleado = new SimpleStringProperty();
	ObjectProperty<Image> foto = new SimpleObjectProperty<>();

	public final ObjectProperty<Empleado> empleadoProperty() {
		return this.empleado;
	}

	public final Empleado getEmpleado() {
		return this.empleadoProperty().get();
	}

	public final void setEmpleado(final Empleado empleado) {
		this.empleadoProperty().set(empleado);
	}

	public final StringProperty nombreEmpleadoProperty() {
		return this.nombreEmpleado;
	}

	public final String getNombreEmpleado() {
		return this.nombreEmpleadoProperty().get();
	}

	public final void setNombreEmpleado(final String nombreEmpleado) {
		this.nombreEmpleadoProperty().set(nombreEmpleado);
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
