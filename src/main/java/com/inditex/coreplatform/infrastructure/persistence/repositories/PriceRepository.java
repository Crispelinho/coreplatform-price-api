package com.inditex.coreplatform.infrastructure.persistence.repositories;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        private static final Logger log = LoggerFactory.getLogger(PriceRepository.class);

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
                                .doOnSubscribe(subscription -> log.info(
                                                "📡 Buscando precio con productoId={}, brandId={}, fecha={}",
                                                productId, brandId, startDate))
                                .doOnNext(entity -> log.info("💡 Entity encontrado: {}", entity))
                                .doOnSuccess(entity -> log.info("✅ Resultado final del Mono: {}", entity))
                                .map(priceMapper::toDomain)
                                .doOnNext(domain -> log.info("🧭 Precio mapeado a dominio: {}", domain))
                                .doOnSuccess(domain -> log.info("✅ Resultado final del Mono: {}", domain));
        }

        @Override
        public Flux<Price> findAll() {
                return reactivePriceRepository
                                .findAll()
                                .doOnNext(entity -> System.out.println("Entity: " + entity))
                                .map(priceMapper::toDomain);
        }
}
