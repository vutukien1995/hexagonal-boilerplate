package com.kien.hexagonal_boilerplate.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kien.hexagonal_boilerplate.application.port.out.ProductPersistencePort;
import com.kien.hexagonal_boilerplate.domain.exception.BadRequestException;
import com.kien.hexagonal_boilerplate.domain.model.Product;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence.ProductEntity;
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
public class ProductServiceImplTest {

    @Mock
    private ProductPersistencePort productPersistencePort;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private ProductServiceImpl orderService;

    private Product sampleProduct;
    private ProductEntity sampleProductEntity;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product(UUID.randomUUID(), "Product A", 10, BigDecimal.valueOf(100.0), "NEW");
        sampleProductEntity = new ProductEntity(UUID.randomUUID(), "Product A", 10, BigDecimal.valueOf(100.0), "NEW");
    }

    @Test
    void createOrder_Success() {
        when(productPersistencePort.getByProduct(anyString())).thenReturn(List.of());
        when(productPersistencePort.save(any(Product.class))).thenReturn(sampleProductEntity);

        ResponseEntity<?> response = orderService.create(sampleProduct);

        assertEquals(201, response.getStatusCode().value());
        verify(productPersistencePort).save(sampleProduct);
    }

    @Test
    void createOrder_ThrowsException_WhenProductExists() {
        when(productPersistencePort.getByProduct(anyString())).thenReturn(List.of(sampleProduct));

        assertThrows(BadRequestException.class, () -> orderService.create(sampleProduct));
    }

    @Test
    void getByProduct_Success() {
        when(productPersistencePort.getByProduct("Product A")).thenReturn(List.of(sampleProduct));

        ResponseEntity<?> response = orderService.getByProduct("Product A");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(sampleProduct, response.getBody());
    }

    @Test
    void getByProduct_NotFound() {
        when(productPersistencePort.getByProduct("Unknown"))
                .thenReturn(List.of());
        when(messageUtil.getBadResponseByMsg("order.product.not.found"))
                .thenReturn(BadResponse.builder().success(false).message("Product is not found").build());

        ResponseEntity<?> response = orderService.getByProduct("Unknown");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void updateOrder_Success() {
        when(productPersistencePort.getByProduct("Product A")).thenReturn(List.of(sampleProduct));
        when(messageUtil.getResponseByMsg("order.product.update.success"))
                .thenReturn(Response.builder().success(true).message("Update success").build());

        ResponseEntity<?> response = orderService.update(sampleProduct);

        assertEquals(200, response.getStatusCode().value());
        verify(productPersistencePort).update(sampleProduct);
    }

    @Test
    void deleteOrder_Success() {
        when(productPersistencePort.getByProduct("Product A")).thenReturn(List.of(sampleProduct));
        when(messageUtil.getResponseByMsg("order.product.delete.success"))
                .thenReturn(Response.builder().success(true).message("Delete success").build());

        ResponseEntity<?> response = orderService.delete("Product A");

        assertEquals(200, response.getStatusCode().value());
        verify(productPersistencePort).delete("Product A");
    }

    @Test
    void deleteOrder_NotFound() {
        when(productPersistencePort.getByProduct("Unknown")).thenReturn(List.of());
        when(messageUtil.getBadResponseByMsg("order.product.not.found"))
                .thenReturn(BadResponse.builder().success(false).message("Not found").build());

        ResponseEntity<?> response = orderService.delete("Unknown");

        assertEquals(404, response.getStatusCodeValue());
    }

}
