package com.inditex.coreplatform.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.infrastructure.persistence.entities.PriceEntity;

@Component
public class PriceMapperImpl implements PriceMapper {

    @Override
    public Price toDomain(PriceEntity entity) {
        if (entity == null) {
            return null;
        }

        return Price.builder()
                .brandId(entity.getBrandId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .productId(entity.getProductId())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .curr(entity.getCurr())
                .build();
    }

    @Override
    public PriceEntity toEntity(Price domain) {
        if (domain == null) {
            return null;
        }

        PriceEntity entity = new PriceEntity();
        entity.setBrandId(domain.getBrandId());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setPriceList(domain.getPriceList());
        entity.setProductId(domain.getProductId());
        entity.setPriority(domain.getPriority());
        entity.setPrice(domain.getPrice());
        entity.setCurr(domain.getCurr());
        return entity;
    }
}
