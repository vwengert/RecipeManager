package de.kochen.food.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Id is not allowed")
public class IdNotAllowedException extends Exception {
    public IdNotAllowedException() {
    }
}
