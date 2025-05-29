# Changelog

Todas las modificaciones importantes de este proyecto se documentarán en este archivo.

## [1.0.4] - 2025-05-29
### Añadido
- Pruebas unitarias completas para PriceController, cubriendo casos de éxito y error para ambos endpoints principales.
- Excepciones personalizadas en la capa de aplicación (`MissingPriceApplicationRequestParamException`).
- Observaciones y recomendaciones sobre arquitectura hexagonal y buenas prácticas en la documentación.
- Refactorización de DTOs y mapeos para mayor claridad y separación de capas.

### Corregido
- Mejoras en la estructura y claridad del README.md, eliminando información duplicada y reorganizando secciones.
- Ajustes en los tests para asegurar la correcta validación de respuestas HTTP y cuerpos de respuesta.

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
