package dad.barganizer.beansprop;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import dad.barganizer.db.beans.Comanda;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ComandaProp extends RecursiveTreeObject<ComandaProp>{

	private LongProperty numMesa = new SimpleLongProperty();
	private StringProperty nombrePlato = new SimpleStringProperty();
	private IntegerProperty cantidad = new SimpleIntegerProperty();
	private DoubleProperty precioUnidad = new SimpleDoubleProperty();
	private DoubleProperty precioTotal = new SimpleDoubleProperty();

	public ComandaProp(Comanda c) {
		setNumMesa(c.getMesa().getNumero());
		setNombrePlato(c.getPlato().getNombre());
		setCantidad(c.getCantidad());
		setPrecioUnidad(c.getPlato().getPrecio());
	}

	public final LongProperty numMesaProperty() {
		return this.numMesa;
	}

	public final long getNumMesa() {
		return this.numMesaProperty().get();
	}

	public final void setNumMesa(final long numMesa) {
		this.numMesaProperty().set(numMesa);
	}

	public final StringProperty nombrePlatoProperty() {
		return this.nombrePlato;
	}

	public final String getNombrePlato() {
		return this.nombrePlatoProperty().get();
	}

	public final void setNombrePlato(final String nombrePlato) {
		this.nombrePlatoProperty().set(nombrePlato);
	}

	public final IntegerProperty cantidadProperty() {
		return this.cantidad;
	}

	public final int getCantidad() {
		return this.cantidadProperty().get();
	}

	public final void setCantidad(final int cantidad) {
		this.cantidadProperty().set(cantidad);
	}

	public final DoubleProperty precioUnidadProperty() {
		return this.precioUnidad;
	}

	public final double getPrecioUnidad() {
		return this.precioUnidadProperty().get();
	}

	public final void setPrecioUnidad(final double precioUnidad) {
		this.precioUnidadProperty().set(precioUnidad);
	}

	public final DoubleProperty precioTotalProperty() {
		return this.precioTotal;
	}

	public final double getPrecioTotal() {
		return this.precioTotalProperty().get();
	}

	public final void setPrecioTotal(final double precioTotal) {
		this.precioTotalProperty().set(precioTotal);
	}

	@Override
	public String toString() {
		return "Comanda: [Mesa:" + getNumMesa() + ", Plato: " + getNombrePlato() + ", Cantidad: " + getCantidad() + "]";
	}
}
