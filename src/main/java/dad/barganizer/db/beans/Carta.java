package dad.barganizer.db.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "carta")
public class Carta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(3)", nullable = false)
	private long id;

	@Column(name = "nombre", nullable = false, columnDefinition = "VARCHAR(80)")
	private String nombre;

	@OneToMany(mappedBy = "carta", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<Plato> platosList;

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

	public List<Plato> getPlatosList() {
		return platosList;
	}

	public void setPlatosList(List<Plato> platosList) {
		this.platosList = platosList;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
