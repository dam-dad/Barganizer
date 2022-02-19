package dad.barganizer.thread;

import java.util.concurrent.Semaphore;

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
			
			semaforo.release(); // Liberamos el permiso una vez se ejecute la tarea
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
