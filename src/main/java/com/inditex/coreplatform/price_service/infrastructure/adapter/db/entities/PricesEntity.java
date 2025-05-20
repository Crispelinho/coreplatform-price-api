package com.inditex.coreplatform.price_service.infrastructure.adapter.db.entities;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("prices")
public class PricesEntity {
    @Column("brand_id")
    private Integer brandId;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("price_list")
    private Integer priceList;

    @Column("product_id")
    private Long productId;

    @Column("priority")
    private Integer priority;

    @Column("price")
    private Double price;

    @Column("curr")
    private String curr;
}
