package dad.barganizer.gui.models;

import dad.barganizer.ImageTile;
import dad.barganizer.beansprop.ComandaProp;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InicioModel {

	private ListProperty<Plato> listaBebidas = new SimpleListProperty<Plato>(FXCollections.observableArrayList());
	private ListProperty<Mesa> listaMesas = new SimpleListProperty<Mesa>(FXCollections.observableArrayList());
	private ListProperty<Carta> listaCartas = new SimpleListProperty<Carta>(FXCollections.observableArrayList());
	private ListProperty<Plato> listaPlatos = new SimpleListProperty<Plato>(FXCollections.observableArrayList());
	private ListProperty<Plato> listaEntrantes = new SimpleListProperty<Plato>(FXCollections.observableArrayList());
	private ListProperty<Plato> listaPostres = new SimpleListProperty<Plato>(FXCollections.observableArrayList());

	private ObjectProperty<ImageTile> tilePlatoSeleccionado = new SimpleObjectProperty<ImageTile>();
	private ObjectProperty<ImageTile> tileMesaSeleccionada = new SimpleObjectProperty<ImageTile>();
	private ObjectProperty<ComandaProp> comandaIndex = new SimpleObjectProperty<ComandaProp>();

	private ListProperty<ComandaProp> comandasMesa = new SimpleListProperty<ComandaProp>();

	public final ListProperty<Plato> listaBebidasProperty() {
		return this.listaBebidas;
	}

	public final ObservableList<Plato> getListaBebidas() {
		return this.listaBebidasProperty().get();
	}

	public final void setListaBebidas(final ObservableList<Plato> listaBebidas) {
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

	public final ListProperty<Carta> listaCartasProperty() {
		return this.listaCartas;
	}

	public final ObservableList<Carta> getListaCartas() {
		return this.listaCartasProperty().get();
	}

	public final void setListaCartas(final ObservableList<Carta> listaCartas) {
		this.listaCartasProperty().set(listaCartas);
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

	public final ListProperty<Plato> listaEntrantesProperty() {
		return this.listaEntrantes;
	}

	public final ObservableList<Plato> getListaEntrantes() {
		return this.listaEntrantesProperty().get();
	}

	public final void setListaEntrantes(final ObservableList<Plato> listaEntrantes) {
		this.listaEntrantesProperty().set(listaEntrantes);
	}

	public final ListProperty<Plato> listaPostresProperty() {
		return this.listaPostres;
	}

	public final ObservableList<Plato> getListaPostres() {
		return this.listaPostresProperty().get();
	}

	public final void setListaPostres(final ObservableList<Plato> listaPostres) {
		this.listaPostresProperty().set(listaPostres);
	}

	public final ObjectProperty<ImageTile> tilePlatoSeleccionadoProperty() {
		return this.tilePlatoSeleccionado;
	}

	public final ImageTile getTilePlatoSeleccionado() {
		return this.tilePlatoSeleccionadoProperty().get();
	}

	public final void setTilePlatoSeleccionado(final ImageTile tilePlatoSeleccionado) {
		this.tilePlatoSeleccionadoProperty().set(tilePlatoSeleccionado);
	}

	public final ObjectProperty<ImageTile> tileMesaSeleccionadaProperty() {
		return this.tileMesaSeleccionada;
	}

	public final ImageTile getTileMesaSeleccionada() {
		return this.tileMesaSeleccionadaProperty().get();
	}

	public final void setTileMesaSeleccionada(final ImageTile tileMesaSeleccionada) {
		this.tileMesaSeleccionadaProperty().set(tileMesaSeleccionada);
	}

	public final ListProperty<ComandaProp> comandasMesaProperty() {
		return this.comandasMesa;
	}

	public final ObservableList<ComandaProp> getComandasMesa() {
		return this.comandasMesaProperty().get();
	}

	public final void setComandasMesa(final ObservableList<ComandaProp> comandasMesa) {
		this.comandasMesaProperty().set(comandasMesa);
	}

	public final ObjectProperty<ComandaProp> comandaIndexProperty() {
		return this.comandaIndex;
	}

	public final ComandaProp getComandaIndex() {
		return this.comandaIndexProperty().get();
	}

	public final void setComandaIndex(final ComandaProp comandaIndex) {
		this.comandaIndexProperty().set(comandaIndex);
	}

}
