package dad.barganizer.gui.models;

import dad.barganizer.ImageTile;
import dad.barganizer.beansprop.CartaProp;
import dad.barganizer.beansprop.PlatoProp;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartaModel {

	private ListProperty<CartaProp> lista = new SimpleListProperty<CartaProp>(FXCollections.observableArrayList());
	private ObjectProperty<CartaProp> cartaSeleccionada = new SimpleObjectProperty<CartaProp>();
	private ObjectProperty<PlatoProp> platoSeleccionado = new SimpleObjectProperty<PlatoProp>();
	private ObjectProperty<ImageTile> imageTileClickeado = new SimpleObjectProperty<ImageTile>();
	

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

}
