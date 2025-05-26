package com.inditex.coreplatform.infrastructure.controllers;

<<<<<<< HEAD
import org.junit.jupiter.api.Test;
=======
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

<<<<<<< HEAD
=======
import com.inditex.coreplatform.application.service.PriceService;
import com.inditex.coreplatform.application.usecases.GetPricesUseCase;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PriceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String BASE_URL = "/applicationPrices";

    @Test
    void testCase1_14June10AM() {
        webTestClient.get()
<<<<<<< HEAD
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .build())
            .exchange()
            .expectStatus().isOk();
=======
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .build())
                .exchange()
                .expectStatus().isOk();
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
    }

    @Test
    void testCase2_14June16PM() {
        webTestClient.get()
<<<<<<< HEAD
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("applicationDate", "2020-06-14T16:00:00")
                .build())
            .exchange()
            .expectStatus().isOk();
=======
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .queryParam("applicationDate", "2020-06-14T16:00:00")
                        .build())
                .exchange()
                .expectStatus().isOk();
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
    }

    @Test
    void testCase3_14June21PM() {
        webTestClient.get()
<<<<<<< HEAD
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("applicationDate", "2020-06-14T21:00:00")
                .build())
            .exchange()
            .expectStatus().isOk();
=======
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .queryParam("applicationDate", "2020-06-14T21:00:00")
                        .build())
                .exchange()
                .expectStatus().isOk();
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
    }

    @Test
    void testCase4_15June10AM() {
        webTestClient.get()
<<<<<<< HEAD
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("applicationDate", "2020-06-15T10:00:00")
                .build())
            .exchange()
            .expectStatus().isOk();
=======
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .queryParam("applicationDate", "2020-06-15T10:00:00")
                        .build())
                .exchange()
                .expectStatus().isOk();
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
    }

    @Test
    void testCase5_16June21PM() {
        webTestClient.get()
<<<<<<< HEAD
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("applicationDate", "2020-06-16T21:00:00")
                .build())
            .exchange()
            .expectStatus().isOk();
    }

   @Test
    void testMissingProductIdReturnsBadRequest() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                // no enviamos productId
                .queryParam("brandId", 1)
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .build())
            .exchange()
            .expectStatus().isBadRequest();
=======
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .queryParam("applicationDate", "2020-06-16T21:00:00")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testMissingProductIdReturnsBadRequest() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        // no enviamos productId
                        .queryParam("brandId", 1)
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .build())
                .exchange()
                .expectStatus().isBadRequest();
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
    }

    @Test
    void testMissingBrandIdReturnsBadRequest() {
        webTestClient.get()
<<<<<<< HEAD
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                // no enviamos brandId
                .queryParam("applicationDate", "2020-06-14T10:00:00")
                .build())
            .exchange()
            .expectStatus().isBadRequest();
=======
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 35455)
                        // no enviamos brandId
                        .queryParam("applicationDate", "2020-06-14T10:00:00")
                        .build())
                .exchange()
                .expectStatus().isBadRequest();
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
    }

    @Test
    void testMissingstartDateReturnsBadRequest() {
        webTestClient.get()
<<<<<<< HEAD
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                // no enviamos applicationtDate
                .build())
            .exchange()
            .expectStatus().isBadRequest();
    }


=======
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        // no enviamos applicationtDate
                        .build())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGetPricesReturnsOkOrNotFound() {
        webTestClient.get()
                .uri("/prices")
                .exchange()
                .expectStatus().value(status ->
                // Accept either 200 OK or 404 Not Found depending on data
                org.assertj.core.api.Assertions.assertThat(status == 200 || status == 404).isTrue());
    }

    @Test
    void testGetPricesReturnsJsonContentType() {
        webTestClient.get()
                .uri("/prices")
                .exchange()
                .expectHeader().contentTypeCompatibleWith("application/json");
    }

    @Test
    void executeReturnsEmptyFlux() {
        PriceService priceService = Mockito.mock(PriceService.class);
        when(priceService.getAllPrices()).thenReturn(Flux.empty());

        GetPricesUseCase useCase = new GetPricesUseCase(priceService);

        StepVerifier.create(useCase.execute())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void testApplicationPricesReturnsNotFoundForNonExisting() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BASE_URL)
                        .queryParam("productId", 999999)
                        .queryParam("brandId", 999)
                        .queryParam("applicationDate", "2030-01-01T00:00:00")
                        .build())
                .exchange()
                .expectStatus().isNotFound();
    }
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
}