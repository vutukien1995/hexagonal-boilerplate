# Spring Boot Hexagonal Architecture Project

## 📌 Overview
This project follows **Hexagonal Architecture**, ensuring separation of concerns and modular design. It integrates various features such as **Exception Handling, AOP Logging, JPA with H2, Validation, Security, API Documentation, Docker, Testing, and Kafka**.

---

## 🚀 Features

### ✅  JPA + H2 Database
- Uses **Spring Data JPA** for database interactions.
- **H2 in-memory database** for quick testing.

### ✅  Validation
- Uses `@Valid` and `@NotNull`, `@Size`, etc., for request validation.

### ✅  Exception Handling (Global Exception Handler)
- Implements a centralized exception handling mechanism using `@RestControllerAdvice`.
- Returns structured error responses for better debugging.


### ✅  AOP Logging
- Logs all controller requests and responses using **Spring AOP**.
- Captures request parameters, execution time, and errors.
- Example log output:
  ```
  Calling: OrderController.create(..) with args [OrderRequestDto(id=null, product=Soap, quantity=1, price=1000, status=NEW)]
  Completed: OrderController.create(..) in 33 ms, response <201 CREATED Created,Response(success=true, code=null, message=null, data=OrderEntity(id=cdae2115-d845-4308-850d-e4431a0769d6, product=Soap, quantity=1, price=1000, status=NEW)),[]>
  ```

### ✅  Swagger (OpenAPI)
- Generates interactive API documentation.
- Access via `http://localhost:8080/swagger-ui.html`.

### ✅  Docker Support
- Includes `Dockerfile` for containerization.

### ✅  Unit Tests
- Uses **JUnit & Mockito** for testing business logic.

### ✅  Spring Security (Option on demand)
- Implements JWT-based authentication.
- Protects endpoints from unauthorized access.

### ✅  Kafka Support (Option on demand)
- Uses **Apache Kafka** for event-driven messaging.

---

## 🛠 How to Run

### 🔹 Run Locally
```sh
mvn spring-boot:run
```

### 🔹 Run with Docker
```sh
docker build -t spring-hexagonal .
docker run -p 8080:8080 spring-hexagonal
```

### 🔹 Access API Docs
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console`

---


🚀 Happy Coding! 🎯

