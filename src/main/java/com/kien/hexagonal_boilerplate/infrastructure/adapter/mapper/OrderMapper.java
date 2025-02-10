package com.kien.hexagonal_boilerplate.infrastructure.adapter.mapper;

import com.kien.hexagonal_boilerplate.application.dto.OrderRequestDto;
import com.kien.hexagonal_boilerplate.domain.model.Order;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence.OrderEntity;

public class OrderMapper {

    public static Order toModel(OrderRequestDto orderRequestDto) {
        return Order.builder()
                .id(orderRequestDto.getId())
                .product(orderRequestDto.getProduct())
                .quantity(orderRequestDto.getQuantity())
                .price(orderRequestDto.getPrice())
                .status(orderRequestDto.getStatus())
                .build();
    }

    public static OrderEntity toEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status(order.getStatus())
                .build();
    }

}
