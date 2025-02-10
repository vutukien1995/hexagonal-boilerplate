package com.kien.hexagonal_boilerplate.infrastructure.adapter.rest;

import com.kien.hexagonal_boilerplate.application.dto.ProductRequestDto;
import com.kien.hexagonal_boilerplate.application.port.in.ProductUseCase;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequestDto requestDto) {
        return productUseCase.create(ProductMapper.toModel(requestDto));
    }

    @GetMapping("/{product}")
    public ResponseEntity<?> get(@PathVariable String product) {
        return productUseCase.getByProduct(product);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody @Valid ProductRequestDto requestDto) {
        return productUseCase.update(ProductMapper.toModel(requestDto));
    }

    @DeleteMapping("/{product}")
    public ResponseEntity<?> delete(@PathVariable String product) {
        return productUseCase.delete(product);
    }

}
