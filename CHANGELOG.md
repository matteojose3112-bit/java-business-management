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
- GitHub Actions workflow running Maven verify; initial CI run passed.
- Dockerfile and Render deployment configuration.

### Deployment
- Created the `java-business-management` Render web service on the free plan in Virginia.
- Connected the service to the GitHub repository's `main` branch with automatic deploys.
- Live URL: https://java-business-management.onrender.com
- Render dashboard: https://dashboard.render.com/web/srv-dbvmvbt9fdbs73bofrt0
- Initial Docker build is in progress; the public health endpoint has not yet been verified.

### Documentation
- Documented the implemented API, local setup, and known limitations.
- Updated roadmap to separate completed MVP work from future persistence, order management, and production-readiness work.

### Known limitations
- H2 is in-memory and all records reset on restart.
- Authentication, supplier and order workflows, persistent storage, and production concurrency safeguards are not yet implemented.
