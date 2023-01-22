package se.atg.service.harrykart.java.interfaces;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import se.atg.service.harrykart.java.domain.exception.ApiErrorModel;
import se.atg.service.harrykart.java.domain.exception.ValidationException;
import se.atg.service.harrykart.java.interfaces.rest.RestExceptionHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("java-test")
public class RestExceptionHandlerTest {

    private RestExceptionHandler handler;

    @BeforeAll
    void setUp() {
        handler = new RestExceptionHandler();
    }

    @DisplayName("Should handle exception")
    @Test
    public void shouldHandleException() throws IOException {
        ResponseEntity<ApiErrorModel> errorResponse = handler.handle(new Exception());
        assertEquals(errorResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DisplayName("Should handle validation exception")
    @Test
    public void shouldHandleValidationException() {
        ResponseEntity<ApiErrorModel> errorResponse = handler.handle(new ValidationException(""));
        assertEquals( errorResponse.getBody().getDetails(), "VALIDATION_EXCEPTION");
        assertEquals(errorResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
