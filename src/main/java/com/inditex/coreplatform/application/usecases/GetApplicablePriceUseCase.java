package com.inditex.coreplatform.application.usecases;

import com.inditex.coreplatform.application.usecases.queries.GetApplicablePriceQuery;
import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.domain.ports.IPriceService;

import reactor.core.publisher.Mono;

/**
 * Use case for retrieving the applicable price based on product ID, brand ID, and application date.
 */

public class GetApplicablePriceUseCase {
    private final IPriceService priceService;

    public GetApplicablePriceUseCase(IPriceService priceService) {
        this.priceService = priceService;
    }

    public Mono<Price> execute(GetApplicablePriceQuery query) {
        return priceService.getPriceByProductAndBrandIdAndApplicationDate(
            query.productId(),
            query.brandId(),
            query.applicationDate()
        );
    }
}
