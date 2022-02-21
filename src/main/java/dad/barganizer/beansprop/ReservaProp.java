package dad.barganizer.beansprop;

import java.time.LocalDateTime;

import dad.barganizer.db.beans.Reserva;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReservaProp {
	
	private StringProperty id = new SimpleStringProperty();
	private StringProperty empleado = new SimpleStringProperty();
	private StringProperty mesa = new SimpleStringProperty();
	private StringProperty personas = new SimpleStringProperty();
	private ObjectProperty<LocalDateTime> fecha = new SimpleObjectProperty<>();
	
	private ObjectProperty<Reserva> referencia = new SimpleObjectProperty<>();
	
	
	public ReservaProp(Reserva reserva) {
		
		setId(String.valueOf(reserva.getId()));
		setEmpleado(reserva.getEmpleadoReserva().getNombre());
		setMesa(String.valueOf(reserva.getMesaReserva().getNumero()));
		setPersonas(String.valueOf(reserva.getCantPersonas()));
		setFecha(reserva.getFecha());
		setReferencia(reserva);
		
	}


	public final StringProperty idProperty() {
		return this.id;
	}
	


	public final String getId() {
		return this.idProperty().get();
	}
	


	public final void setId(final String id) {
		this.idProperty().set(id);
	}
	


	public final StringProperty empleadoProperty() {
		return this.empleado;
	}
	


	public final String getEmpleado() {
		return this.empleadoProperty().get();
	}
	


	public final void setEmpleado(final String empleado) {
		this.empleadoProperty().set(empleado);
	}
	


	public final StringProperty mesaProperty() {
		return this.mesa;
	}
	


	public final String getMesa() {
		return this.mesaProperty().get();
	}
	


	public final void setMesa(final String mesa) {
		this.mesaProperty().set(mesa);
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
	


	public final ObjectProperty<LocalDateTime> fechaProperty() {
		return this.fecha;
	}
	


	public final LocalDateTime getFecha() {
		return this.fechaProperty().get();
	}
	


	public final void setFecha(final LocalDateTime fecha) {
		this.fechaProperty().set(fecha);
	}
	


	public final ObjectProperty<Reserva> referenciaProperty() {
		return this.referencia;
	}
	


	public final Reserva getReferencia() {
		return this.referenciaProperty().get();
	}
	


	public final void setReferencia(final Reserva referencia) {
		this.referenciaProperty().set(referencia);
	}
	
	
	

}
