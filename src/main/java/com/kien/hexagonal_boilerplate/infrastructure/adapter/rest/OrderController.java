package com.kien.hexagonal_boilerplate.infrastructure.adapter.rest;

import com.kien.hexagonal_boilerplate.application.dto.OrderRequestDto;
import com.kien.hexagonal_boilerplate.application.dto.OrderResponseDto;
import com.kien.hexagonal_boilerplate.application.port.in.OrderUseCase;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.mapper.OrderMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid OrderRequestDto requestDto) {
        return orderUseCase.create(OrderMapper.toModel(requestDto));
    }

    @GetMapping("/{product}")
    public ResponseEntity<?> get(@PathVariable String product) {
        return orderUseCase.getByProduct(product);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody @Valid OrderRequestDto requestDto) {
        return orderUseCase.update(OrderMapper.toModel(requestDto));
    }

    @DeleteMapping("/{product}")
    public ResponseEntity<?> delete(@PathVariable String product) {
        return orderUseCase.delete(product);
    }


}
