package com.inditex.coreplatform.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Inditex
 * @version 1.0
 * @since 2023-10-01
 *
 * Esta clase representa un precio en el sistema.
 * Contiene información sobre la marca, fechas de inicio y fin, lista de precios,
 * producto, prioridad, precio y moneda.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Integer productId;
    private Integer priority;
    private Double price;
    private String curr;

}