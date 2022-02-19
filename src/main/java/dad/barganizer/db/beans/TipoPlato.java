package dad.barganizer.db.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_plato")
public class TipoPlato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT(3)", nullable = false)
	private long id;
	
	@Column(name = "nombre", nullable = false, columnDefinition = "ENUM('Entrante', 'Principal', 'Bebida', 'Postre', 'Vegetariano')")
	private String nombre;
	
	// Relación 1:N
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tipoPlato")
	private List<Plato> platosTipo = new ArrayList<Plato>();
	
	
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

	public List<Plato> getPlatosTipo() {
		return platosTipo;
	}

	public void setPlatosTipo(List<Plato> platosTipo) {
		this.platosTipo = platosTipo;
	}
	
	
}
