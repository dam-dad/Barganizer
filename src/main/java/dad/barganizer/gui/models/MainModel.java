package dad.barganizer.gui.models;

import dad.barganizer.db.beans.Empleado;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MainModel {

	ObjectProperty<Empleado> empleado = new SimpleObjectProperty<>();

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
