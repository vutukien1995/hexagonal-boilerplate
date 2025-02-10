package com.kien.hexagonal_boilerplate.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kien.hexagonal_boilerplate.application.port.out.OrderPersistencePort;
import com.kien.hexagonal_boilerplate.domain.exception.BadRequestException;
import com.kien.hexagonal_boilerplate.domain.model.Order;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence.OrderEntity;
import com.kien.hexagonal_boilerplate.infrastructure.common.BadResponse;
import com.kien.hexagonal_boilerplate.infrastructure.common.Response;
import com.kien.hexagonal_boilerplate.infrastructure.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderPersistencePort orderPersistencePort;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order sampleOrder;
    private OrderEntity sampleOrderEntity;

    @BeforeEach
    void setUp() {
        sampleOrder = new Order(UUID.randomUUID(), "Product A", 10, BigDecimal.valueOf(100.0), "NEW");
        sampleOrderEntity = new OrderEntity(UUID.randomUUID(), "Product A", 10, BigDecimal.valueOf(100.0), "NEW");
    }

    @Test
    void createOrder_Success() {
        when(orderPersistencePort.getByProduct(anyString())).thenReturn(List.of());
        when(orderPersistencePort.save(any(Order.class))).thenReturn(sampleOrderEntity);

        ResponseEntity<?> response = orderService.create(sampleOrder);

        assertEquals(201, response.getStatusCode().value());
        verify(orderPersistencePort).save(sampleOrder);
    }

    @Test
    void createOrder_ThrowsException_WhenProductExists() {
        when(orderPersistencePort.getByProduct(anyString())).thenReturn(List.of(sampleOrder));

        assertThrows(BadRequestException.class, () -> orderService.create(sampleOrder));
    }

    @Test
    void getByProduct_Success() {
        when(orderPersistencePort.getByProduct("Product A")).thenReturn(List.of(sampleOrder));

        ResponseEntity<?> response = orderService.getByProduct("Product A");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(sampleOrder, response.getBody());
    }

    @Test
    void getByProduct_NotFound() {
        when(orderPersistencePort.getByProduct("Unknown"))
                .thenReturn(List.of());
        when(messageUtil.getBadResponseByMsg("order.product.not.found"))
                .thenReturn(BadResponse.builder().success(false).message("Order is not found").build());

        ResponseEntity<?> response = orderService.getByProduct("Unknown");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void updateOrder_Success() {
        when(orderPersistencePort.getByProduct("Product A")).thenReturn(List.of(sampleOrder));
        when(messageUtil.getResponseByMsg("order.product.update.success"))
                .thenReturn(Response.builder().success(true).message("Update success").build());

        ResponseEntity<?> response = orderService.update(sampleOrder);

        assertEquals(200, response.getStatusCode().value());
        verify(orderPersistencePort).update(sampleOrder);
    }

    @Test
    void deleteOrder_Success() {
        when(orderPersistencePort.getByProduct("Product A")).thenReturn(List.of(sampleOrder));
        when(messageUtil.getResponseByMsg("order.product.delete.success"))
                .thenReturn(Response.builder().success(true).message("Delete success").build());

        ResponseEntity<?> response = orderService.delete("Product A");

        assertEquals(200, response.getStatusCode().value());
        verify(orderPersistencePort).delete("Product A");
    }

    @Test
    void deleteOrder_NotFound() {
        when(orderPersistencePort.getByProduct("Unknown")).thenReturn(List.of());
        when(messageUtil.getBadResponseByMsg("order.product.not.found"))
                .thenReturn(BadResponse.builder().success(false).message("Not found").build());

        ResponseEntity<?> response = orderService.delete("Unknown");

        assertEquals(404, response.getStatusCodeValue());
    }

}
