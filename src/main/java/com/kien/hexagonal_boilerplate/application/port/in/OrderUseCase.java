package com.kien.hexagonal_boilerplate.application.port.in;

import com.kien.hexagonal_boilerplate.domain.model.Order;
import org.springframework.http.ResponseEntity;

public interface OrderUseCase {

    ResponseEntity<?> create(Order request);
    ResponseEntity<?> getByProduct(String product);
    ResponseEntity<?> update(Order request);
    ResponseEntity<?> delete(String product);

}