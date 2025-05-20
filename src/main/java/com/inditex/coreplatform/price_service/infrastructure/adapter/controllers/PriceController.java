package com.inditex.coreplatform.price_service.infrastructure.adapter.controllers;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.inditex.coreplatform.price_service.application.service.PriceService;
import com.inditex.coreplatform.price_service.infrastructure.adapter.db.entities.PricesEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping(value = "/prices", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public ResponseEntity<Flux<PricesEntity>> getPrices(
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Integer brandId,
            @RequestParam("applicationDate") LocalDateTime applicationDate){
           //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        
        Flux<PricesEntity> pricesFlux = priceService.getPrice(productId, brandId, applicationDate);

    return pricesFlux.hasElements()
            .flatMap(hasElements -> {
                if (hasElements) {
                    return Mono.just(ResponseEntity.ok(pricesFlux));
                } else {
                    return Mono.just(ResponseEntity.notFound().build());
                }
            });
            }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        Class<?> requiredType = ex.getRequiredType();
        String type = requiredType != null ? requiredType.getSimpleName() : "unknown";
        String value = (ex != null && ex.getValue() != null) ? String.valueOf(ex.getValue()) : "null";
        String message = String.format("El parámetro '%s' con valor '%s' no es válido para el tipo '%s'.", name, value, type);
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        String message = String.format("Falta el parámetro obligatorio '%s'.", name);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error inesperado. Por favor, inténtelo más tarde.");
    }
}