package dad.barganizer.db.beans;

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
@Table(name = "comanda")
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)", nullable = false)
    private long id;

    // @Column(name = "mesa", nullable = false, columnDefinition = "INT(3)")
    // private int mesa;

//    @Column(name = "plato", nullable = false, columnDefinition = "INT(2)")
//    private int plato;

    @Column(name = "cantidad", nullable = false, columnDefinition = "INT(2)")
    private int cantidad;

    // Relación N:1
    @ManyToOne
    @JoinColumn(name = "mesa")
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "plato")
    private Plato plato;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
    
}