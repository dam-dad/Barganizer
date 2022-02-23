package dad.barganizer.thread;

import java.util.concurrent.Semaphore;

import dad.barganizer.App;
import javafx.concurrent.Task;

public class HiloEjecutador extends Thread {

	private Semaphore semaforo;
	private Task<?> tarea;

	public HiloEjecutador(Semaphore ref, Task<?> tarea) {
		this.semaforo = ref;
		this.tarea = tarea;
	}

	@Override
	public void run() {

		try {
			semaforo.acquire(); // Solicitamos acceso al semáforo

			tarea.run(); // Ejecutamos la tarea

			App.getBARGANIZERDB().resetSesion();
			semaforo.release(); // Liberamos el permiso una vez se ejecute la tarea
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
