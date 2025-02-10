package com.kien.hexagonal_boilerplate.application.port.in;

import com.kien.hexagonal_boilerplate.domain.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductUseCase {

    ResponseEntity<?> create(Product request);
    ResponseEntity<?> getByProduct(String product);
    ResponseEntity<?> update(Product request);
    ResponseEntity<?> delete(String product);

}