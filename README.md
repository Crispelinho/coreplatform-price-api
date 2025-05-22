# Prueba Técnica - Inditex Core Platform

## Descripción

Este proyecto es una aplicación backend desarrollada en **Spring Boot** con enfoque **reactivo (WebFlux)** y acceso a datos mediante **R2DBC**, que simula parte del sistema core de ecommerce de Inditex.

La aplicación expone un endpoint REST que permite consultar el precio aplicable a un producto en una fecha determinada, según su marca y reglas de prioridad en la tabla `PRICES`.

La base de datos utilizada es **H2 en memoria**, inicializada automáticamente con datos de prueba al arrancar la aplicación.

---

## Características
- Exposición de endpoints REST para consultar precios de productos.
- Uso de DTOs como PriceResponse para estructurar las respuestas.
- Arquitectura hexagonal y principios SOLID.
- Configuración y dependencias gestionadas con Gradle.
- Incluye pruebas unitarias y de integración.

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
- Respuesta exitosa: Código 200 y un array de objetos `Price`.
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

## Estructura de carpetas (Arquitectura Hexagonal)

El proyecto sigue una arquitectura hexagonal (puertos y adaptadores), separando claramente los distintos niveles de la aplicación:

```
src/
  main/
    java/
      com.inditex.coreplatform/
        application/         # Lógica de aplicación y casos de uso
          service/
          usecases/
        domain/              # Modelos y lógica de dominio
          models/
        infrastructure/      # Adaptadores de entrada/salida (REST, persistencia, mappers)
          rest/
            controllers/
            controllers.responses/
          mappers/
    resources/               # Configuración y scripts de base de datos
      application.properties
      data.sql
      schema.sql
  test/
    java/
      com.inditex.coreplatform/
        application/
        domain/
        infrastructure/
```

- **application**: Casos de uso y servicios de la aplicación.
- **domain**: Entidades y lógica de negocio.
- **infrastructure**: Adaptadores externos (controladores REST, persistencia, mapeadores).
- **resources**: Configuración y datos de la base de datos.
- **test**: Pruebas unitarias y de integración.

---

## Estructura de carpetas y archivos principales

```
price-service/
├── build.gradle
├── gradlew
├── gradlew.bat
├── HELP.md
├── README.md
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── inditex/
│   │   │           └── coreplatform/
│   │   │               ├── application/
│   │   │               │   ├── service/
│   │   │               │   │   └── PriceService.java
│   │   │               │   └── usecases/
│   │   │               │       ├── GetPricesUseCase.java
│   │   │               │       └── queries/
│   │   │               │           └── GetApplicablePriceQuery.java
│   │   │               ├── domain/
│   │   │               │   └── models/
│   │   │               │       └── Price.java
│   │   │               └── infrastructure/
│   │   │                   ├── mappers/
│   │   │                   │   └── PriceMapper.java
│   │   │                   │   └── PriceMapperImpl.java
│   │   │                   └── rest/
│   │   │                       └── controllers/
│   │   │                           ├── PriceController.java
│   │   │                           └── responses/
│   │   │                               └── PriceResponse.java
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
│                       │   ├── service/
│                       │   │   └── PriceServiceTest.java
│                       │   └── usecases/
│                       │       ├── GetPricesUseCaseTest.java
│                       │       └── queries/
│                       │           └── GetApplicablePriceQueryTest.java
│                       ├── infrastructure/
│                       │   ├── controllers/
│                       │   │   └── PriceControllerTest.java
│                       │   └── mappers/
│                       │       └── PriceMapperImplTest.java
│                       └── domain/
│                           └── models/

build/
  ... (archivos generados por compilación y pruebas)
```

Esta estructura refleja todos los archivos fuente y de test generados hasta el momento, organizados según la arquitectura hexagonal y las convenciones de un proyecto Spring Boot moderno.

---
