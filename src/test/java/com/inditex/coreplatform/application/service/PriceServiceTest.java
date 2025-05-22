package com.inditex.coreplatform.application.service;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.domain.ports.IPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.*;

class PriceServiceTest {

    private IPriceRepository priceRepository;
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        priceRepository = Mockito.mock(IPriceRepository.class);
        priceService = new PriceService(priceRepository);
    }

    @Test
    void testGetPriceByProductAndBrandIdAndApplicationDate_ReturnsPrice() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime applicationDate = LocalDateTime.now();
        Price price = new Price();

        Mockito.when(priceRepository
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        eq(productId), eq(brandId), eq(applicationDate), eq(applicationDate)))
                .thenReturn(Mono.just(price));

        Mono<Price> result = priceService.getPriceByProductAndBrandIdAndApplicationDate(productId, brandId, applicationDate);

        StepVerifier.create(result)
                .expectNext(price)
                .verifyComplete();
    }

    @Test
    void testGetPriceByProductAndBrandIdAndApplicationDate_ReturnsEmpty() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime applicationDate = LocalDateTime.now();

        Mockito.when(priceRepository
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        eq(productId), eq(brandId), eq(applicationDate), eq(applicationDate)))
                .thenReturn(Mono.empty());

        Mono<Price> result = priceService.getPriceByProductAndBrandIdAndApplicationDate(productId, brandId, applicationDate);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testGetAllPrices_ReturnsPrices() {
        Price price1 = new Price();
        Price price2 = new Price();

        Mockito.when(priceRepository.findAll()).thenReturn(Flux.just(price1, price2));

        Flux<Price> result = priceService.getAllPrices();

        StepVerifier.create(result)
                .expectNext(price1)
                .expectNext(price2)
                .verifyComplete();
    }

    @Test
    void testGetAllPrices_ReturnsEmpty() {
        Mockito.when(priceRepository.findAll()).thenReturn(Flux.empty());

        Flux<Price> result = priceService.getAllPrices();

        StepVerifier.create(result)
                .verifyComplete();
    }
}