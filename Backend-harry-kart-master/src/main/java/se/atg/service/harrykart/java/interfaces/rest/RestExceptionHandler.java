package se.atg.service.harrykart.java.interfaces.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.atg.service.harrykart.java.domain.exception.ApiErrorModel;
import se.atg.service.harrykart.java.domain.exception.ValidationException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ApiErrorModel> handle(ValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorModel.builder()
                        .errorMessage(ex.getErrorMessage())
                        .details("VALIDATION_EXCEPTION")
                        .build()
                );
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiErrorModel> handle(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiErrorModel.builder()
                                .details("UNEXPECTED_EXCEPTION")
                                .errorMessage("An unexpected error has occurred")

                                .build()
                );
    }
}
