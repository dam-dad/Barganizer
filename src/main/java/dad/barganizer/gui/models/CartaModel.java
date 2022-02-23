package dad.barganizer.gui.models;

import dad.barganizer.ImageTile;
import dad.barganizer.beansprop.CartaProp;
import dad.barganizer.beansprop.PlatoProp;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartaModel {

	private ListProperty<CartaProp> lista = new SimpleListProperty<CartaProp>(FXCollections.observableArrayList());
	private ObjectProperty<CartaProp> cartaSeleccionada = new SimpleObjectProperty<CartaProp>();
	private ObjectProperty<PlatoProp> platoSeleccionado = new SimpleObjectProperty<PlatoProp>();
	private ObjectProperty<ImageTile> imageTileClickeado = new SimpleObjectProperty<ImageTile>();

	private BooleanProperty cartaCheckSeleccionada = new SimpleBooleanProperty();
	private BooleanProperty imageTileCheckSeleccionada = new SimpleBooleanProperty();

	private IntegerProperty indiceAnterior = new SimpleIntegerProperty(-1);

	public final ListProperty<CartaProp> listaProperty() {
		return this.lista;
	}

	public final ObservableList<CartaProp> getLista() {
		return this.listaProperty().get();
	}

	public final void setLista(final ObservableList<CartaProp> lista) {
		this.listaProperty().set(lista);
	}

	public final ObjectProperty<CartaProp> cartaSeleccionadaProperty() {
		return this.cartaSeleccionada;
	}

	public final CartaProp getCartaSeleccionada() {
		return this.cartaSeleccionadaProperty().get();
	}

	public final void setCartaSeleccionada(final CartaProp cartaSeleccionada) {
		this.cartaSeleccionadaProperty().set(cartaSeleccionada);
	}

	public final ObjectProperty<PlatoProp> platoSeleccionadoProperty() {
		return this.platoSeleccionado;
	}

	public final PlatoProp getPlatoSeleccionado() {
		return this.platoSeleccionadoProperty().get();
	}

	public final void setPlatoSeleccionado(final PlatoProp platoSeleccionado) {
		this.platoSeleccionadoProperty().set(platoSeleccionado);
	}

	public final ObjectProperty<ImageTile> imageTileClickeadoProperty() {
		return this.imageTileClickeado;
	}

	public final ImageTile getImageTileClickeado() {
		return this.imageTileClickeadoProperty().get();
	}

	public final void setImageTileClickeado(final ImageTile imageTileClickeado) {
		this.imageTileClickeadoProperty().set(imageTileClickeado);
	}

	public final BooleanProperty cartaCheckSeleccionadaProperty() {
		return this.cartaCheckSeleccionada;
	}

	public final boolean isCartaCheckSeleccionada() {
		return this.cartaCheckSeleccionadaProperty().get();
	}

	public final void setCartaCheckSeleccionada(final boolean cartaCheckSeleccionada) {
		this.cartaCheckSeleccionadaProperty().set(cartaCheckSeleccionada);
	}

	public final BooleanProperty imageTileCheckSeleccionadaProperty() {
		return this.imageTileCheckSeleccionada;
	}

	public final boolean isImageTileCheckSeleccionada() {
		return this.imageTileCheckSeleccionadaProperty().get();
	}

	public final void setImageTileCheckSeleccionada(final boolean imageTileCheckSeleccionada) {
		this.imageTileCheckSeleccionadaProperty().set(imageTileCheckSeleccionada);
	}

	public final IntegerProperty indiceAnteriorProperty() {
		return this.indiceAnterior;
	}

	public final int getIndiceAnterior() {
		return this.indiceAnteriorProperty().get();
	}

	public final void setIndiceAnterior(final int indiceAnterior) {
		this.indiceAnteriorProperty().set(indiceAnterior);
	}

}
