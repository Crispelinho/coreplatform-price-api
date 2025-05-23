package com.inditex.coreplatform.application.usecases;

import com.inditex.coreplatform.application.usecases.queries.GetApplicablePriceQuery;
import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.domain.ports.IPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

class GetApplicablePriceUseCaseTest {

    private IPriceService priceService;
    private GetApplicablePriceUseCase useCase;

    @BeforeEach
    void setUp() {
        priceService = Mockito.mock(IPriceService.class);
        useCase = new GetApplicablePriceUseCase(priceService);
    }

    @Test
    void execute_shouldReturnPrice_whenServiceReturnsPrice() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime applicationDate = LocalDateTime.now();
        Price price = new Price(); // Assuming default constructor

        GetApplicablePriceQuery query = mock(GetApplicablePriceQuery.class);
        when(query.getProductId()).thenReturn(productId);
        when(query.getBrandId()).thenReturn(brandId);
        when(query.getApplicationDate()).thenReturn(applicationDate);

        when(priceService.getPriceByProductAndBrandIdAndApplicationDate(productId, brandId, applicationDate))
                .thenReturn(Mono.just(price));

        Mono<Price> result = useCase.execute(query);

        StepVerifier.create(result)
                .expectNext(price)
                .verifyComplete();

        verify(priceService, times(1))
                .getPriceByProductAndBrandIdAndApplicationDate(productId, brandId, applicationDate);
    }

    @Test
    void execute_shouldReturnEmpty_whenServiceReturnsEmpty() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime applicationDate = LocalDateTime.now();

        GetApplicablePriceQuery query = mock(GetApplicablePriceQuery.class);
        when(query.getProductId()).thenReturn(productId);
        when(query.getBrandId()).thenReturn(brandId);
        when(query.getApplicationDate()).thenReturn(applicationDate);

        when(priceService.getPriceByProductAndBrandIdAndApplicationDate(productId, brandId, applicationDate))
                .thenReturn(Mono.empty());

        Mono<Price> result = useCase.execute(query);

        StepVerifier.create(result)
                .verifyComplete();

        verify(priceService, times(1))
                .getPriceByProductAndBrandIdAndApplicationDate(productId, brandId, applicationDate);
    }
}