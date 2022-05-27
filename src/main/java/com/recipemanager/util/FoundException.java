package com.recipemanager.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to send Http Status Found
 */
@ResponseStatus(code = HttpStatus.OK, reason = "Found")
public class FoundException extends Exception {
	public FoundException() {
	}
}
