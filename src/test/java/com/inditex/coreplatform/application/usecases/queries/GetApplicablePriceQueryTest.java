package com.inditex.coreplatform.application.usecases.queries;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class GetApplicablePriceQueryTest {

    @Test
    void constructorAndGettersShouldWorkCorrectly() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime applicationDate = LocalDateTime.of(2024, 6, 1, 12, 0);

        GetApplicablePriceQuery query = new GetApplicablePriceQuery(productId, brandId, applicationDate);

        assertEquals(productId, query.getProductId());
        assertEquals(brandId, query.getBrandId());
        assertEquals(applicationDate, query.getApplicationDate());
    }

    @Test
    void equalsAndHashCodeShouldWorkCorrectly() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime applicationDate = LocalDateTime.of(2024, 6, 1, 12, 0);

        GetApplicablePriceQuery query1 = new GetApplicablePriceQuery(productId, brandId, applicationDate);
        GetApplicablePriceQuery query2 = new GetApplicablePriceQuery(productId, brandId, applicationDate);

        assertEquals(query1, query2);
        assertEquals(query1.hashCode(), query2.hashCode());
    }

    @Test
    void toStringShouldContainFieldValues() {
        Integer productId = 1;
        Integer brandId = 2;
        LocalDateTime applicationDate = LocalDateTime.of(2024, 6, 1, 12, 0);

        GetApplicablePriceQuery query = new GetApplicablePriceQuery(productId, brandId, applicationDate);

        String toString = query.toString();
        assertTrue(toString.contains(productId.toString()));
        assertTrue(toString.contains(brandId.toString()));
        assertTrue(toString.contains(applicationDate.toString()));
    }
}