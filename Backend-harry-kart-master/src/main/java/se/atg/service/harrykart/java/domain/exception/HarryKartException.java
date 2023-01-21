package se.atg.service.harrykart.java.domain.exception;

public class HarryKartException extends RuntimeException{
    int errorCode;

    public HarryKartException() {
    }

    public HarryKartException(String message) {
        super(message);
    }

    public HarryKartException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
