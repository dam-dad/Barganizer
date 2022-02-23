package dad.barganizer.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;
import dad.barganizer.App;
import dad.barganizer.beansprop.ComandaProp;
import dad.barganizer.beansprop.EmpleadoProp;
import dad.barganizer.db.beans.Alergeno;
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

			Query consulta = ses.createQuery("from Plato WHERE tipoPlato = 2");
			List<Plato> platosList = consulta.getResultList();

			return platosList;

		} catch (Exception e) {
			System.err.println("Error.");
			ses.getTransaction().rollback();
			return null;
		}

	}

	public static List<Plato> listarBebidas(Session ses) {

		try {
			ses.beginTransaction();
			Query consulta = ses.createQuery("from Plato where tipoPlato = 3");
			List<Plato> bebidasList = consulta.getResultList();
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
			ses.beginTransaction();
			Query consulta = ses.createQuery("from Empleado");
			List<Empleado> empleadoList = consulta.getResultList();
			ses.getTransaction().commit();
			return empleadoList;

		} catch (Exception e) {
			System.err.println("Error.");
			e.printStackTrace();
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

	public static void insertarPlato(Session ses, String nombre, TipoPlato tipo, double precio, byte[] foto, Carta c) {

		try {

			ses.beginTransaction();

			Plato plato = new Plato();
			plato.setNombre(nombre);
			plato.setFoto(foto);
			plato.setTipoPlato(tipo);
			plato.setPrecio(precio);
			plato.setCarta(c);

			ses.persist(plato);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("Error insertando plato");
			throw e;
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

	public static void insertarComanda(Session ses, Mesa mesa, Plato plato, int cantidad) {

		try {
			ses.beginTransaction();

			List<Comanda> comprobacion = ses
					.createQuery("FROM Comanda where plato = " + plato.getId() + " AND mesa = " + mesa.getNumero())
					.list();

			if (comprobacion != null && comprobacion.size() != 0) {
				// Si existe un plato en la comanda, simplemente estableceremos su cantidad a +1
				ses.createNativeQuery("UPDATE comanda SET cantidad = cantidad+1 WHERE plato = " + plato.getId()
						+ " AND mesa = " + mesa.getNumero()).executeUpdate();
			} else {
				Comanda comanda = new Comanda();
				comanda.setMesa(mesa);
				comanda.setPlato(plato);
				comanda.setCantidad(cantidad);

				ses.persist(comanda);
			}
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}

	public static void insertarEmpleado(Session ses, String nombre, String apellido, String genero,
			LocalDate nacimiento, LocalDate ingreso, byte[] foto, String contrasena) {

		try {

			ses.beginTransaction();

			Empleado empleado = new Empleado();
			empleado.setNombre(nombre);
			empleado.setApellidos(apellido);
			empleado.setGenero(genero);
			empleado.setFnac(nacimiento);
			empleado.setFechaIngreso(ingreso);
			empleado.setFoto(foto);
			empleado.setPass(contrasena.getBytes());

			ses.persist(empleado);
			ses.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("No se ha podido completar la inserción: " + e.getMessage());
		}
	}

	public static void insertarEmpleado(Session ses, String nombre, String apellido, String genero,
			LocalDate nacimiento, LocalDate ingreso, byte[] foto, byte[] pass) {

		try {

			ses.beginTransaction();

			Empleado empleado = new Empleado();
			empleado.setNombre(nombre);
			empleado.setApellidos(apellido);
			empleado.setGenero(genero);
			empleado.setFnac(nacimiento);
			empleado.setFechaIngreso(ingreso);
			empleado.setFoto(foto);
			empleado.setPass(pass);
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
			List<Plato> res = ses.createQuery("FROM Plato WHERE carta = " + carta.getId() + " AND tipoPlato = 2")
					.list();
			ses.getTransaction().commit();
			return res;
		} catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
			return null;

		}
	}

	public static List<Plato> listarEntrantesCarta(Session ses, Carta c) {

		try {
			ses.beginTransaction();
			List<Plato> res = ses.createQuery(
					"SELECT p FROM Plato AS p INNER JOIN TipoPlato AS tp ON p.tipoPlato=tp.id WHERE p.carta = "
							+ c.getId() + " AND tp.nombre = 'Entrante'")
					.list();
			ses.getTransaction().commit();
			return res;
		} catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	public static List<Plato> listarEntrantes(Session ses) {

		try {
			ses.beginTransaction();
			List<Plato> res = ses.createQuery(
					"SELECT p FROM Plato AS p INNER JOIN TipoPlato AS tp ON p.tipoPlato=tp.id WHERE tp.nombre = 'Entrante'")
					.list();
			ses.getTransaction().commit();
			return res;
		} catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	public static List<Plato> listarPostresCarta(Session ses, Carta c) {

		try {
			ses.beginTransaction();
			List<Plato> res = ses.createQuery(
					"SELECT p FROM Plato AS p INNER JOIN TipoPlato AS tp ON p.tipoPlato=tp.id WHERE p.carta = "
							+ c.getId() + " AND tp.nombre = 'Postre'")
					.list();
			ses.getTransaction().commit();
			return res;
		} catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	public static List<Plato> listarPostres(Session ses) {

		try {
			ses.beginTransaction();
			List<Plato> res = ses.createQuery(
					"SELECT p FROM Plato AS p INNER JOIN TipoPlato AS tp ON p.tipoPlato=tp.id WHERE tp.nombre = 'Postre'")
					.list();
			ses.getTransaction().commit();
			return res;
		} catch (Exception e) {
			ses.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	public static void eliminarMesa(Session sesion, Mesa mesa) {

		try {

			sesion.beginTransaction();

			Mesa m = sesion.get(Mesa.class, mesa.getNumero());
			sesion.delete(m);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			// App.error("Error", "Error al borrar", "No puede borrar una mesa con una
			// comanda activa.");

			sesion.getTransaction().rollback();
			throw e;
		}
	}

	public static void eliminarAlergeno(Session sesion, Alergeno alergeno) {

		try {

			sesion.beginTransaction();

			Alergeno a = sesion.get(Alergeno.class, alergeno.getId());
			sesion.delete(a);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("No se puede eliminar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}
	}

	public static void eliminarCarta(Session sesion, Carta carta) {

		try {

			sesion.beginTransaction();

			Carta c = sesion.get(Carta.class, carta.getId());
			sesion.delete(c);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("No se puede eliminar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}
	}

	public static void eliminarComanda(Session sesion, Comanda comanda) {

		try {

			sesion.beginTransaction();

			Comanda c = sesion.get(Comanda.class, comanda.getId());

			if (comanda.getCantidad() > 1) {
				c.setCantidad(c.getCantidad() - 1);
				sesion.save(c);
			} else {
				sesion.delete(c);
			}
			sesion.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("No se puede eliminar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}
	}

	public static void eliminarEmpleado(Session sesion, EmpleadoProp empleadoProp) {

		try {

			sesion.beginTransaction();

			Empleado e = sesion.get(Empleado.class, empleadoProp.getId());
			sesion.delete(e);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("No se puede eliminar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}
	}

	public static void eliminarPlato(Session sesion, Plato plato) {

		try {

			sesion.beginTransaction();

			Plato p = sesion.get(Plato.class, plato.getId());
			sesion.delete(p);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			sesion.getTransaction().rollback();
			throw e;
		}
	}

	public static void eliminarReserva(Session sesion, Reserva reserva) {

		try {

			sesion.beginTransaction();

			Reserva r = sesion.get(Reserva.class, reserva.getId());
			sesion.delete(r);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("No se puede eliminar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}
	}

	public static void eliminarTipo(Session sesion, TipoPlato tipo) {

		try {

			sesion.beginTransaction();

			TipoPlato t = sesion.get(TipoPlato.class, tipo.getId());
			sesion.delete(t);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("No se puede eliminar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}
	}

	public static void modificarAlergeno(Session sesion, Alergeno alergeno) {

		try {

			sesion.beginTransaction();

			Alergeno a = sesion.get(Alergeno.class, alergeno.getId());
			a.setNombre(alergeno.getNombre());
			a.setIcono(alergeno.getIcono());
			sesion.update(a);

			sesion.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No se puede modificar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}

	}

	public static void modificarCarta(Session sesion, Carta carta) {

		try {

			sesion.beginTransaction();

			Carta c = sesion.get(Carta.class, carta.getId());
			c.setNombre(carta.getNombre());
			sesion.update(c);

			sesion.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("No se puede modificar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}

	}

	public static void modificarComanda(Session sesion, Comanda comanda) {

		try {

			sesion.beginTransaction();

			Comanda c = sesion.get(Comanda.class, comanda.getId());
			c.setMesa(comanda.getMesa());
			c.setCantidad(comanda.getCantidad());
			c.setPlato(comanda.getPlato());
			sesion.update(c);

			sesion.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No se puede modificar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}

	}

	public static void modificarMesa(Session sesion, Mesa mesa) {

		try {

			sesion.beginTransaction();

			Mesa m = sesion.get(Mesa.class, mesa.getNumero());
			m.setCantPersonas(mesa.getCantPersonas());
			m.setActiva(mesa.isActiva());
			sesion.update(m);

			sesion.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No se puede modificar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}

	}

	public static void modificarPlato(Session sesion, Plato plato) {

		try {

			sesion.beginTransaction();

			Plato p = sesion.get(Plato.class, plato.getId());
			p.setNombre(plato.getNombre());
			p.setFoto(plato.getFoto());
			p.setPrecio(plato.getPrecio());
			p.setTipoPlato(plato.getTipoPlato());
			p.setCarta(plato.getCarta());
			sesion.update(p);

			sesion.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No se puede modificar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
			throw e;
		}

	}

	public static void modificarReserva(Session sesion, Reserva reserva) {

		try {

			sesion.beginTransaction();

			Reserva r = sesion.get(Reserva.class, reserva.getId());
			r.setCantPersonas(reserva.getCantPersonas());
			r.setEmpleadoReserva(reserva.getEmpleadoReserva());
			r.setFecha(reserva.getFecha());
			r.setMesaReserva(reserva.getMesaReserva());
			sesion.update(r);

			sesion.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No se puede modificar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}

	}

	public static void modificarTipo(Session sesion, TipoPlato tipo) {

		try {

			sesion.beginTransaction();

			TipoPlato t = sesion.get(TipoPlato.class, tipo.getId());
			t.setNombre(tipo.getNombre());
			sesion.update(t);

			sesion.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("No se puede modificar el registro: " + e.getMessage());
			sesion.getTransaction().rollback();
		}

	}

	public static List<Mesa> listarMesasActivas(Session ses) {

		try {
			ses.beginTransaction();
			Query consulta = ses.createQuery("from Mesa where activa = true");
			List<Mesa> mesasList = consulta.getResultList();
			ses.getTransaction().commit();
			return mesasList;

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			ses.getTransaction().rollback();
			return null;
		}
	}

	public static List<Comanda> listarComandasMesa(Session ses, Mesa m) {
		try {
			ses.beginTransaction();
			List<Comanda> listaComandas = ses.createQuery("FROM Comanda WHERE mesa = " + m.getNumero()).list();
			ses.getTransaction().commit();
			return listaComandas;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			ses.getTransaction().rollback();
			return null;
		}
	}

	public static void modificarEmpleado(Session ses, EmpleadoProp empleadoProp) {
		try {

			ses.beginTransaction();

			Empleado emp = ses.get(Empleado.class, empleadoProp.getId());

			emp.setNombre(empleadoProp.getNombre());
			emp.setApellidos(empleadoProp.getApellido());
			emp.setGenero(empleadoProp.getGenero().toString());
			emp.setFnac(empleadoProp.getNacimiento());
			// emp.setFoto(empleadoProp.getFoto());
			emp.setPass(empleadoProp.getPassword().getBytes());
			emp.setFechaIngreso(empleadoProp.getIngreso());

			ses.update(emp);

			ses.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println("No se puede modificar el registro: " + ex.getMessage());
			ses.getTransaction().rollback();
		}
	}

	public static void eliminarComandasMesa(Session ses, Mesa m) {
		try {

			ses.beginTransaction();
			ses.createQuery("DELETE FROM Comanda WHERE mesa = " + m.getNumero()).executeUpdate();

			ses.getTransaction().commit();

		} catch (Exception ex) {
			System.err.println("Hubo un error al eliminar las comandas de la mesa: ");
			ses.getTransaction().rollback();
		}
	}

	public static void insertarCarta(Session ses, String nombreCarta) {
		try {
			ses.beginTransaction();
			Carta c = new Carta();
			c.setNombre(nombreCarta);
			ses.persist(c);
			ses.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Hubo un error al insertar la carta ");
			ses.getTransaction().rollback();
			throw e;
		}
	}

	public static Carta obtenerCarta(Session ses, String nombre) {
		try {
			ses.beginTransaction();
			Carta retorno = null;
			List<Carta> res = ses.createQuery("FROM Carta WHERE nombre LIKE '" + nombre + "'").list();
			if (res.size() == 1) {
				retorno = res.get(0);
			}
			ses.getTransaction().commit();
			return retorno;
		} catch (Exception e) {
			App.error("Error", "Excepción obteniendo carta", "Detalles: " + e.getMessage());
			return null;

		}
	}

	public static void eliminarPlatosCarta(Session ses, Carta c) {
		try {
			ses.beginTransaction();
			ses.createQuery("DELETE FROM Plato WHERE carta = " + c.getId()).executeUpdate();
			ses.getTransaction().commit();

		} catch (Exception e) {
			ses.getTransaction().rollback();
			App.error("Error", "Error eliminando platos de la carta",
					"Es posible que algún plato se encuentre en una comanda activa. Detalles: " + e.getMessage());

		}
	}

}
