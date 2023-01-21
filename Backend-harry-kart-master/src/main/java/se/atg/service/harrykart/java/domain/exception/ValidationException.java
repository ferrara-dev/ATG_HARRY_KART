package se.atg.service.harrykart.java.domain.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException{
    public static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public ValidationException(String message) {
        super(message);
    }
}
