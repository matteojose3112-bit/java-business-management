# Java Business Management

> A lightweight inventory management REST API for small retail and distribution workflows.

**Status:** MVP implemented; CI verification in progress  
**Primary language:** Java 21  
**Framework:** Spring Boot 3.5, Spring Data JPA  
**Build:** Maven  
**Database:** H2 in-memory (development/demo)  
**Live demo:** Not deployed

## Overview

This project models a practical inventory workflow: create products, record stock entering or leaving inventory, inspect movement history, and identify products at or below their reorder threshold.

The API enforces basic business rules, including unique SKUs, positive movement quantities, and prevention of negative stock balances.

## Implemented capabilities

- Product creation with SKU, name, opening quantity, and reorder level.
- Product listing with current stock and low-stock status.
- Low-stock report.
- Stock-in and stock-out movements.
- Per-product stock movement history.
- Input validation and clear HTTP error responses.
- Automated Spring Boot integration tests.
- Dockerfile, Render Blueprint, and GitHub Actions CI configuration.

## API

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/health` | Health check |
| GET | `/api/products` | List products |
| POST | `/api/products` | Create a product |
| GET | `/api/products/low-stock` | List products at/below reorder level |
| POST | `/api/products/{id}/stock-movements` | Record stock IN or OUT |
| GET | `/api/products/{id}/stock-movements` | View product movement history |

### Create a product

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"sku":"SKU-1001","name":"Widget","openingQuantity":25,"reorderLevel":5}'
```

### Record a stock-out

```bash
curl -X POST http://localhost:8080/api/products/1/stock-movements \
  -H "Content-Type: application/json" \
  -d '{"type":"OUT","quantity":3,"note":"Order fulfilment"}'
```

Use `IN` to add stock. A stock-out that would make inventory negative is rejected with HTTP 409.

## Run locally

Requirements: Java 21 and Maven 3.9+.

```bash
mvn spring-boot:run
```

Run the test suite and package the application:

```bash
mvn verify
```

The service listens on port 8080 by default. The `PORT` environment variable controls the HTTP port.

## Technology and structure

- Java 21
- Spring Boot Web, Validation, and Data JPA
- H2 database
- Maven
- JUnit 5 and Spring MockMvc
- Docker and Render Blueprint configuration

```text
src/main/java/com/matteovelez/business/
├── HealthController.java
├── product/
│   ├── Product.java
│   ├── ProductController.java
│   ├── ProductRepository.java
│   └── ProductService.java
└── inventory/
    ├── StockMovement.java
    └── StockMovementRepository.java
src/test/java/ — integration tests
.github/workflows/ci.yml
Dockerfile
render.yaml
pom.xml
```

## Quality and deployment

GitHub Actions runs `mvn --batch-mode verify` on pushes and pull requests to `main`. The workflow is being verified against the initial implementation.

A Dockerfile and Render free-plan Blueprint are included as deployment preparation. No Render service has been created and there is no live URL yet.

## Current limitations

- H2 is in-memory: data resets whenever the application restarts.
- No authentication, authorization, or multi-user separation.
- Supplier management, purchase orders, sales orders, and order lifecycle are not implemented.
- No database migrations, audit trail, or concurrency/locking strategy for high-volume inventory updates.
- This MVP is a portfolio demonstration, not a production inventory system.

## Project tracking

- [Roadmap](ROADMAP.md)
- [Changelog](CHANGELOG.md)
