# Changelog

Todas las modificaciones importantes de este proyecto se documentarán en este archivo.

## [1.0.2] - 2025-05-23
### Añadido
- Acción automática de backport para merges contra `main`, que crea PRs hacia `develop` o ramas `release/**`.
### Cambiado
- Modificado el pipeline de CI para ejecutarse cuando se realizan cambios en un pull request (push al PR).

## [1.0.1] - 2025-05-23
### Añadido
- Configuración de CI/CD para integración y despliegue continuo.
- Ejecución automática de tests y análisis de calidad en cada push (build, test, Jacoco, SonarQube).
- Generación de artefactos y reporte de cobertura en el pipeline.

## [1.0.0] - 2025-05-22
### Añadido
- Estructura inicial del proyecto siguiendo arquitectura hexagonal.
- Implementación de endpoints REST para consulta de precios.
- Modelo de dominio y DTOs (`Price`, `PriceResponse`).
- Servicios y casos de uso para lógica de negocio.
- Pruebas unitarias y de integración con JUnit 5 y Reactor Test.
- Configuración de cobertura de tests con Jacoco.
- Integración de análisis de calidad con SonarQube.
- Base de datos H2 en memoria con scripts de inicialización.
- Documentación inicial en README.md.
