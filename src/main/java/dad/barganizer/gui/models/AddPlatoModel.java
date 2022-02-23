package dad.barganizer.gui.models;

import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.TipoPlato;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class AddPlatoModel {

	private StringProperty nombre = new SimpleStringProperty();
	private ObjectProperty<Image> foto = new SimpleObjectProperty<Image>(
			new Image(getClass().getResourceAsStream("/images/platounknown.png")));
	private ObjectProperty<TipoPlato> tipo = new SimpleObjectProperty<TipoPlato>();
	private DoubleProperty precio = new SimpleDoubleProperty();
	private ObjectProperty<Carta> carta = new SimpleObjectProperty<Carta>();

	private ObjectProperty<byte[]> bytesFoto = new SimpleObjectProperty<byte[]>();

	private ListProperty<Carta> listaCartas = new SimpleListProperty<Carta>();
	private ListProperty<TipoPlato> listaTipos = new SimpleListProperty<TipoPlato>();

	private ObjectProperty<Carta> cartaSeleccionada = new SimpleObjectProperty<>();

	private StringProperty precioFormat = new SimpleStringProperty();
	private DoubleProperty precioValue = new SimpleDoubleProperty();

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

	public final ObjectProperty<byte[]> bytesFotoProperty() {
		return this.bytesFoto;
	}

	public final byte[] getBytesFoto() {
		return this.bytesFotoProperty().get();
	}

	public final void setBytesFoto(final byte[] bytesFoto) {
		this.bytesFotoProperty().set(bytesFoto);
	}

	public final ListProperty<Carta> listaCartasProperty() {
		return this.listaCartas;
	}

	public final ObservableList<Carta> getListaCartas() {
		return this.listaCartasProperty().get();
	}

	public final void setListaCartas(final ObservableList<Carta> listaCartas) {
		this.listaCartasProperty().set(listaCartas);
	}

	public final ListProperty<TipoPlato> listaTiposProperty() {
		return this.listaTipos;
	}

	public final ObservableList<TipoPlato> getListaTipos() {
		return this.listaTiposProperty().get();
	}

	public final void setListaTipos(final ObservableList<TipoPlato> listaTipos) {
		this.listaTiposProperty().set(listaTipos);
	}

	public final ObjectProperty<Carta> cartaSeleccionadaProperty() {
		return this.cartaSeleccionada;
	}

	public final Carta getCartaSeleccionada() {
		return this.cartaSeleccionadaProperty().get();
	}

	public final void setCartaSeleccionada(final Carta cartaSeleccionada) {
		this.cartaSeleccionadaProperty().set(cartaSeleccionada);
	}

	public final StringProperty precioFormatProperty() {
		return this.precioFormat;
	}

	public final String getPrecioFormat() {
		return this.precioFormatProperty().get();
	}

	public final void setPrecioFormat(final String precioFormat) {
		this.precioFormatProperty().set(precioFormat);
	}

	public final DoubleProperty precioValueProperty() {
		return this.precioValue;
	}

	public final double getPrecioValue() {
		return this.precioValueProperty().get();
	}

	public final void setPrecioValue(final double precioValue) {
		this.precioValueProperty().set(precioValue);
	}

}
