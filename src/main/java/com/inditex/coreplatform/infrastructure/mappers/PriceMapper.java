package com.inditex.coreplatform.infrastructure.mappers;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.infrastructure.persistence.entities.PriceEntity;
<<<<<<< HEAD
import com.inditex.coreplatform.infrastructure.rest.controllers.responses.PriceResponse;
=======
import com.inditex.coreplatform.infrastructure.rest.controllers.dtos.PriceResponse;
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))

public interface PriceMapper {

    Price toDomain(PriceEntity entity);
    
    PriceEntity toEntity(Price domain);

    PriceResponse toResponse(Price domain);
}
