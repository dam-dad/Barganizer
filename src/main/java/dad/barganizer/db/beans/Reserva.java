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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)", nullable = false)
    private long id;

    //@Column(name = "mesa", nullable = false, columnDefinition = "INT(3)")
    //private int mesa;

    //@Column(name = "emp", nullable = false, columnDefinition = "INT(3)")
    //private int empleado;

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

}