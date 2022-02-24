package dad.barganizer.db;

import java.util.ArrayList;
import java.util.List;

import dad.barganizer.App;
import dad.barganizer.beansprop.CartaProp;
import dad.barganizer.beansprop.EmpleadoProp;
import dad.barganizer.db.beans.Carta;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import dad.barganizer.db.beans.Reserva;
import dad.barganizer.db.beans.TipoPlato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * 
 * Clase que declara las tareas que ejecutan funciones que acceden a la base de
 * datos, las cuales serán controladas al llamarse en el código para gestionar
 * un correcto flujo de ejecución del proyecto.
 */
public class BarganizerTasks {

	private Carta cartaSeleccionada;

	public BarganizerTasks() {
	}

	public BarganizerTasks(Carta referencia) {
		this.cartaSeleccionada = referencia;
	}

	private Task<ObservableList<Plato>> inicializarBebidasTask = new Task<ObservableList<Plato>>() {

		@Override
		protected ObservableList<Plato> call() throws Exception {
			List<Plato> listaBebidas = FuncionesDB.listarBebidas(App.getBARGANIZERDB().getSes());

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

	private Task<ObservableList<Reserva>> inicializarReservasTask = new Task<ObservableList<Reserva>>() {

		@Override
		protected ObservableList<Reserva> call() throws Exception {

			List<Reserva> listaReserva = FuncionesDB.listarReservas(App.getBARGANIZERDB().getSes());

			return FXCollections.observableArrayList(listaReserva);
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
			List<Plato> listaPostres = FuncionesDB.listarPostres(App.getBARGANIZERDB().getSes());

			return FXCollections.observableArrayList(listaPostres);
		}
	};

	private Task<ObservableList<EmpleadoProp>> obtenerEmpleadosTask = new Task<ObservableList<EmpleadoProp>>() {

		@Override
		protected ObservableList<EmpleadoProp> call() throws Exception {
			List<Empleado> listaEmpleado = null;

			listaEmpleado = FuncionesDB.listarEmpleados(App.getBARGANIZERDB().getSes());
			List<EmpleadoProp> listaProp = new ArrayList<>();
			for (Empleado empleado : listaEmpleado) {
				listaProp.add(new EmpleadoProp(empleado));
			}

			return FXCollections.observableArrayList(listaProp);
		}
	};

	private Task<ObservableList<Mesa>> obtenerMesasActivasTask = new Task<ObservableList<Mesa>>() {

		@Override
		protected ObservableList<Mesa> call() throws Exception {

			List<Mesa> listaMesas = FuncionesDB.listarMesasActivas(App.getBARGANIZERDB().getSes());

			return FXCollections.observableArrayList(listaMesas);
		}
	};

	private Task<ObservableList<TipoPlato>> obtenerTiposPlatoTask = new Task<ObservableList<TipoPlato>>() {

		@Override
		protected ObservableList<TipoPlato> call() throws Exception {

			List<TipoPlato> listaTipos = FuncionesDB.listarTipo(App.getBARGANIZERDB().getSes());

			return FXCollections.observableArrayList(listaTipos);
		}
	};

	private Task<ObservableList<CartaProp>> inicializarCartaPropTask = new Task<ObservableList<CartaProp>>() {

		@Override
		protected ObservableList<CartaProp> call() throws Exception {
			List<Carta> listaCartas = FuncionesDB.listarCarta(App.getBARGANIZERDB().getSes());
			List<CartaProp> listaProps = new ArrayList<>();
			for (Carta carta : listaCartas) {
				listaProps.add(new CartaProp(carta));
			}
			return FXCollections.observableArrayList(listaProps);
		}
	};

	public Task<ObservableList<Plato>> getInicializarBebidasTask() {
		return inicializarBebidasTask;
	}

	public Task<ObservableList<Mesa>> getInicializarMesasTask() {
		return inicializarMesasTask;
	}

	public Task<ObservableList<Reserva>> getInicializarReservasTask() {
		return inicializarReservasTask;
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

	public Task<ObservableList<EmpleadoProp>> getObtenerEmpleadosTask() {
		return obtenerEmpleadosTask;
	}

	public Task<ObservableList<Mesa>> getObtenerMesasActivasTask() {
		return obtenerMesasActivasTask;
	}

	public Task<ObservableList<TipoPlato>> getObtenerTiposPlatoTask() {
		return obtenerTiposPlatoTask;
	}

	public Task<ObservableList<CartaProp>> getInicializarCartaPropTask() {
		return inicializarCartaPropTask;
	}
}
