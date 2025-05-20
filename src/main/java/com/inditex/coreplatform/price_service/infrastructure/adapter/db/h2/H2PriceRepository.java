package com.inditex.coreplatform.price_service.infrastructure.adapter.db.h2;

import java.time.LocalDateTime;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.inditex.coreplatform.price_service.infrastructure.adapter.db.entities.PricesEntity;

import reactor.core.publisher.Flux;


public interface H2PriceRepository extends  ReactiveCrudRepository<PricesEntity, Long> {

    Flux<PricesEntity> findByProductIdAndBrandIdAndStartDate(Long productId, Integer brandId, LocalDateTime startDate);
}   
