package dad.barganizer.gui.models;

import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Mesa;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InicioModel {

	private ListProperty<Bebida> listaBebidas = new SimpleListProperty<Bebida>(FXCollections.observableArrayList());
	private ListProperty<Mesa> listaMesas = new SimpleListProperty<Mesa>(FXCollections.observableArrayList());

	public final ListProperty<Bebida> listaBebidasProperty() {
		return this.listaBebidas;
	}

	public final ObservableList<Bebida> getListaBebidas() {
		return this.listaBebidasProperty().get();
	}

	public final void setListaBebidas(final ObservableList<Bebida> listaBebidas) {
		this.listaBebidasProperty().set(listaBebidas);
	}

	public final ListProperty<Mesa> listaMesasProperty() {
		return this.listaMesas;
	}

	public final ObservableList<Mesa> getListaMesas() {
		return this.listaMesasProperty().get();
	}

	public final void setListaMesas(final ObservableList<Mesa> listaMesas) {
		this.listaMesasProperty().set(listaMesas);
	}

}
