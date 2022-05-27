package com.recipemanager.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to send Http Status No Content 204
 */
@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "No Content")
public class NoContentException extends Exception {
	public NoContentException() {
	}
}
