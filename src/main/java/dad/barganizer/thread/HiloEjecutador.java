package dad.barganizer.thread;

import java.util.concurrent.Semaphore;

import dad.barganizer.App;
import javafx.concurrent.Task;

/** HiloEjecutador representa un hilo se encarga de ejecutar una tarea, pasándole un semáforo como referencia y una tarea
 * a ejecutar en su constructor.
 * Cada vez que se ejecuta este hilo y la tarea termina su ejecución,
 * se reestablece la sesión con la base de datos para obtener sus datos actualizados,
 * independientemente de la operación realizada sobre la misma o su resultado. **/
public class HiloEjecutador extends Thread {

	private Semaphore semaforo;
	private Task<?> tarea;

	/** Recibe un semáforo como referencia, que en la aplicación principal se le asigna un único permiso,
	 * para evitar que los diferentes hilos de la aplicación puedan "pisarse" entre ellos y obtener resultados
	 * no esperados en las consultas. **/
	public HiloEjecutador(Semaphore ref, Task<?> tarea) {
		this.semaforo = ref;
		this.tarea = tarea;
	}

	/** Al ejecutarse el hilo, solicita permiso al semáforo y cuando éste se lo brinde, se ejecutará
	 * la operación sobre la base de datos a través de la tarea, y se reestablecerá la conexión de la
	 * aplicación para obtener sus datos más recientes **/
	@Override
	public void run() {

		try {
			semaforo.acquire(); // Solicitamos acceso al semáforo

			tarea.run(); // Ejecutamos la tarea

			App.getBARGANIZERDB().resetSesion(); // Establecemos un refresco de sesión para obtener los nuevos datos actualizados de la base de datos
			
			semaforo.release(); // Liberamos el permiso una vez se ejecute la tarea
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
