package com.recipemanager.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to send Http Status Id not found or allowed
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Id is not allowed")
public class IdNotAllowedException extends Exception {
	public IdNotAllowedException() {
	}
}
