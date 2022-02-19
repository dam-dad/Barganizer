package dad.barganizer.gui.models;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AñadirMesaModel {
	
	IntegerProperty cantidad = new SimpleIntegerProperty();
	BooleanProperty activa = new SimpleBooleanProperty();
	
	
	public final IntegerProperty cantidadProperty() {
		return this.cantidad;
	}
	
	public final int getCantidad() {
		return this.cantidadProperty().get();
	}
	
	public final void setCantidad(final int cantidad) {
		this.cantidadProperty().set(cantidad);
	}
	
	public final BooleanProperty activaProperty() {
		return this.activa;
	}
	
	public final boolean isActiva() {
		return this.activaProperty().get();
	}
	
	public final void setActiva(final boolean activa) {
		this.activaProperty().set(activa);
	}
	
	
	
	
}
