package com.kien.hexagonal_boilerplate.infrastructure.adapter.mapper;

import com.kien.hexagonal_boilerplate.application.dto.ProductRequestDto;
import com.kien.hexagonal_boilerplate.domain.model.Product;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence.ProductEntity;

public class ProductMapper {

    public static Product toModel(ProductRequestDto productRequestDto) {
        return Product.builder()
                .id(productRequestDto.getId())
                .product(productRequestDto.getProduct())
                .quantity(productRequestDto.getQuantity())
                .price(productRequestDto.getPrice())
                .status(productRequestDto.getStatus())
                .build();
    }

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .product(product.getProduct())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }

}
