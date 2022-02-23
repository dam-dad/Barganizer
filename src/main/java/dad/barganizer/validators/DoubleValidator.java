package dad.barganizer.validators;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

import javafx.scene.control.Control;

public class DoubleValidator implements Validator<String>{

	
	@Override
	public ValidationResult apply(Control control, String value) {
   		boolean condition = false;
		try {
			Double.parseDouble(value);
		} catch (RuntimeException e) {
			condition = true;
		}
		catch (Exception e) {
			condition = true;
		}
        return ValidationResult.fromMessageIf(control, "Debe introducir un número", Severity.ERROR, condition);
	}
}
