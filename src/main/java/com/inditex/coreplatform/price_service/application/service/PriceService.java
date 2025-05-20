package com.inditex.coreplatform.price_service.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inditex.coreplatform.price_service.infrastructure.adapter.db.entities.PricesEntity;
import com.inditex.coreplatform.price_service.infrastructure.adapter.db.h2.H2PriceRepository;

import reactor.core.publisher.Flux;

@Service
public class PriceService {
    private final H2PriceRepository priceRepository;

        public PriceService(H2PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Flux<PricesEntity> getPrice(Long productId, Integer brandId, LocalDateTime applicationDate) {
        return priceRepository.findByProductIdAndBrandIdAndStartDate(productId, brandId, applicationDate);
    }
}
