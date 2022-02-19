package dad.barganizer.db;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import dad.barganizer.App;
import dad.barganizer.db.beans.Alergeno;
import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Comanda;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.db.beans.Reserva;
import dad.barganizer.db.beans.TipoPlato;

public class FuncionesDB {

	public static List<Plato> listarPlatos(Session ses) {

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

	public static List<Bebida> listarBebidas(Session ses) {

		try {
			ses.beginTransaction();
			Query consulta = ses.createQuery("from Bebida");
			List<Bebida> bebidasList = consulta.getResultList();
			ses.getTransaction().commit();
			return bebidasList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public static List<Alergeno> listarAlergenos(Session ses) {

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

	public static List<Comanda> listarComandas(Session ses) {

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

	public static List<Empleado> listarEmpleados(Session ses) {

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

	public static List<Mesa> listarMesas(Session ses) {

		try {
			ses.beginTransaction();
			Query consulta = ses.createQuery("from Mesa");
			List<Mesa> mesasList = consulta.getResultList();
			ses.getTransaction().commit();
			return mesasList;

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			ses.getTransaction().rollback();
			return null;
		}

	}

	public static List<Reserva> listarReservas(Session ses) {

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

	public static List<TipoPlato> listarTipo(Session ses) {

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

	public static List<Carta> listarCarta(Session ses) {
		try {
			ses.beginTransaction();
			Query consulta = ses.createQuery("from Carta");
			List<Carta> cartasList = consulta.getResultList();
			ses.getTransaction().commit();
			return cartasList;
		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}
	}

	public static void insertarPlato(Session ses, String nombre, TipoPlato tipo, Double precio, byte[] foto,
			List<Alergeno> alergenos) {

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

	public static void insertarBebida(Session ses, String nombre, byte[] foto, Double precio) {

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

	public static void insertarAlergeno(Session ses, String nombre, byte[] foto, Double precio) {

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

	public static void insertarComanda(Session ses, Mesa mesa, int plato, int cantidad) {

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

	public static void insertarEmpleado(Session ses, String nombre, String apellido, String genero, Date nacimiento,
			Date ingreso, byte[] foto) {

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

	public static void insertarMesa(Session ses, int personas, Boolean activa) {

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

	public static void insertarReserva(Session ses, Mesa mesa, Empleado empleado, LocalDateTime fecha, int personas) {

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

	public static void insertarTipoPlato(Session ses, String nombre) {

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
	
	public static List<Plato> listarPlatosCarta(Session ses, Carta carta) {
		try {
			ses.beginTransaction();
			List<Plato> res = ses.createQuery("FROM Plato WHERE carta = " + carta.getId()).list();
			ses.getTransaction().commit();
			return res;
		} catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
			return null;
			
		}
	}
}
