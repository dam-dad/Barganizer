package dad.barganizer.gui.models;

import java.time.LocalDate;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * 
 * Modelo de properties usadas para añadir un elemento "Reserva" a la base de datos.
 *
 */
public class AddReservaModel {

	ObjectProperty<Mesa> mesaSeleccionada = new SimpleObjectProperty<>();
	ObjectProperty<Empleado> empleadoSeleccionado = new SimpleObjectProperty<>();
	StringProperty personas = new SimpleStringProperty();
	StringProperty hora = new SimpleStringProperty();
	ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();

	public final ObjectProperty<Mesa> mesaSeleccionadaProperty() {
		return this.mesaSeleccionada;
	}

	public final Mesa getMesaSeleccionada() {
		return this.mesaSeleccionadaProperty().get();
	}

	public final void setMesaSeleccionada(final Mesa mesaSeleccionada) {
		this.mesaSeleccionadaProperty().set(mesaSeleccionada);
	}

	public final ObjectProperty<Empleado> empleadoSeleccionadoProperty() {
		return this.empleadoSeleccionado;
	}

	public final Empleado getEmpleadoSeleccionado() {
		return this.empleadoSeleccionadoProperty().get();
	}

	public final void setEmpleadoSeleccionado(final Empleado empleadoSeleccionado) {
		this.empleadoSeleccionadoProperty().set(empleadoSeleccionado);
	}

	public final StringProperty personasProperty() {
		return this.personas;
	}

	public final String getPersonas() {
		return this.personasProperty().get();
	}

	public final void setPersonas(final String personas) {
		this.personasProperty().set(personas);
	}

	public final StringProperty horaProperty() {
		return this.hora;
	}

	public final String getHora() {
		return this.horaProperty().get();
	}

	public final void setHora(final String hora) {
		this.horaProperty().set(hora);
	}

	public final ObjectProperty<LocalDate> fechaProperty() {
		return this.fecha;
	}

	public final LocalDate getFecha() {
		return this.fechaProperty().get();
	}

	public final void setFecha(final LocalDate fecha) {
		this.fechaProperty().set(fecha);
	}

}
