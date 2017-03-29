package com.deltaa.superrduperr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author denigeorge
 * Exception when there is no list.
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoListException extends RuntimeException {
	public NoListException(){
		super("There are no lists.");
	}
}
