package dad.barganizer.gui.models;

import dad.barganizer.beansprop.PlatoProp;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.TipoPlato;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModificarPlatoModel {

	private ObjectProperty<PlatoProp> platoModificar = new SimpleObjectProperty<PlatoProp>();
	private ListProperty<TipoPlato> listaTipos = new SimpleListProperty<TipoPlato>(FXCollections.observableArrayList());
	private ListProperty<Carta> listaCartas = new SimpleListProperty<Carta>(
			FXCollections.observableArrayList());

	private ObjectProperty<TipoPlato> valorTipoCombo = new SimpleObjectProperty<>();
	private ObjectProperty<Carta> valorCartaCombo = new SimpleObjectProperty<>();

	private ObjectProperty<PlatoProp> platoModificado = new SimpleObjectProperty<>(new PlatoProp());

	public final ObjectProperty<PlatoProp> platoModificarProperty() {
		return this.platoModificar;
	}
	

	public final PlatoProp getPlatoModificar() {
		return this.platoModificarProperty().get();
	}
	

	public final void setPlatoModificar(final PlatoProp platoModificar) {
		this.platoModificarProperty().set(platoModificar);
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
	

	public final ListProperty<Carta> listaCartasProperty() {
		return this.listaCartas;
	}
	

	public final ObservableList<Carta> getListaCartas() {
		return this.listaCartasProperty().get();
	}
	

	public final void setListaCartas(final ObservableList<Carta> listaCartas) {
		this.listaCartasProperty().set(listaCartas);
	}
	

	public final ObjectProperty<TipoPlato> valorTipoComboProperty() {
		return this.valorTipoCombo;
	}
	

	public final TipoPlato getValorTipoCombo() {
		return this.valorTipoComboProperty().get();
	}
	

	public final void setValorTipoCombo(final TipoPlato valorTipoCombo) {
		this.valorTipoComboProperty().set(valorTipoCombo);
	}
	

	public final ObjectProperty<Carta> valorCartaComboProperty() {
		return this.valorCartaCombo;
	}
	

	public final Carta getValorCartaCombo() {
		return this.valorCartaComboProperty().get();
	}
	

	public final void setValorCartaCombo(final Carta valorCartaCombo) {
		this.valorCartaComboProperty().set(valorCartaCombo);
	}
	

	public final ObjectProperty<PlatoProp> platoModificadoProperty() {
		return this.platoModificado;
	}
	

	public final PlatoProp getPlatoModificado() {
		return this.platoModificadoProperty().get();
	}
	

	public final void setPlatoModificado(final PlatoProp platoModificado) {
		this.platoModificadoProperty().set(platoModificado);
	}
	
	
	

}
