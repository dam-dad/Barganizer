package dad.barganizer.db.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plato")
public class Plato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(3)", nullable = false)
	private long id;
	
	@Column(name = "nombre", nullable = false, columnDefinition = "VARCHAR(80)")
	private String nombre;
	
	@Column(name = "foto", nullable = true, columnDefinition = "MEDIUMBLOB")
	private byte[] foto;
	
	@ManyToOne
	@JoinColumn(name = "tipo")
	private TipoPlato tipoPlato;
	
	@Column(name="precio", nullable=false, columnDefinition = "DECIMAL(6,2)")
	private double precio;
	
	@JoinTable(
			name = "plato_alergeno",
			joinColumns = @JoinColumn(name = "plato", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "alergeno", nullable = false)
			)
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Alergeno> alergenos;

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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public TipoPlato getTipoPlato() {
		return tipoPlato;
	}

	public void setTipoPlato(TipoPlato tipoPlato) {
		this.tipoPlato = tipoPlato;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
