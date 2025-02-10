package com.kien.hexagonal_boilerplate.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot API",
                version = "3.0.0",
                description = "API Documentation for Spring Boot Hexagonal Architecture Project"
        )
)
@Configuration
public class SwaggerConfig {
}
