package dad.barganizer.db;

import java.util.List;

import dad.barganizer.App;
import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class BarganizerTasks {

	public BarganizerTasks() {
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

	public Task<ObservableList<Bebida>> getInicializarBebidasTask() {
		return inicializarBebidasTask;
	}

	public Task<ObservableList<Mesa>> getInicializarMesasTask() {
		return inicializarMesasTask;
	}

	public Task<ObservableList<Plato>> getInicializarPlatosTask() {
		return inicializarPlatosTask;
	}

}
