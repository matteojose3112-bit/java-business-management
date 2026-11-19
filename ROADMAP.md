# Roadmap

Items are complete only when implemented and verified.

## Phase 1 — Inventory API foundation

- [x] Establish Java 21, Spring Boot, Maven, and project structure.
- [x] Add health endpoint.
- [x] Model products and stock movements.
- [x] Implement product creation and listing.
- [x] Implement stock IN/OUT movements and movement history.
- [x] Enforce unique SKUs, positive movement quantities, and non-negative stock.
- [x] Add low-stock reporting.
- [x] Add Spring Boot integration tests.
- [x] Add GitHub Actions CI workflow.
- [x] Prepare Dockerfile and Render Blueprint.
- [ ] Verify the initial GitHub Actions run and fix any failures.

## Phase 2 — Persistence and reliability

- [ ] Replace in-memory H2 with persistent PostgreSQL.
- [ ] Add database migrations.
- [ ] Add transaction/concurrency safeguards for simultaneous stock updates.
- [ ] Add audit metadata and pagination/filtering.
- [ ] Expand tests for duplicate SKUs, missing products, and movement edge cases.

## Phase 3 — Business workflows

- [ ] Add supplier records and supplier-product relationships.
- [ ] Add purchase orders and receiving workflow.
- [ ] Add customer orders and stock reservation/fulfilment.
- [ ] Add reporting for stock valuation and movement summaries.

## Phase 4 — Deployment and production readiness

- [ ] Obtain approval before creating a Render service.
- [ ] Deploy on the free plan and verify the live health endpoint.
- [ ] Configure persistent database connectivity and environment variables.
- [ ] Add authentication, authorization, rate limiting, and operational logging.
- [ ] Document live API examples and known limitations.

## Definition of done

- Core inventory rules are covered by automated tests.
- The API can be run locally from documented instructions.
- No secrets or real customer data are committed.
- README, roadmap, and changelog reflect verified behavior.
- Deployment is only marked complete after a live endpoint is checked.
