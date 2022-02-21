package dad.barganizer.gui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginModel {

	StringProperty nombre = new SimpleStringProperty();
	StringProperty clave = new SimpleStringProperty();
	
	
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

}
