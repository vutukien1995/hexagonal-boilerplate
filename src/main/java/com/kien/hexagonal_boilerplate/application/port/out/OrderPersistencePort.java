package com.kien.hexagonal_boilerplate.application.port.out;

import com.kien.hexagonal_boilerplate.domain.model.Order;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderPersistencePort {

    OrderEntity save(Order order);
    void delete(String product);
    Optional<Order> getById(String id);
    void update(Order order);
    List<Order> getByProduct(String product);

}
