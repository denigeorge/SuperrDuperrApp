package com.deltaa.superrduperr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author denigeorge
 *	Exception when there is no items in the list.
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoItemsException extends RuntimeException {
	public NoItemsException(){
		super("The list is empty.");
	}
}
