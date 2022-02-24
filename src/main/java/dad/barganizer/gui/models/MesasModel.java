package dad.barganizer.gui.models;

import dad.barganizer.ImageTile;
import dad.barganizer.db.beans.Mesa;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * 
 * Modelo de properties usadas para controlar el listado
 * del elemento "Mesa" en la base de datos.
 *
 */
public class MesasModel {

	private ListProperty<Mesa> listaMesas = new SimpleListProperty<Mesa>(FXCollections.observableArrayList());
	private ObjectProperty<ImageTile> mesaSeleccionada = new SimpleObjectProperty<>();

	public final ListProperty<Mesa> listaMesasProperty() {
		return this.listaMesas;
	}

	public final ObservableList<Mesa> getListaMesas() {
		return this.listaMesasProperty().get();
	}

	public final void setListaMesas(final ObservableList<Mesa> listaMesas) {
		this.listaMesasProperty().set(listaMesas);
	}

	public final ObjectProperty<ImageTile> mesaSeleccionadaProperty() {
		return this.mesaSeleccionada;
	}

	public final ImageTile getMesaSeleccionada() {
		return this.mesaSeleccionadaProperty().get();
	}

	public final void setMesaSeleccionada(final ImageTile mesaSeleccionada) {
		this.mesaSeleccionadaProperty().set(mesaSeleccionada);
	}

}
