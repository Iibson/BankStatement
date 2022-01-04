package pl.nullreference.bankstatement.services;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Service;
import pl.nullreference.bankstatement.exception.InvalidTextFieldException;


@Service
public class Validator {

    private final String DECIMAL_NUMBER_REGEX = "\\A[-]?[0-9]+[,.]?[0-9]*\\Z";

    private final String INTEGER_NUMBER_REGEX = "^[^a-zA-Z]*$";

    public void validateDecimalNumber(TextField textField, String fieldName) throws InvalidTextFieldException {
        if(!textField.getText().matches(DECIMAL_NUMBER_REGEX)) {
            throw new InvalidTextFieldException(fieldName + " can only contain decimal number");
        }
    }

    public void validateIntegerNumber(TextField textField, String fieldName) throws InvalidTextFieldException{
        if(!textField.getText().matches(INTEGER_NUMBER_REGEX)) {
            throw new InvalidTextFieldException(fieldName + " can only contain numbers.");
        }
    }
}
