package dad.barganizer.reports;

import java.io.ByteArrayInputStream;
import java.util.List;

import dad.barganizer.beansprop.ComandaProp;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/** Esta clase nos ayudará a representar la datasource que se le proporcionará
 * al formulario. Implementa una interfaz JRDataSource en la cual
 * definimos la forma en la que se recorre una colección de elementos
 * y la forma en la que se asignan los valores a los campos del formulario**/
public class ComandasDataSource implements JRDataSource{

	private List<ComandaProp> comandas;
	private int index = -1;
	
	public ComandasDataSource(ObservableList<ComandaProp> lista) {
		this.comandas = lista;
	}

	
	@Override
	public boolean next() throws JRException {
		index++;
		return (index < comandas.size());
	}

	/** Este método nos ayudará a tratar el campo leído en el fichero jasper y proporcionar
	 * el valor correspondiente del elemento encontrado en la lista**/
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		Object valor = null;
		
		String nombreCampo = jrField.getName();
		
		switch (nombreCampo) {
		case "Orden":
			valor = index+1;
			break;
		case "Plato":
			valor = comandas.get(index).getNombrePlato();
			break;
		case "Precio":
			valor = comandas.get(index).getPrecioUnidad();
			break;
		case "Cantidad":
			valor = comandas.get(index).getCantidad();
			break;
		case "Cuantia":
			valor = (comandas.get(index).getCantidad()*(comandas.get(index).getPrecioUnidad()));
			break;
		case "rutalogo":
			valor = (comandas.get(index).getRutaLogo());
			break;
		case "rutalogoder":
			valor = "report_models/coffee_stain.png";
			break;
		case "foto":
			valor = comandas.get(index).getFotoPlato();
		case "Total":
			double suma = 0;
			for (ComandaProp comanda : comandas) {
				suma += (comanda.getPrecioUnidad()*comanda.getCantidad());
			}
			valor = suma;
			break;
		}
		return valor;
	}

	
}
