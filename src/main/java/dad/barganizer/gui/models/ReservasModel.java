package dad.barganizer.gui.models;

import dad.barganizer.beansprop.ReservaProp;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReservasModel {

	ListProperty<ReservaProp> lista = new SimpleListProperty<>(FXCollections.observableArrayList());
	ObjectProperty<ReservaProp> seleccionada = new SimpleObjectProperty<>();

	public final ListProperty<ReservaProp> listaProperty() {
		return this.lista;
	}

	public final ObservableList<ReservaProp> getLista() {
		return this.listaProperty().get();
	}

	public final void setLista(final ObservableList<ReservaProp> lista) {
		this.listaProperty().set(lista);
	}

	public final ObjectProperty<ReservaProp> seleccionadaProperty() {
		return this.seleccionada;
	}

	public final ReservaProp getSeleccionada() {
		return this.seleccionadaProperty().get();
	}

	public final void setSeleccionada(final ReservaProp seleccionada) {
		this.seleccionadaProperty().set(seleccionada);
	}

}
