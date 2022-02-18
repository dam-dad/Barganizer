package dad.barganizer.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import dad.barganizer.db.beans.Alergeno;
import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Comanda;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.db.beans.Reserva;
import dad.barganizer.db.beans.TipoPlato;

import dad.barganizer.db.beans.Bebida;

/**
 * Representa la sesión general de la aplicación. Esta clase es un encapsulado
 * de una Sesión de Hibernate, y cuyos métodos
 **/
public class BarganizerDB {

	/** Sesión de Hibernate que representará la conexión con la base de datos. **/
	private Session ses;

	/** Constructor vacío que inicializa la sesión **/
	public BarganizerDB() {
		this.ses = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	/** Método para cerrar la sesión **/
	public void cerrar() {
		try {
			this.ses.close();
		} catch (Exception e) {
			System.err.println("Hubo un error cerrando la sesión. Detalles de la traza:");
			e.printStackTrace();
		}
	}

	/** Getter que nos devuelve la hibernate session **/
	public Session getSes() {
		return this.ses;
	}

	/** Setter para cambiar la sesión si es deseado **/
	public void setSes(Session s) {
		this.ses = s;
	}

	public List<Plato> listarPlatos() {

		try {

			Query consulta = ses.createQuery("from Plato");
			List<Plato> platosList = consulta.getResultList();

			return platosList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public List<Bebida> listarBebidas() {

		try {

			Query consulta = ses.createQuery("from Bebida");
			List<Bebida> bebidasList = consulta.getResultList();

			return bebidasList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public List<Alergeno> listarAlergenos() {

		try {

			Query consulta = ses.createQuery("from Alergeno");
			List<Alergeno> alergList = consulta.getResultList();

			return alergList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public List<Comanda> listarComandas() {

		try {

			Query consulta = ses.createQuery("from Comanda");
			List<Comanda> comandasList = consulta.getResultList();

			return comandasList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public List<Empleado> listarEmpleados() {

		try {

			Query consulta = ses.createQuery("from Empleado");
			List<Empleado> empleadoList = consulta.getResultList();

			return empleadoList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public List<Mesa> listarMesas() {

		try {

			Query consulta = ses.createQuery("from Mesa");
			List<Mesa> mesasList = consulta.getResultList();

			return mesasList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public List<Reserva> listarReservas() {

		try {

			Query consulta = ses.createQuery("from Reserva");
			List<Reserva> reservasList = consulta.getResultList();

			return reservasList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public List<TipoPlato> listarTipo() {

		try {

			Query consulta = ses.createQuery("from TipoPlato");
			List<TipoPlato> tiposList = consulta.getResultList();

			return tiposList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}



	public void insertarPlato(String nombre, TipoPlato tipo, Double precio, byte[] foto, List<Alergeno> alergenos) {

		try {

			ses.beginTransaction();

			Plato plato = new Plato();
			plato.setNombre(nombre);
			plato.setFoto(foto);
			plato.setTipoPlato(tipo);
			plato.setPrecio(0);
			plato.setAlergenos(alergenos);

			ses.persist(plato);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}

	public void insertarBebida(String nombre, byte[] foto, Double precio) {

		try {

			ses.beginTransaction();

			Bebida bebida = new Bebida();
			bebida.setNombre(nombre);
			bebida.setFoto(foto);
			bebida.setPrecio(precio);

			ses.persist(bebida);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}

	public void insertarAlergeno(String nombre, byte[] foto, Double precio) {

		try {

			ses.beginTransaction();

			Alergeno alergeno = new Alergeno();
			alergeno.setNombre(nombre);
			alergeno.setIcono(foto);

			ses.persist(alergeno);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}

	public void insertarComanda(Mesa mesa, int plato, int cantidad) {

		try {

			ses.beginTransaction();

			Comanda comanda = new Comanda();
			comanda.setMesa(mesa);
			comanda.setPlato(plato);
			comanda.setCantidad(cantidad);

			ses.persist(comanda);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}

	public void insertarEmpleado(String nombre, String apellido, String genero, Date nacimiento, Date ingreso,
			byte[] foto) {

		try {

			ses.beginTransaction();

			Empleado empleado = new Empleado();
			empleado.setNombre(nombre);
			empleado.setApellidos(apellido);
			empleado.setGenero(genero);
			empleado.setFnac(nacimiento);
			empleado.setFechaIngreso(ingreso);
			empleado.setFoto(foto);

			ses.persist(empleado);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}
	
	public void insertarMesa(int personas, Boolean activa) {

		try {

			ses.beginTransaction();

			Mesa mesa = new Mesa();
			mesa.setCantPersonas(personas);
			mesa.setActiva(activa);
			
			

			ses.persist(mesa);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}
	
	public void insertarReserva(Mesa mesa, Empleado empleado, LocalDateTime fecha, int personas) {

		try {

			ses.beginTransaction();

			Reserva reserva = new Reserva();
			reserva.setMesaReserva(mesa);
			reserva.setEmpleadoReserva(empleado);
			reserva.setFecha(fecha);
			reserva.setCantPersonas(personas);

			ses.persist(reserva);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}
	
	public void insertarTipoPlato(String nombre) {

		try {

			ses.beginTransaction();

			TipoPlato tipo = new TipoPlato();
			tipo.setNombre(nombre);

			ses.persist(tipo);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}

}