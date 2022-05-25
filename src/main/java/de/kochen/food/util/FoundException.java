package de.kochen.food.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.OK, reason = "Found")
public class FoundException extends Exception {
    public FoundException() {
    }
}
