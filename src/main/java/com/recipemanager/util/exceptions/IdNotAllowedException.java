package com.recipemanager.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to send Http Status Id not found or allowed
 */
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Id is not allowed")
public class IdNotAllowedException extends Exception {
	public IdNotAllowedException() {
	}
}
