package com.inditex.coreplatform.infrastructure.adapter.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.inditex.coreplatform.domain.ports.IPriceRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PriceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String BASE_URL = "/prices";

    @Autowired
    private IPriceRepository priceRepository;

    @Test
    void testBeanCreated() {
        assertNotNull(priceRepository);
    }

    @Test
    void testCase1_14June10AM() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("startDate", "2020-06-14T10:00:00")
                .build())
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void testCase2_14June16PM() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("startDate", "2020-06-14T16:00:00")
                .build())
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void testCase3_14June21PM() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("startDate", "2020-06-14T21:00:00")
                .build())
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void testCase4_15June10AM() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("startDate", "2020-06-15T10:00:00")
                .build())
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void testCase5_16June21PM() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .queryParam("startDate", "2020-06-16T21:00:00")
                .build())
            .exchange()
            .expectStatus().isNotFound();
    }

   @Test
    void testMissingProductIdReturnsBadRequest() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                // no enviamos productId
                .queryParam("brandId", 1)
                .queryParam("startDate", "2020-06-14T10:00:00")
                .build())
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void testMissingBrandIdReturnsBadRequest() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                // no enviamos brandId
                .queryParam("startDate", "2020-06-14T10:00:00")
                .build())
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void testMissingstartDateReturnsBadRequest() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BASE_URL)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                // no enviamos startDate
                .build())
            .exchange()
            .expectStatus().isBadRequest();
    }


}