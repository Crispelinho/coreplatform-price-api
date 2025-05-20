package com.inditex.coreplatform.price_service.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Inditex
 * @version 1.0
 * @since 2023-10-01
 *
 * Esta clase representa un precio en el sistema.
 * Contiene información sobre la marca, fechas de inicio y fin, lista de precios,
 * producto, prioridad, precio y moneda.
 */


/**
 * Clase que representa un precio en el sistema.
 * Contiene información sobre la marca, fechas de inicio y fin, lista de precios,
 * producto, prioridad, precio y moneda.
 */

@Data
@Builder
@AllArgsConstructor
public class Price {

    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;

    public boolean isApplicable(LocalDateTime applicationDate) {
        return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
    }

}