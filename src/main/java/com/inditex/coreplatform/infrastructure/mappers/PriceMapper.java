package com.inditex.coreplatform.infrastructure.mappers;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.infrastructure.persistence.entities.PriceEntity;
import com.inditex.coreplatform.infrastructure.rest.controllers.dtos.PriceResponse;

public interface PriceMapper {

    Price toDomain(PriceEntity entity);
    
    PriceEntity toEntity(Price domain);

    PriceResponse toResponse(Price domain);
}
