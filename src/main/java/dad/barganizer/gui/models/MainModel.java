package dad.barganizer.gui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainModel {

	private StringProperty ejemplo = new SimpleStringProperty();

	public final StringProperty ejemploProperty() {
		return this.ejemplo;
	}
	

	public final String getEjemplo() {
		return this.ejemploProperty().get();
	}
	

	public final void setEjemplo(final String ejemplo) {
		this.ejemploProperty().set(ejemplo);
	}
	
	
	
}
