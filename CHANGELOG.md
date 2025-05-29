# Changelog

All significant changes to this project will be documented in this file.

## [1.0.4] - 2025-05-29
### Added
- Complete unit tests for PriceController, covering success and error cases for both main endpoints.
- Custom exceptions in the application layer (`MissingPriceApplicationRequestParamException`).
- Observations and recommendations on hexagonal architecture and best practices in the documentation.
- Refactoring of DTOs and mappings for greater clarity and separation of layers.

### Fixed
- Improved structure and clarity of README.md, removing duplicate information and reorganizing sections.
- Adjustments in tests to ensure correct validation of HTTP responses and response bodies.

## [1.0.2] - 2025-05-23
### Added
- Automatic backport action for merges into `main`, creating PRs to `develop` or `release/**` branches.
### Changed
- Modified the CI pipeline to run when changes are made in a pull request (push to PR).

## [1.0.1] - 2025-05-23
### Added
- CI/CD configuration for continuous integration and deployment.
- Automatic execution of tests and quality analysis on each push (build, test, Jacoco, SonarQube).
- Artifact generation and coverage report in the pipeline.

## [1.0.0] - 2025-05-22
### Added
- Initial project structure following hexagonal architecture.
- Implementation of REST endpoints for price queries.
- Domain model and DTOs (`Price`, `PriceResponse`).
- Services and use cases for business logic.
- Unit and integration tests with JUnit 5 and Reactor Test.
- Test coverage configuration with Jacoco.
- Quality analysis integration with SonarQube.
- In-memory H2 database with initialization scripts.
- Initial documentation in README.md.
