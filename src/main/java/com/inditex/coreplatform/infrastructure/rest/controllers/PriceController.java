package com.inditex.coreplatform.infrastructure.rest.controllers;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.coreplatform.application.service.PriceService;
import com.inditex.coreplatform.application.usecases.GetApplicablePriceUseCase;
import com.inditex.coreplatform.application.usecases.GetPricesUseCase;
import com.inditex.coreplatform.application.usecases.queries.GetApplicablePriceQuery;
<<<<<<< HEAD
import com.inditex.coreplatform.domain.models.Price;
import com.inditex.coreplatform.infrastructure.mappers.PriceMapper;
import com.inditex.coreplatform.infrastructure.rest.controllers.responses.PriceResponse;
=======
import com.inditex.coreplatform.infrastructure.mappers.PriceMapper;
import com.inditex.coreplatform.infrastructure.rest.controllers.dtos.PriceResponse;
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))

import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class PriceController {

        private final PriceService priceService;
        private final PriceMapper priceMapper;

        public PriceController(PriceService priceService, PriceMapper priceMapper) {
                this.priceService = priceService;
                this.priceMapper = priceMapper;
        }

<<<<<<< HEAD
        @GetMapping(value = "/prices", produces = MediaType.APPLICATION_NDJSON_VALUE)
        public Mono<ResponseEntity<Flux<Price>>> getPrices() {
                GetPricesUseCase getPricesUseCase = new GetPricesUseCase(priceService);

                Flux<Price> prices = getPricesUseCase.execute();

                return prices.hasElements()
                                .flatMap(hasElements -> hasElements
                                                ? Mono.just(ResponseEntity.ok(prices))
                                                : Mono.just(ResponseEntity.notFound().build()));
        }

        @GetMapping(value = "/applicationPrices", produces = MediaType.APPLICATION_NDJSON_VALUE)
=======
        @GetMapping(value = "/prices", produces = MediaType.APPLICATION_JSON_VALUE)
        public Mono<ResponseEntity<Flux<PriceResponse>>> getPrices() {
                GetPricesUseCase getPricesUseCase = new GetPricesUseCase(priceService);
                Flux<PriceResponse> pricesResponse = getPricesUseCase.execute().map(priceMapper::toResponse);

                return pricesResponse.hasElements()
                                .flatMap(hasElements -> hasElements
                                                ? Mono.just(ResponseEntity.ok(pricesResponse))
                                                : Mono.just(ResponseEntity.notFound().build()));
        }

        @GetMapping(value = "/applicationPrices", produces = MediaType.APPLICATION_JSON_VALUE)
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
        public Mono<ResponseEntity<PriceResponse>> getPricesByProduct(
                        @RequestParam("productId") @NotNull Integer productId,
                        @RequestParam("brandId") @NotNull Integer brandId,
                        @RequestParam("applicationDate") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull LocalDateTime applicationDate) {

                GetApplicablePriceUseCase getApplicablePriceUseCase = new GetApplicablePriceUseCase(priceService);
                GetApplicablePriceQuery query = new GetApplicablePriceQuery(productId, brandId, applicationDate);

                return getApplicablePriceUseCase.execute(query)
<<<<<<< HEAD
                                .map(priceMapper::toResponse).map(ResponseEntity::ok)
=======
                                .map(priceMapper::toResponse)
                                .map(ResponseEntity::ok)
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
                                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
        }
}