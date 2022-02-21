package dad.barganizer.beansprop;

import java.util.List;

import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Plato;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartaProp {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private ListProperty<Plato> listaPlatos = new SimpleListProperty<Plato>(FXCollections.observableArrayList());

	public CartaProp(Carta c) {
		setId(c.getId());
		setNombre(c.getNombre());
		setListaPlatos(FXCollections.observableArrayList(c.getPlatosList()));

	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final ListProperty<Plato> listaPlatosProperty() {
		return this.listaPlatos;
	}

	public final ObservableList<Plato> getListaPlatos() {
		return this.listaPlatosProperty().get();
	}

	public final void setListaPlatos(final ObservableList<Plato> listaPlatos) {
		this.listaPlatosProperty().set(listaPlatos);
	}

	public final LongProperty idProperty() {
		return this.id;
	}

	public final long getId() {
		return this.idProperty().get();
	}

	public final void setId(final long id) {
		this.idProperty().set(id);
	}

	@Override
	public String toString() {
		String s = String.format("Carta: %12s Platos: %3d", getNombre(), getListaPlatos().size());
		return s;
	}

}
