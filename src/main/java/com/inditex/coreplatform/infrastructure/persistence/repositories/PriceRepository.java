package com.inditex.coreplatform.infrastructure.persistence.repositories;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.domain.ports.IPriceRepository;
import com.inditex.coreplatform.infrastructure.mappers.PriceMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PriceRepository implements IPriceRepository {
        private final IReactivePriceRepository reactivePriceRepository;
        private final PriceMapper priceMapper;

        public PriceRepository(IReactivePriceRepository reactivePriceRepository, PriceMapper priceMapper) {
                this.reactivePriceRepository = reactivePriceRepository;
                this.priceMapper = priceMapper;
        }

        @Override
        public Mono<Price> findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        Integer productId,
                        Integer brandId,
                        LocalDateTime startDate,
                        LocalDateTime endDate) {
                return reactivePriceRepository
                                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                                                productId,
                                                brandId,
                                                startDate,
                                                endDate)
                                .map(priceMapper::toDomain);
        }

        @Override
        public Flux<Price> findAll() {
                return reactivePriceRepository
                                .findAll()
                                .map(priceMapper::toDomain);
        }
}
