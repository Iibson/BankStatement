package pl.nullreference.bankstatement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidTextFieldException extends Exception{
    private final String message;
}
