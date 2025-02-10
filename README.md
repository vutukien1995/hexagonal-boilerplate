# Spring Boot Hexagonal Architecture Project

## 📌 Overview
This project follows **Hexagonal Architecture**, ensuring separation of concerns and modular design. It integrates various features such as **Exception Handling, AOP Logging, JPA with H2, Validation, Security, API Documentation, Docker, Testing, and Kafka**.

---

## 🎢 Layers

### Domain Layer
#### Role:
- Contains all critical business logic.
- Does not depend on Spring, databases, APIs, or any framework.
- Defines Entities, Value Objects, Domain Services, Domain Events.
- Centralizes business rules and constraints.

### Application Layer
#### Role:
- Contains Use Cases (Application Business Logic).
- Coordinates the execution flow but does not contain actual business rules.
- Calls the Domain Layer to process business logic.
- Uses Ports (Interfaces) to communicate with Infrastructure Layer.
- Does not depend on technologies like databases, APIs, or UI.

### Domain Layer
#### Role:
- Implements Adapters (External Communication).
- Contains Repositories, API Clients, Message Queue Handlers, Database Access.
- Does not contain business logic, only handles data interactions.
- Implements Ports (Interfaces) defined in the Application Layer.

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

### ✅  Standardized Error Handling
- Each error has a unique error code (100, 101, 102, etc.), making it easier to debug and follow.
- API clients (frontend, mobile apps, other services) can rely on error codes instead of parsing text.
- Example log output:
  ```
  product.invalid.status=100-is invalid
  product.duplicated=101-is duplicated
  product.not.found=102-Product is not found
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

