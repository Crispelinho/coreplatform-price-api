package com.inditex.coreplatform.application.usecases;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.domain.ports.IPriceService;

import reactor.core.publisher.Flux;

public class GetPricesUseCase {
    private final IPriceService priceService;

    public GetPricesUseCase(IPriceService priceService) {
        this.priceService = priceService;
    }

    public Flux<Price> execute() {
        return priceService.getAllPrices();
    }

}
