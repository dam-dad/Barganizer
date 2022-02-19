package dad.barganizer.gui.models;

import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InicioModel {

	private ListProperty<Bebida> listaBebidas = new SimpleListProperty<Bebida>(FXCollections.observableArrayList());
	private ListProperty<Mesa> listaMesas = new SimpleListProperty<Mesa>(FXCollections.observableArrayList());
	private ListProperty<Carta> listaCartas = new SimpleListProperty<Carta>(FXCollections.observableArrayList());
	private ListProperty<Plato> listaPlatos = new SimpleListProperty<Plato>(FXCollections.observableArrayList());
	private ListProperty<Plato> listaEntrantes = new SimpleListProperty<Plato>(FXCollections.observableArrayList());
	private ListProperty<Plato> listaPostres = new SimpleListProperty<Plato>(FXCollections.observableArrayList());

	public final ListProperty<Bebida> listaBebidasProperty() {
		return this.listaBebidas;
	}

	public final ObservableList<Bebida> getListaBebidas() {
		return this.listaBebidasProperty().get();
	}

	public final void setListaBebidas(final ObservableList<Bebida> listaBebidas) {
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

}
