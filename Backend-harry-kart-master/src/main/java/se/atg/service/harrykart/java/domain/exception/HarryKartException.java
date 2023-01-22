package se.atg.service.harrykart.java.domain.exception;

import lombok.Getter;

@Getter
public class HarryKartException extends RuntimeException{
    private int errorCode;
    private String errorMessage;

    private String details;

    public HarryKartException(String message,String details, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
        this.errorMessage = message;
    }

}
