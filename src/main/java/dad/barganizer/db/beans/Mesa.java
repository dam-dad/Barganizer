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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mesa")
public class Mesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "numero", columnDefinition = "INT(3)", nullable = false)
	private long numero;
	@Column(name = "cantPersonas", nullable = false, columnDefinition = "INT(3)")
	private int cantPersonas;
	@Column(name = "activa", nullable = false, columnDefinition = "BIT")
	private boolean activa;

	@OneToMany(mappedBy = "mesa", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<Comanda> comandasList = new ArrayList<Comanda>();

	@OneToMany(mappedBy = "mesaReserva", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<Reserva> reservasList = new ArrayList<Reserva>();
	
	

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public int getCantPersonas() {
		return cantPersonas;
	}

	public void setCantPersonas(int cantPersonas) {
		this.cantPersonas = cantPersonas;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public List<Comanda> getComandasList() {
		return comandasList;
	}

	public void setComandasList(List<Comanda> comandasList) {
		this.comandasList = comandasList;
	}

	public List<Reserva> getReservasList() {
		return reservasList;
	}

	public void setReservasList(List<Reserva> reservasList) {
		this.reservasList = reservasList;
	}
	
	
	public String toString() {
		
		return String.valueOf(numero);
		
	}

}
