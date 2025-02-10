package com.kien.hexagonal_boilerplate.domain.service;

import com.kien.hexagonal_boilerplate.application.port.in.OrderUseCase;
import com.kien.hexagonal_boilerplate.application.port.out.OrderPersistencePort;
import com.kien.hexagonal_boilerplate.domain.exception.BadRequestException;
import com.kien.hexagonal_boilerplate.domain.model.Order;
import com.kien.hexagonal_boilerplate.infrastructure.common.Response;
import com.kien.hexagonal_boilerplate.infrastructure.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderUseCase {

    private final OrderPersistencePort orderPersistencePort;
    private final MessageUtil messageUtil;

    public OrderServiceImpl(OrderPersistencePort orderPersistencePort, MessageUtil messageUtil) {
        this.orderPersistencePort = orderPersistencePort;
        this.messageUtil = messageUtil;
    }

    @Override
    public ResponseEntity<?> create(Order request) {
        List<Order> orders = orderPersistencePort.getByProduct(request.getProduct());
        if (!CollectionUtils.isEmpty(orders))
            throw new BadRequestException("order.product.duplicated");

        return new ResponseEntity<>(Response.builder()
                .success(true)
                .data(orderPersistencePort.save(request))
                .build(),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getByProduct(String product) {
        List<Order> orders = orderPersistencePort.getByProduct(product);
        if (CollectionUtils.isEmpty(orders))
            return new ResponseEntity<>(messageUtil.getBadResponseByMsg("order.product.not.found"),
                    HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(orders.getFirst());
    }

    @Override
    public ResponseEntity<?> update(Order request) {
        List<Order> orders = orderPersistencePort.getByProduct(request.getProduct());
        if (CollectionUtils.isEmpty(orders))
            return new ResponseEntity<>(messageUtil.getBadResponseByMsg("order.product.not.found"),
                    HttpStatus.NOT_FOUND);

        Order order = orders.getFirst();
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setStatus(request.getStatus());

        orderPersistencePort.update(order);

        return ResponseEntity.ok(messageUtil.getResponseByMsg("order.product.update.success"));
    }

    @Override
    public ResponseEntity<?> delete(String product) {
        List<Order> orders = orderPersistencePort.getByProduct(product);
        if (CollectionUtils.isEmpty(orders))
            return new ResponseEntity<>(messageUtil.getBadResponseByMsg("order.product.not.found"),
                    HttpStatus.NOT_FOUND);

        orderPersistencePort.delete(product);

        return ResponseEntity.ok(messageUtil.getResponseByMsg("order.product.delete.success"));
    }
}
