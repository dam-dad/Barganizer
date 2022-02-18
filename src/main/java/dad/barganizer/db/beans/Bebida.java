package dad.barganizer.db.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bebida")
public class Bebida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(3)", nullable = false)
	private long id;
	
	@Column(name = "nombre", nullable = false, columnDefinition = "VARCHAR(80)")
	private String nombre;
	
	@Column(name = "foto", nullable = true, columnDefinition = "MEDIUMBLOB")
	private byte[] foto;
	
	@Column(name = "precio", nullable = false, columnDefinition = "DECIMAL(6,2)")
	private double precio;

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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	
}
