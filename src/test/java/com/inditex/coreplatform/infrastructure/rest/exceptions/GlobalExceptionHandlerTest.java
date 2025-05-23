package com.inditex.coreplatform.infrastructure.rest.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MissingRequestValueException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleTypeMismatch_returnsBadRequest() {
        TypeMismatchException ex = new TypeMismatchException("value", String.class);
        ResponseEntity<String> response = handler.handleTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Parámetro"));
        assertTrue(response.getBody().contains("inválido"));
    }

    @Test
    void handleTypeMismatch_withMethodArgumentTypeMismatch_returnsBadRequest() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "abc", Integer.class, "id", null, new IllegalArgumentException("bad type"));
        ResponseEntity<String> response = handler.handleTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Parámetro 'id' inválido: 'abc'. Se esperaba tipo Integer."));
    }

    @Test
    void handleMissingParams_returnsBadRequest() {
        MissingRequestValueException ex = mock(MissingRequestValueException.class);
        when(ex.getName()).thenReturn("fecha");
        ResponseEntity<String> response = handler.handleMissingParams(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Falta el parámetro obligatorio 'fecha'.", response.getBody());
    }

    @Test
    void handleConstraintViolation_returnsBadRequestWithMessage() {
        @SuppressWarnings("unchecked")
        ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
        Path mockPath = mock(jakarta.validation.Path.class);
        when(mockPath.toString()).thenReturn("param");
        when(violation.getPropertyPath()).thenReturn(mockPath);
        when(violation.getMessage()).thenReturn("no puede ser nulo");
        Set<ConstraintViolation<?>> violations = Collections.singleton(violation);

        ConstraintViolationException ex = new ConstraintViolationException(violations);
        ResponseEntity<String> response = handler.handleConstraintViolation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() != null && response.getBody().contains("'param' no puede ser nulo"));
    }

    @Test
    void handleConstraintViolation_returnsDefaultMessageIfNoViolations() {
        ConstraintViolationException ex = new ConstraintViolationException(Collections.emptySet());
        ResponseEntity<String> response = handler.handleConstraintViolation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Parámetro inválido.", response.getBody());
    }

    @Test
    void handleDateParseError_returnsBadRequest() {
        DateTimeParseException ex = new DateTimeParseException("Invalid format", "bad-date", 0);
        ResponseEntity<String> response = handler.handleDateParseError(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Formato de fecha inválido"));
    }

    @Test
    void handleGenericException_returnsInternalServerError() {
        Exception ex = new Exception("Unexpected error");
        ResponseEntity<String> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocurrió un error inesperado. Por favor, inténtelo más tarde.", response.getBody());
    }
}