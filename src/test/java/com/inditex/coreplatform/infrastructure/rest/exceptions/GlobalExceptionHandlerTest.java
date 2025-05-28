package com.inditex.coreplatform.infrastructure.rest.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.ServerWebInputException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleWebInputException_withTypeMismatch_returnsDetailedMessage() {
        TypeMismatchException cause = mock(TypeMismatchException.class);
        when(cause.getPropertyName()).thenReturn("productId");
        when(cause.getValue()).thenReturn("abc");
        when(cause.getRequiredType()).thenReturn((Class) Integer.class);

        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.getParameterName()).thenReturn("productId");
        ServerWebInputException ex = new ServerWebInputException("Parámetro inválido", parameter, cause);

        ResponseEntity<String> response = handler.handleWebInputException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Parámetro 'productId' inválido: 'abc'. Se esperaba tipo Integer.", response.getBody());
    }

    @Test
    void handleWebInputException_withNullFields_returnsDesconocido() {
        // Causa con campos nulos
        TypeMismatchException cause = mock(TypeMismatchException.class);
        when(cause.getPropertyName()).thenReturn(null);
        when(cause.getValue()).thenReturn(null);
        when(cause.getRequiredType()).thenReturn(null);

        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.getParameterName()).thenReturn("desconocido");

        ServerWebInputException ex = new ServerWebInputException("Parámetro inválido", parameter, cause);

        ResponseEntity<String> response = handler.handleWebInputException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Parámetro 'desconocido' inválido: 'desconocido'. Se esperaba tipo desconocido.", response.getBody());
    }

    @Test
    void handleWebInputException_withOtherCause_returnsGenericReason() {
        // Causa distinta a TypeMismatchException
        Throwable otherCause = new IllegalArgumentException("invalid param");
        MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.getParameterName()).thenReturn("parameter");
        ServerWebInputException ex = new ServerWebInputException("Parámetro inválido", parameter, otherCause);

        ResponseEntity<String> response = handler.handleWebInputException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Parámetro inválido: Parámetro inválido", response.getBody());
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
    void handleAll_withGenericException_returnsInternalServerError() {
        Exception ex = new Exception("Error inesperado");

        ResponseEntity<String> response = handler.handleAll(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocurrió un error inesperado.", response.getBody());
    }
}
