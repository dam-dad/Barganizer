package dad.barganizer.gui.models;

import dad.barganizer.db.beans.Empleado;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {

	StringProperty nombre = new SimpleStringProperty();
	StringProperty clave = new SimpleStringProperty();
	ObjectProperty<Empleado> empleado = new SimpleObjectProperty<>();

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty claveProperty() {
		return this.clave;
	}

	public final String getClave() {
		return this.claveProperty().get();
	}

	public final void setClave(final String clave) {
		this.claveProperty().set(clave);
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

}
