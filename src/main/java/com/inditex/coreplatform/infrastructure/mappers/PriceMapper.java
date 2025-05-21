package com.inditex.coreplatform.infrastructure.mappers;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.infrastructure.persistence.entities.PriceEntity;

public interface PriceMapper {

    Price toDomain(PriceEntity entity);
    
    PriceEntity toEntity(Price domain);
}
