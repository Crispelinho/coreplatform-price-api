package com.inditex.coreplatform.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inditex.coreplatform.application.exceptions.MissingPriceApplicationRequestParamException;
import com.inditex.coreplatform.application.exceptions.PriceNotFoundException;
import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.domain.ports.IPriceRepository;
import com.inditex.coreplatform.domain.ports.IPriceService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive service for handling price-related operations.
 * This service interacts with the price repository to fetch prices based on
 * product ID, brand ID, and application date.
 */

@Service
public class ReactivePriceService implements IPriceService {
    private final IPriceRepository priceRepository;

    public ReactivePriceService(IPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Mono<Price> getPriceByProductAndBrandIdAndApplicationDate(Integer productId, Integer brandId,
            LocalDateTime applicationDate) {
        if (productId == null) {
            return Mono.error(new MissingPriceApplicationRequestParamException("productId"));
        }
        if (brandId == null) {
            return Mono.error(new MissingPriceApplicationRequestParamException("brandId"));
        }
        if (applicationDate == null) {
            return Mono.error(new MissingPriceApplicationRequestParamException("applicationDate"));
        }

        return priceRepository
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, applicationDate, applicationDate)
                .filter(price -> price.isApplicableAt(applicationDate))
                .switchIfEmpty(Mono.error(new PriceNotFoundException(productId, brandId)));
    }

    public Flux<Price> getAllPrices() {
        return priceRepository.findAll();
    }
}
