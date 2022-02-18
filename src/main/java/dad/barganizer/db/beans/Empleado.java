package dad.barganizer.db.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "empleado")
public class Empleado {

	@Id
	private int id;
	@Column(name = "nombre", nullable = false, columnDefinition = "VARCHAR(20)")
	private String nombre;
	@Column(name = "apellidos", nullable = false, columnDefinition = "VARCHAR(20)")
	private String apellidos;
	@Column(name = "genero", nullable = false, columnDefinition = "enum('Hombre', 'Mujer')")
	private String genero;
	@Column(name = "fnac", nullable = false, columnDefinition = "DATE")
	private Date fnac;
	@Column(name = "fechaIngreso", nullable = false, columnDefinition = "DATE")
	private Date fechaIngreso;
	@Column(name = "foto", nullable = true, columnDefinition = "MEDIUMBLOB")
	private byte[] Foto;

	@OneToMany(mappedBy = "empleadoReserva", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<Reserva> reservasList = new ArrayList<Reserva>();

	public Empleado() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getFnac() {
		return fnac;
	}

	public void setFnac(Date fnac) {
		this.fnac = fnac;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public byte[] getFoto() {
		return Foto;
	}

	public void setFoto(byte[] foto) {
		Foto = foto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}