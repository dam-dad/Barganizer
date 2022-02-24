package dad.barganizer.db.beans;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * Esta clase representa el mapeo de la tabla alergeno que contiene un
 * identificador autonumérico, y campos String y byte[].
 *
 */

@Entity
@Table(name = "alergeno")
public class Alergeno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(3)", nullable = false)
	private long id;

	@Column(name = "nombre", nullable = false, columnDefinition = "VARCHAR(70)")
	private String nombre;

	@Column(name = "icono", nullable = true, columnDefinition = "MEDIUMBLOB")
	private byte[] icono;

	@ManyToMany(mappedBy = "alergenos")
	private List<Plato> platos;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getIcono() {
		return icono;
	}

	public void setIcono(byte[] icono) {
		this.icono = icono;
	}

	public List<Plato> getPlatos() {
		return platos;
	}

	public void setPlatos(List<Plato> platos) {
		this.platos = platos;
	}

}
