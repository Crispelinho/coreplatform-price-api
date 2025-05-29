# Prueba Técnica - Inditex Core Platform

## Descripción

Este proyecto es una aplicación backend desarrollada en **Spring Boot** con enfoque **reactivo (WebFlux)** y acceso a datos mediante **R2DBC**, que simula parte del sistema core de ecommerce de Inditex.

La aplicación expone endpoints REST para consultar el precio aplicable a un producto en una fecha determinada, según su marca y reglas de prioridad en la tabla `PRICES`.

La base de datos utilizada es **H2 en memoria**, inicializada automáticamente con datos de prueba al arrancar la aplicación.

---

## Características
- Endpoints REST para consulta de precios.
- Uso de DTOs para estructurar las respuestas.
- Arquitectura hexagonal y principios SOLID.
- Configuración y dependencias gestionadas con Gradle.
- Pruebas unitarias y de integración.
- Cobertura de tests y análisis de calidad con Jacoco y SonarQube.
- Pipeline CI/CD con ejemplo de configuración para GitHub Actions.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring WebFlux
- Spring Data R2DBC
- H2 Database (en memoria)
- JUnit 5 / Reactor Test
- Jacoco (cobertura de tests)
- SonarQube (análisis de calidad de código)
- Gradle

---

## Modelo de datos

La tabla `PRICES` contiene los siguientes campos:

| Campo       | Descripción                                                                 |
|-------------|-----------------------------------------------------------------------------|
| `BRAND_ID`  | Identificador de la marca (ej. 1 = ZARA)                                    |
| `START_DATE`, `END_DATE` | Rango de fechas de validez del precio                     |
| `PRICE_LIST`| Identificador de la tarifa                                                  |
| `PRODUCT_ID`| Identificador del producto                                                  |
| `PRIORITY`  | Nivel de prioridad para resolver solapamientos entre precios               |
| `PRICE`     | Precio final aplicable                                                      |
| `CURR`      | Moneda (formato ISO, ej. EUR)                                               |

---

## Instalación

1. Clona el repositorio o descarga el código fuente.
2. Asegúrate de tener instalado Java 17 y Gradle.
3. Desde la raíz del proyecto, ejecuta:

```sh
./gradlew build
```

4. Para iniciar la aplicación localmente:

```sh
./gradlew bootRun
```

5. El servicio estará disponible en: `http://localhost:8080`

6. Para ejecutar los tests y ver el reporte de cobertura:

```sh
./gradlew test jacocoTestReport
```

- El reporte de cobertura Jacoco estará en `build/reports/jacoco/test/html/index.html`.
- El reporte de tests estará en `build/reports/tests/test/index.html`.

7. (Opcional) Para analizar la calidad del código con SonarQube, asegúrate de tener un servidor SonarQube disponible y configura las propiedades necesarias en `build.gradle` o `sonar-project.properties`.

---

## Endpoints disponibles

### 1. Obtener todos los precios

**GET /prices**

- Devuelve un listado de todos los precios disponibles en formato JSON.
- Respuesta exitosa: Código 200 y un array de objetos `PriceResponse`.
- Si no existen precios: Código 404.

#### Ejemplo de respuesta exitosa

```json
[
  {
    "productId": 35455,
    "brandId": 1,
    "rateId": 2,
    "startDate": "2020-06-14T15:00:00",
    "endDate": "2020-06-14T18:30:00",
    "price": 25.45
  }
]
```

### 2. Obtener precio aplicable por producto, marca y fecha

**GET /applicationPrices?productId={productId}&brandId={brandId}&applicationDate={fecha}**

- Devuelve el precio aplicable para un producto, marca y fecha dados.
- Respuesta exitosa: Código 200 y un objeto `PriceResponse`.
- Si no existe precio aplicable: Código 404.

#### Parámetros

| Nombre        | Tipo     | Descripción                          |
|---------------|----------|--------------------------------------|
| `applicationDate`        | `String` | Fecha de aplicación (formato ISO-8601, ej. `2020-06-14T10:00:00`) |
| `productId`   | `Integer`   | Identificador del producto           |
| `brandId`     | `Integer`   | Identificador de la marca            |

#### Ejemplo de respuesta exitosa

