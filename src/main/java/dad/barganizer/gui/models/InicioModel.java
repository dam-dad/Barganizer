package dad.barganizer.gui.models;

import dad.barganizer.db.beans.Bebida;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InicioModel {

	private ListProperty<Bebida> listaBebidas = new SimpleListProperty<>(FXCollections.observableArrayList());

	public final ListProperty<Bebida> listaBebidasProperty() {
		return this.listaBebidas;
	}
	

	public final ObservableList<Bebida> getListaBebidas() {
		return this.listaBebidasProperty().get();
	}
	

	public final void setListaBebidas(final ObservableList<Bebida> listaBebidas) {
		this.listaBebidasProperty().set(listaBebidas);
	}
	
	
	
}
