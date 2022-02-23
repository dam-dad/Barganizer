package dad.barganizer.db;

import org.hibernate.Session;


/**
 * Representa la sesión general de la aplicación. Esta clase es un encapsulado
 * de una Sesión de Hibernate, y cuyos métodos
 **/
public class BarganizerDB {

	/** Sesión de Hibernate que representará la conexión con la base de datos. **/
	private Session ses;

	/** Constructor vacío que inicializa la sesión **/
	public BarganizerDB() {
		this.ses = HibernateUtil.getSessionFactory().openSession();
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

	public void setSes(Session s) {
		this.ses = s;
	}

	public void resetSesion() {
		this.ses = HibernateUtil.getSessionFactory().openSession();
	}

}