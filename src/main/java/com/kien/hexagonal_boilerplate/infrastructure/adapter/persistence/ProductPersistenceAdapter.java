package com.kien.hexagonal_boilerplate.infrastructure.adapter.persistence;

import com.kien.hexagonal_boilerplate.application.port.out.ProductPersistencePort;
import com.kien.hexagonal_boilerplate.domain.model.Product;
import com.kien.hexagonal_boilerplate.infrastructure.adapter.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;

    public ProductPersistenceAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity save(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        return productRepository.save(productEntity);
    }

    @Override
    public void delete(String product) {
        List<ProductEntity> orders = productRepository.findByProduct(product);
        productRepository.deleteAll(orders);
    }

    @Override
    public Optional<Product> getById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        productRepository.save(productEntity);
    }

    @Override
    public List<Product> getByProduct(String product) {
        return Optional.ofNullable(productRepository.findByProduct(product))
                .orElse(List.of())
                .stream()
                .map(o -> Product.builder()
                        .id(o.getId())
                        .product(o.getProduct())
                        .quantity(o.getQuantity())
                        .price(o.getPrice())
                        .status(o.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
