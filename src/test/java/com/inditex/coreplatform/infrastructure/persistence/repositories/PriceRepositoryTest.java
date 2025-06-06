package com.inditex.coreplatform.infrastructure.persistence.repositories;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.infrastructure.mappers.PriceMapper;
import com.inditex.coreplatform.infrastructure.persistence.entities.PriceEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

class PriceRepositoryTest {

    private IReactivePriceRepository reactivePriceRepository;
    private PriceMapper priceMapper;
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        reactivePriceRepository = mock(IReactivePriceRepository.class);
        priceMapper = mock(PriceMapper.class);
        priceRepository = new PriceRepository(reactivePriceRepository, priceMapper);
    }

    @Test
    void testFindTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        PriceEntity entity = new PriceEntity();
        Price domainPrice = new Price();

        when(reactivePriceRepository
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, startDate, endDate))
                .thenReturn(Mono.just(entity));
        when(priceMapper.toDomain(entity)).thenReturn(domainPrice);

        Mono<Price> result = priceRepository
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, startDate, endDate);

        StepVerifier.create(result)
                .expectNext(domainPrice)
                .verifyComplete();

        verify(reactivePriceRepository).findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, startDate, endDate);
        verify(priceMapper).toDomain(entity);
    }

    @Test
    void testFindAll() {
        PriceEntity entity1 = new PriceEntity();
        entity1.setProductId(1); // Diferenciar entidades
        PriceEntity entity2 = new PriceEntity();
        entity2.setProductId(2);
        Price domainPrice1 = new Price();
        Price domainPrice2 = new Price();

        when(reactivePriceRepository.findAll()).thenReturn(Flux.just(entity1, entity2));
        when(priceMapper.toDomain(entity1)).thenReturn(domainPrice1);
        when(priceMapper.toDomain(entity2)).thenReturn(domainPrice2);

        Flux<Price> result = priceRepository.findAll();

        StepVerifier.create(result)
                .expectNext(domainPrice1)
                .expectNext(domainPrice2)
                .verifyComplete();

        verify(reactivePriceRepository).findAll();
        verify(priceMapper, times(1)).toDomain(entity1);
        verify(priceMapper, times(1)).toDomain(entity2);
    }
}