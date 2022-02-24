package dad.barganizer.db.beans;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {

	/**
	 * 
	 * Esta clase representa el mapeo de la reserva que contiene un identificador
	 * autonumérico y campos LocalDateTome o Int.
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(11)", nullable = false)
	private long id;

	// @Column(name = "mesa", nullable = false, columnDefinition = "INT(3)")
	// private int mesa;

	// @Column(name = "emp", nullable = false, columnDefinition = "INT(3)")
	// private int empleado;

	@Column(name = "fechaHora", nullable = false, columnDefinition = "DATETIME")
	private LocalDateTime fecha;

	@Column(name = "cantPersonas", nullable = false, columnDefinition = "INT(2)")
	private int cantPersonas;

	// Relación N:1
	@ManyToOne
	@JoinColumn(name = "mesa")
	private Mesa mesaReserva;

	// Relación N:1
	@ManyToOne
	@JoinColumn(name = "emp")
	private Empleado empleadoReserva;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public int getCantPersonas() {
		return cantPersonas;
	}

	public void setCantPersonas(int cantPersonas) {
		this.cantPersonas = cantPersonas;
	}

	public Mesa getMesaReserva() {
		return mesaReserva;
	}

	public void setMesaReserva(Mesa mesaReserva) {
		this.mesaReserva = mesaReserva;
	}

	public Empleado getEmpleadoReserva() {
		return empleadoReserva;
	}

	public void setEmpleadoReserva(Empleado empleadoReserva) {
		this.empleadoReserva = empleadoReserva;
	}

}