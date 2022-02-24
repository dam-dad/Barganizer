package dad.barganizer.gui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Modelo de properties usadas para añadir un elemento "Carta" a la base de datos.
 *
 */
public class AddCartaModel {

	private StringProperty nombre = new SimpleStringProperty();


	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

}
