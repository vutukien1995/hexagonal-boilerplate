package com.kien.hexagonal_boilerplate.application.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductRequestDto {

    private UUID id;

    @NotBlank(message = "field.not.blank")
    private String product;

    @NotNull(message = "field.not.null")
    @Min(value = 1, message = "field.min1")
    private Integer quantity;

    @NotNull(message = "field.not.null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "field.not.blank")
    @Pattern(regexp = "^(NEW|PROCESSING|COMPLETED|CANCELED)$", message = "product.invalid.status")
    private String status;

}
