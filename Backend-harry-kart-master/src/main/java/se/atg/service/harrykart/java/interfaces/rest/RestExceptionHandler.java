package se.atg.service.harrykart.java.interfaces.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import se.atg.service.harrykart.java.domain.exception.ApiErrorModel;
import se.atg.service.harrykart.java.domain.exception.HarryKartException;
import se.atg.service.harrykart.java.domain.exception.ValidationException;

@ControllerAdvice
@RestController
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
    @ExceptionHandler(HarryKartException.class)
    public final ResponseEntity<ApiErrorModel> handle(HarryKartException ex) {
        return ResponseEntity
                .status(ex.getErrorCode())
                .body(ApiErrorModel.builder()
                        .errorMessage(ex.getErrorMessage())
                        .details(ex.getDetails())
                        .build()
                );
    }
}
