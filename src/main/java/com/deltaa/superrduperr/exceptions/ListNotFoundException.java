package com.deltaa.superrduperr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author denigeorge
 *	Exception when the list with listId not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ListNotFoundException extends RuntimeException {
	public ListNotFoundException(Integer listId){
		super("could not find list '" + listId + "'.");
	}
}
