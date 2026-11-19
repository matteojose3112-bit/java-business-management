# Changelog

All notable project changes are recorded here.

## [0.1.0] — 2026-11-19

### Added
- Java 21 and Spring Boot 3.5 Maven application.
- Product entity and repository with unique SKU constraint.
- Product creation and listing REST endpoints.
- Low-stock report based on product reorder levels.
- Stock movement entity and repository.
- Stock IN/OUT workflow with validation and insufficient-stock protection.
- Per-product stock movement history endpoint.
- Health endpoint.
- Spring Boot integration tests using MockMvc.
- GitHub Actions workflow running Maven verify.
- Dockerfile and Render Blueprint for future deployment.

### Documentation
- Replaced planning-only README with implemented API documentation.
- Updated roadmap to separate completed MVP work from future persistence, order management, and production-readiness work.

### Known limitations
- H2 is in-memory and all records reset on restart.
- Authentication, supplier and order workflows, persistent storage, and production concurrency safeguards are not yet implemented.
- Render deployment configuration is prepared, but no service has been created.
