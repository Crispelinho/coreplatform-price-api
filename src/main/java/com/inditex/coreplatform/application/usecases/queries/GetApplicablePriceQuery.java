package com.inditex.coreplatform.application.usecases.queries;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetApplicablePriceQuery implements Query {
    private final Integer productId;
    private final Integer brandId;
    private final LocalDateTime applicationDate;

    public GetApplicablePriceQuery(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        this.productId = productId;
        this.brandId = brandId;
        this.applicationDate = applicationDate;
    }
}