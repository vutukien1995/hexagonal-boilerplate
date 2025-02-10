package com.kien.hexagonal_boilerplate.domain.service;

import com.kien.hexagonal_boilerplate.application.port.in.ProductUseCase;
import com.kien.hexagonal_boilerplate.application.port.out.ProductPersistencePort;
import com.kien.hexagonal_boilerplate.domain.exception.BadRequestException;
import com.kien.hexagonal_boilerplate.domain.model.Product;
import com.kien.hexagonal_boilerplate.infrastructure.common.Response;
import com.kien.hexagonal_boilerplate.infrastructure.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductUseCase {

    private final ProductPersistencePort productPersistencePort;
    private final MessageUtil messageUtil;

    public ProductServiceImpl(ProductPersistencePort productPersistencePort, MessageUtil messageUtil) {
        this.productPersistencePort = productPersistencePort;
        this.messageUtil = messageUtil;
    }

    @Override
    public ResponseEntity<?> create(Product request) {
        List<Product> products = productPersistencePort.getByProduct(request.getProduct());
        if (!CollectionUtils.isEmpty(products))
            throw new BadRequestException("product.duplicated");

        return new ResponseEntity<>(Response.builder()
                .success(true)
                .data(productPersistencePort.save(request))
                .build(),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getByProduct(String product) {
        List<Product> products = productPersistencePort.getByProduct(product);
        if (CollectionUtils.isEmpty(products))
            return new ResponseEntity<>(messageUtil.getBadResponseByMsg("product.not.found"),
                    HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(products.getFirst());
    }

    @Override
    public ResponseEntity<?> update(Product request) {
        List<Product> products = productPersistencePort.getByProduct(request.getProduct());
        if (CollectionUtils.isEmpty(products))
            return new ResponseEntity<>(messageUtil.getBadResponseByMsg("product.not.found"),
                    HttpStatus.NOT_FOUND);

        Product product = products.getFirst();
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());

        productPersistencePort.update(product);

        return ResponseEntity.ok(messageUtil.getResponseByMsg("product.update.success"));
    }

    @Override
    public ResponseEntity<?> delete(String product) {
        List<Product> products = productPersistencePort.getByProduct(product);
        if (CollectionUtils.isEmpty(products))
            return new ResponseEntity<>(messageUtil.getBadResponseByMsg("product.not.found"),
                    HttpStatus.NOT_FOUND);

        productPersistencePort.delete(product);

        return ResponseEntity.ok(messageUtil.getResponseByMsg("product.delete.success"));
    }
}
