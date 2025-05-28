package com.inditex.coreplatform.application.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(Integer productId, Integer brandId) {
        super("No price found for productId " + productId + " and brandId " + brandId);
    }
}
