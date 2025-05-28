package com.inditex.coreplatform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class PriceServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	private static final String BASE_URL = "/api/prices";
	private static final Integer PRODUCT_ID = 35455;
	private static final Integer BRAND_ID = 1;

	@Test
	void contextLoads() {
		PriceServiceApplication.main(new String[] {});
	}

	@Test
	void testCase1_14June10AM() {
		testApplicablePrice(PRODUCT_ID, BRAND_ID, "2020-06-14T10:00:00", 200);
	}

	@Test
	void testCase2_14June16PM() {
		testApplicablePrice(PRODUCT_ID, BRAND_ID, "2020-06-14T16:00:00", 200);
	}

	@Test
	void testCase3_14June21PM() {
		testApplicablePrice(PRODUCT_ID, BRAND_ID, "2020-06-14T21:00:00", 200);
	}

	@Test
	void testCase4_15June10AM() {
		testApplicablePrice(PRODUCT_ID, BRAND_ID, "2020-06-15T10:00:00", 200);
	}

	@Test
	void testCase5_16June21PM() {
		testApplicablePrice(PRODUCT_ID, BRAND_ID, "2020-06-16T21:00:00", 200);
	}

	@Test
	void testMissingProductIdReturnsBadRequest() {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path(BASE_URL + "/applicable")
						.queryParam("brandId", BRAND_ID)
						.queryParam("applicationDate", "2020-06-14T10:00:00")
						.build())
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void testMissingBrandIdReturnsBadRequest() {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path(BASE_URL + "/applicable")
						.queryParam("productId", PRODUCT_ID)
						.queryParam("applicationDate", "2020-06-14T10:00:00")
						.build())
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void testMissingApplicationDateReturnsBadRequest() {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path(BASE_URL + "/applicable")
						.queryParam("productId", PRODUCT_ID)
						.queryParam("brandId", BRAND_ID)
						.build())
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void testNotFoundForNonMatchingData() {
		testApplicablePrice(99999, 999, "2030-01-01T00:00:00", 404);
	}

	@Test
	void testInvalidProductIdTypeReturnsBadRequest() {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path(BASE_URL + "/applicable")
						.queryParam("productId", "abc")
						.queryParam("brandId", 1)
						.queryParam("applicationDate", "2020-06-14T10:00:00")
						.build())
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(String.class)
				.value(body -> assertThat(body).contains("Parámetro 'productId' inválido"));
	}

	@Test
	void testInvalidBrandIdTypeReturnsBadRequest() {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path(BASE_URL + "/applicable")
						.queryParam("productId", 35455)
						.queryParam("brandId", "xyz") // tipo inválido
						.queryParam("applicationDate", "2020-06-14T10:00:00")
						.build())
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(String.class)
				.value(body -> assertThat(body).contains("Parámetro 'brandId' inválido"));
	}

	@Test
	void testNegativeProductIdReturnsBadRequest() {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path(BASE_URL + "/applicable")
						.queryParam("productId", -1)
						.queryParam("brandId", 1)
						.queryParam("applicationDate", "2020-06-14T10:00:00")
						.build())
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(String.class)
				.value(body -> assertThat(body).contains("'getApplicablePrice.productId' debe ser mayor que 0"));
	}

	@Test
	void testGetAllPricesReturns200() {
		webTestClient.get()
				.uri(BASE_URL)
				.exchange()
				.expectHeader().contentTypeCompatibleWith("application/json")
				.expectStatus().isOk();
	}

	private void testApplicablePrice(Integer productId, Integer brandId, String applicationDate, int expectedStatus) {
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path(BASE_URL + "/applicable")
						.queryParam("productId", productId)
						.queryParam("brandId", brandId)
						.queryParam("applicationDate", applicationDate)
						.build())
				.exchange()
				.expectStatus().isEqualTo(expectedStatus);
	}
}
