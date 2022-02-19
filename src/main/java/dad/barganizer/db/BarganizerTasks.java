package dad.barganizer.db;

import java.util.List;

import dad.barganizer.App;
import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class BarganizerTasks {

	private Carta cartaSeleccionada;
	
	public BarganizerTasks() {
	}
	
	public BarganizerTasks(Carta referencia) {
		this.cartaSeleccionada = referencia;
	}

	private Task<ObservableList<Bebida>> inicializarBebidasTask = new Task<ObservableList<Bebida>>() {

		@Override
		protected ObservableList<Bebida> call() throws Exception {
			List<Bebida> listaBebidas = FuncionesDB.listarBebidas(App.getBARGANIZERDB().getSes());

			return FXCollections.observableArrayList(listaBebidas);
		}
	};

	private Task<ObservableList<Mesa>> inicializarMesasTask = new Task<ObservableList<Mesa>>() {

		@Override
		protected ObservableList<Mesa> call() throws Exception {

			List<Mesa> listaMesas = FuncionesDB.listarMesas(App.getBARGANIZERDB().getSes());

			return FXCollections.observableArrayList(listaMesas);
		}
	};

	private Task<ObservableList<Plato>> inicializarPlatosTask = new Task<ObservableList<Plato>>() {

		@Override
		protected ObservableList<Plato> call() throws Exception {

			List<Plato> listaPlatos = FuncionesDB.listarPlatos(App.getBARGANIZERDB().getSes());

			return FXCollections.observableArrayList(listaPlatos);
		}
	};
	
	private Task<ObservableList<Carta>> inicializarCartaTask = new Task<ObservableList<Carta>>() {
		
		
		@Override
		protected ObservableList<Carta> call() throws Exception {
			
			List<Carta> listaCartas = FuncionesDB.listarCarta(App.getBARGANIZERDB().getSes());
			
			return FXCollections.observableArrayList(listaCartas);
		}
	};
	
	private Task<ObservableList<Plato>> obtenerPlatosCartaTask = new Task<ObservableList<Plato>>() {
		
		@Override
		protected ObservableList<Plato> call() throws Exception {
			List<Plato> listaPlatosCarta = null;
			if (cartaSeleccionada.getNombre().equals("Completa")) {
				listaPlatosCarta = FuncionesDB.listarPlatos(App.getBARGANIZERDB().getSes());
			} else {
				listaPlatosCarta = FuncionesDB.listarPlatosCarta(App.getBARGANIZERDB().getSes(), cartaSeleccionada);
			}
			
			return FXCollections.observableArrayList(listaPlatosCarta);
		}
	};
	
	private Task<ObservableList<Plato>> obtenerEntrantesTask = new Task<ObservableList<Plato>>() {
		
		@Override
		protected ObservableList<Plato> call() throws Exception {
			List<Plato> listaEntrantes = null;
			
			if (cartaSeleccionada.getNombre().equals("Completa")) {
				listaEntrantes = FuncionesDB.listarEntrantes(App.getBARGANIZERDB().getSes());
			} else {
				listaEntrantes = FuncionesDB.listarEntrantesCarta(App.getBARGANIZERDB().getSes(), cartaSeleccionada);
			}
			 
			
			return FXCollections.observableArrayList(listaEntrantes);
		}
	};
	
	private Task<ObservableList<Plato>> obtenerPostresTask = new Task<ObservableList<Plato>>() {
		
		@Override
		protected ObservableList<Plato> call() throws Exception {
			List<Plato> listaPostres = null;
			
			if (cartaSeleccionada.getNombre().equals("Completa")) {
				listaPostres = FuncionesDB.listarPostres(App.getBARGANIZERDB().getSes());
			} else {
				listaPostres = FuncionesDB.listarPostresCarta(App.getBARGANIZERDB().getSes(), cartaSeleccionada);
			}
			
			return FXCollections.observableArrayList(listaPostres);
		}
	};
	
	private Task<ObservableList<Empleado>> obtenerEmpleados = new Task<ObservableList<Empleado>>() {
		
		@Override
		protected ObservableList<Empleado> call() throws Exception {
			List<Empleado> listaEmpleado = null;
			
			listaEmpleado = FuncionesDB.listarEmpleados(App.getBARGANIZERDB().getSes());
			
			return FXCollections.observableArrayList(listaEmpleado);
		}
	};

	public Task<ObservableList<Bebida>> getInicializarBebidasTask() {
		return inicializarBebidasTask;
	}

	public Task<ObservableList<Mesa>> getInicializarMesasTask() {
		return inicializarMesasTask;
	}

	public Task<ObservableList<Plato>> getInicializarPlatosTask() {
		return inicializarPlatosTask;
	}
	
	public Task<ObservableList<Carta>> getInicializarCartaTask() {
		return inicializarCartaTask;
	}

	public Task<ObservableList<Plato>> getObtenerPlatosCartaTask() {
		return obtenerPlatosCartaTask;
	}
	
	public Task<ObservableList<Plato>> getObtenerEntrantesTask() {
		return obtenerEntrantesTask;
	}
	
	public Task<ObservableList<Plato>> getObtenerPostresTask() {
		return obtenerPostresTask;
	}
	
	public Task<ObservableList<Empleado>> getObtenerEmpleados() {
		return obtenerEmpleados;
	}
}
