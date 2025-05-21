package com.inditex.coreplatform.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.domain.ports.IPriceRepository;
import com.inditex.coreplatform.domain.ports.IPriceService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PriceService implements IPriceService {
    private final IPriceRepository priceRepository;

    public PriceService(IPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Mono<Price> getPriceByProductAndBrandIdAndApplicationDate(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        return priceRepository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate
            );
    }

    public Flux<Price> getAllPrices() {
        return priceRepository.findAll();
    }
}
