package com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence;

import com.kien.hexagonal_boilerplate.application.port.out.OrderPersistencePort;
import com.kien.hexagonal_boilerplate.domain.model.Order;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.mapper.OrderMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceAdapter implements OrderPersistencePort {

    private final OrderRepository orderRepository;

    public OrderPersistenceAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity save(Order order) {
        OrderEntity orderEntity = OrderMapper.toEntity(order);
        return orderRepository.save(orderEntity);
    }

    @Override
    public void delete(String product) {
        List<OrderEntity> orders = orderRepository.findByProduct(product);
        orderRepository.deleteAll(orders);
    }

    @Override
    public Optional<Order> getById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Order order) {
        OrderEntity orderEntity = OrderMapper.toEntity(order);
        orderRepository.save(orderEntity);
    }

    @Override
    public List<Order> getByProduct(String product) {
        return Optional.ofNullable(orderRepository.findByProduct(product))
                .orElse(List.of())
                .stream()
                .map(o -> Order.builder()
                        .id(o.getId())
                        .product(o.getProduct())
                        .quantity(o.getQuantity())
                        .price(o.getPrice())
                        .status(o.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