```json
{
  "productId": 35455,
  "brandId": 1,
  "rateId": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45
}
```

#### Ejemplo de respuesta cuando no hay precio aplicable

- Código de estado: 404 Not Found

---

## Estructura de carpetas y archivos principales

```
coreplatform-price-api/
├── .gitignore
├── build.gradle
├── CHANGELOG.md
├── Dockerfile
├── gradlew
├── gradlew.bat
├── README.md
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── inditex/
│   │   │           └── coreplatform/
│   │   │               ├── PriceServiceApplication.java
│   │   │               ├── application/
│   │   │               │   ├── exceptions/
│   │   │               │   │   └── MissingPriceApplicationRequestParamException.java
│   │   │               │   ├── service/
│   │   │               │   │   └── ReactivePriceService.java
│   │   │               │   └── usecases/
│   │   │               │       ├── GetPricesUseCase.java
│   │   │               │       └── queries/
│   │   │               │           └── GetApplicablePriceQuery.java
│   │   │               ├── domain/
│   │   │               │   ├── models/
│   │   │               │   │   └── Price.java
│   │   │               │   └── ports/
│   │   │               ├── infrastructure/
│   │   │               │   ├── config/
│   │   │               │   │   ├── UseCaseBeanConfig.java
│   │   │               │   ├── mappers/
│   │   │               │   │   ├── PriceMapper.java
│   │   │               │   │   └── PriceMapperImpl.java
│   │   │               │   ├── persistence/
│   │   │               │   │   ├── entities/
│   │   │               │   │   │   └── PriceEntity.java
│   │   │               │   │   ├── repositories/
│   │   │               │   │   │   ├── IReactivePriceRepository.java
│   │   │               │   │   │   └── PriceRepositoryAdapter.java
│   │   │               │   ├── rest/
│   │   │               │   │   ├── controllers/
│   │   │               │   │   │   ├── PriceController.java
│   │   │               │   │   │   └── dtos/
│   │   │               │   │   │       ├── ErrorPriceResponse.java
│   │   │               │   │   │       └── PriceResponse.java
│   │   │               │   │   └── exceptions/
│   │   │               │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── data.sql
│   │       └── schema.sql
│   └── test/
│       └── java/
│           └── com/
│               └── inditex/
│                   └── coreplatform/
│                       ├── PriceServiceApplicationTests.java
│                       ├── application/
│                       │   ├── exceptions/
│                       │   │   └── MissingPriceApplicationRequestParamExceptionTest.java
│                       │   ├── service/
│                       │   │   └── ReactivePriceServiceTest.java
│                       │   └── usecases/
│                       │       ├── GetApplicablePriceUseCaseTest.java
│                       │       ├── GetPricesUseCaseTest.java
│                       │       └── queries/
│                       │           └── GetApplicablePriceQueryTest.java
│                       ├── domain/
│                       │   └── models/
│                       │       └── PriceTest.java
│                       ├── infrastructure/
│                       │   ├── controllers/
│                       │   │   └── PriceControllerTest.java
│                       │   ├── mappers/
│                       │   │   └── PriceMapperImplTest.java
│                       │   ├── persistence/
│                       │   │   └── repositories/
│                       │   │       └── PriceRepositoryAdapterTest.java
│                       │   └── rest/
│                       │       └── exceptions/
│                       │           └── GlobalExceptionHandlerTest.java
build/
  ... (archivos generados por compilación y pruebas)
.github/
  workflows/
    ci.yml
    cd.yml
    backport.yml
load-tests/
  load-test.js

```

---

## Integración y Despliegue Continuo (CI/CD)

El proyecto cuenta con pipelines automatizados configurados en la carpeta `.github/workflows/`:

- **ci.yml**: Ejecuta el pipeline de integración continua en cada push, pull request o creación de rama relevante. Realiza checkout del código, configura JDK 17, ejecuta build, tests y genera reportes de cobertura con Jacoco. Los resultados y reportes se suben como artefactos del workflow.
- **cd.yml**: Pipeline de despliegue continuo, encargado de publicar el artefacto generado a un entorno de pruebas o producción tras pasar los tests y validaciones.
- **backport.yml**: Automatiza la creación de pull requests de backport cuando se hace merge a `main`, facilitando la sincronización con ramas `develop` o `release/*`.
