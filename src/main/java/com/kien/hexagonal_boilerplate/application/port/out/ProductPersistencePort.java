package com.kien.hexagonal_boilerplate.application.port.out;

import com.kien.hexagonal_boilerplate.domain.model.Product;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductPersistencePort {

    ProductEntity save(Product product);
    void delete(String product);
    Optional<Product> getById(String id);
    void update(Product product);
    List<Product> getByProduct(String product);

}
