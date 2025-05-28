package com.inditex.coreplatform.infrastructure.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.ServerWebInputException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String UNKNOWN = "desconocido";

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<String> handleWebInputException(ServerWebInputException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof org.springframework.beans.TypeMismatchException tmex) {
            String name = tmex.getPropertyName() != null ? tmex.getPropertyName() : UNKNOWN;
            Object value = tmex.getValue();
            Class<?> requiredType = tmex.getRequiredType();

            String typeName = requiredType != null ? requiredType.getSimpleName() : UNKNOWN;
            String message = String.format("Parámetro '%s' inválido: '%s'. Se esperaba tipo %s.",
                    name,
                    value != null ? value : UNKNOWN,
                    typeName);
            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.badRequest()
                .body("Parámetro inválido: " + ex.getReason());
    }

    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<String> handleMissingParams(MissingRequestValueException ex) {
        String name = ex.getName();
        String message = String.format("Falta el parámetro obligatorio '%s'.", name);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> String.format("'%s' %s", v.getPropertyPath(), v.getMessage()))
                .findFirst()
                .orElse("Parámetro inválido.");
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error inesperado.");
    }
}
