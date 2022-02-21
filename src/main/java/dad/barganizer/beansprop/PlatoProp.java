package dad.barganizer.beansprop;

import java.io.ByteArrayInputStream;

import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.db.beans.TipoPlato;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class PlatoProp {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private ObjectProperty<Image> foto = new SimpleObjectProperty<Image>();
	private ObjectProperty<TipoPlato> tipo = new SimpleObjectProperty<TipoPlato>();
	private DoubleProperty precio = new SimpleDoubleProperty();
	private ObjectProperty<Carta> carta = new SimpleObjectProperty<Carta>();

	public PlatoProp(Plato p) {
		setId(p.getId());
		setNombre(p.getNombre());
		setFoto(new Image(new ByteArrayInputStream(p.getFoto())));
		setTipo(p.getTipoPlato());
		setPrecio(p.getPrecio());
		setCarta(p.getCarta());
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

	public final ObjectProperty<Image> fotoProperty() {
		return this.foto;
	}

	public final Image getFoto() {
		return this.fotoProperty().get();
	}

	public final void setFoto(final Image foto) {
		this.fotoProperty().set(foto);
	}

	public final ObjectProperty<TipoPlato> tipoProperty() {
		return this.tipo;
	}

	public final TipoPlato getTipo() {
		return this.tipoProperty().get();
	}

	public final void setTipo(final TipoPlato tipo) {
		this.tipoProperty().set(tipo);
	}

	public final DoubleProperty precioProperty() {
		return this.precio;
	}

	public final double getPrecio() {
		return this.precioProperty().get();
	}

	public final void setPrecio(final double precio) {
		this.precioProperty().set(precio);
	}

	public final ObjectProperty<Carta> cartaProperty() {
		return this.carta;
	}

	public final Carta getCarta() {
		return this.cartaProperty().get();
	}

	public final void setCarta(final Carta carta) {
		this.cartaProperty().set(carta);
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

}
