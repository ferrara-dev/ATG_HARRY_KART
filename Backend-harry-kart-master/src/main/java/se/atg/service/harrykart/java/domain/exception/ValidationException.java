package se.atg.service.harrykart.java.domain.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{
    private String errorMessage;

    public ValidationException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
