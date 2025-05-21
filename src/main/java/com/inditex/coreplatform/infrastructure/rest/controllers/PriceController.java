package com.inditex.coreplatform.infrastructure.rest.controllers;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.coreplatform.application.service.PriceService;
import com.inditex.coreplatform.application.usecases.GetApplicablePriceUseCase;
import com.inditex.coreplatform.application.usecases.GetPricesUseCase;
import com.inditex.coreplatform.application.usecases.queries.GetApplicablePriceQuery;
import com.inditex.coreplatform.domain.models.Price;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PriceController {

        private final PriceService priceService;

        public PriceController(PriceService priceService) {
                this.priceService = priceService;
        }

        @GetMapping(value = "/prices", produces = MediaType.APPLICATION_NDJSON_VALUE)
        public Mono<ResponseEntity<Flux<Price>>> getPrices() {  
                GetPricesUseCase getPricesUseCase = new GetPricesUseCase(priceService);

                Flux<Price> pricesFlux = getPricesUseCase.execute();

                return pricesFlux.hasElements()
                        .flatMap(hasElements -> hasElements
                                ? Mono.just(ResponseEntity.ok(pricesFlux))
                                : Mono.just(ResponseEntity.notFound().build()));
        }

        @GetMapping(value = "/applicationPrices", produces = MediaType.APPLICATION_NDJSON_VALUE)
        public Mono<ResponseEntity<Price>> getPricesByProduct(
                        @RequestParam("productId") Integer productId,
                        @RequestParam("brandId") Integer brandId,
                        @RequestParam("applicationDate") 
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {

                GetApplicablePriceUseCase getApplicablePriceUseCase = new GetApplicablePriceUseCase(priceService);
                GetApplicablePriceQuery query = new GetApplicablePriceQuery(productId, brandId, applicationDate);

                return getApplicablePriceUseCase.execute(query)
                        .map(ResponseEntity::ok)
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
        }
}