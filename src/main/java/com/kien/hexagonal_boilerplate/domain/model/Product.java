package com.kien.hexagonal_boilerplate.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private UUID id;
    private String product;
    private Integer quantity;
    private BigDecimal price;
    private String status;

}
