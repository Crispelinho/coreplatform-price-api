package com.inditex.coreplatform.infrastructure.rest.exceptions;

import java.time.format.DateTimeParseException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MissingRequestValueException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(TypeMismatchException ex) {
        String name = ex.getPropertyName();
        Object value = ex.getValue();
        Class<?> requiredType = ex.getRequiredType();

        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException matme = (MethodArgumentTypeMismatchException) ex;
            name = matme.getPropertyName();
            value = matme.getValue();
            requiredType = matme.getRequiredType();
        }

        String typeName = (requiredType != null) ? requiredType.getSimpleName() : "desconocido";
        String message = String.format("Parámetro '%s' inválido: '%s'. Se esperaba tipo %s.",
                (name != null) ? name : "desconocido",
                (value != null) ? value : "desconocido",
                typeName);
        return ResponseEntity.badRequest().body(message);
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

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateParseError(DateTimeParseException ex) {
        return ResponseEntity.badRequest()
                .body("Formato de fecha inválido. Use el formato ISO 8601 (e.g., 2024-05-20T15:30:00).");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error inesperado. Por favor, inténtelo más tarde.");
    }
}
