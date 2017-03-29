package com.deltaa.superrduperr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author denigeorge
 *	Exception if the Item with itemId is not found in the list.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException(Integer itemId){
		super("could not find list '" + itemId + "'.");
	}
}
