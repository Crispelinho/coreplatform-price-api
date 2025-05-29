package com.inditex.coreplatform.application.usecases.queries;

import java.time.LocalDateTime;

public record GetApplicablePriceQuery(
    Integer productId,
    Integer brandId,
    LocalDateTime applicationDate
) {}