package dad.barganizer.validators;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

import javafx.scene.control.Control;
/**
 * 
 * Validador encargado de controlar el uso estricto de un dato que no sea "null" y cuya longitud sea mayor de cero.
 *
 */
public class NombrePlatoValidator implements Validator<String> {

	@Override
	public ValidationResult apply(Control t, String value) {
		boolean condicion = true;
		
		if (value != null && value.length() > 0) {
			condicion = false;
		}
		
		return ValidationResult.fromMessageIf(t, "Debe introducir un nombre de plato", Severity.ERROR, condicion);
	}

}
