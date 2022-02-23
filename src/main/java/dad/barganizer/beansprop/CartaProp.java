package dad.barganizer.beansprop;


import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Plato;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartaProp {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private ListProperty<Plato> listaPlatos = new SimpleListProperty<Plato>(FXCollections.observableArrayList());
	private ObjectProperty<Carta> referencia = new SimpleObjectProperty<Carta>();
	private boolean mostradoCombo;

	public CartaProp(Carta c) {
		setId(c.getId());
		setNombre(c.getNombre());
		setListaPlatos(FXCollections.observableArrayList(c.getPlatosList()));
		setReferencia(c);
		mostradoCombo = false;
	}

	public CartaProp(Carta c, boolean p) {
		setId(c.getId());
		setNombre(c.getNombre());
		setListaPlatos(FXCollections.observableArrayList(c.getPlatosList()));
		mostradoCombo = true;
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

	public final ObjectProperty<Carta> referenciaProperty() {
		return this.referencia;
	}

	public final Carta getReferencia() {
		return this.referenciaProperty().get();
	}

	public final void setReferencia(final Carta referencia) {
		this.referenciaProperty().set(referencia);
	}
	
	

	public boolean isMostradoCombo() {
		return mostradoCombo;
	}

	public void setMostradoCombo(boolean mostradoCombo) {
		this.mostradoCombo = mostradoCombo;
	}

	@Override
	public String toString() {
		String s = (mostradoCombo) ? getNombre()
				: String.format("Carta: %12s Platos: %3d", getNombre(), getListaPlatos().size());
		return s;
	}

}
