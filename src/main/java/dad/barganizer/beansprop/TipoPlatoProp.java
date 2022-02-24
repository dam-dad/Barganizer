package dad.barganizer.beansprop;



import java.io.FileNotFoundException;

import dad.barganizer.db.beans.Plato;
import dad.barganizer.db.beans.TipoPlato;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * 
 * Clase Bean de properties que servirá de apoyo para trabajar los tipos de plato y representarlos en la interfaz gráfica.
 *
 */
public class TipoPlatoProp {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private ListProperty<PlatoProp> platosTipo = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public TipoPlatoProp(TipoPlato tp) throws FileNotFoundException {
		setId(tp.getId());
		setNombre(tp.getNombre());
		
		for (Plato plato : tp.getPlatosTipo()) {
			platosTipo.add(new PlatoProp(plato));
		}
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
	

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public final String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	

	public final ListProperty<PlatoProp> platosTipoProperty() {
		return this.platosTipo;
	}
	

	public final ObservableList<PlatoProp> getPlatosTipo() {
		return this.platosTipoProperty().get();
	}
	

	public final void setPlatosTipo(final ObservableList<PlatoProp> platosTipo) {
		this.platosTipoProperty().set(platosTipo);
	}
	
	@Override
	public String toString() {
		return getNombre();
	}
	
	
}
