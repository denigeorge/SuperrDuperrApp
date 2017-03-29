package com.deltaa.superrduperr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author denigeorge
 * Exception for Incorrect date format in setting reminder.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectFormatException extends RuntimeException {
	public IncorrectFormatException(){
		super("There are no lists.");
	}
}
