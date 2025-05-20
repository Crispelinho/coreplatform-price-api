# Prueba Técnica - Inditex Core Platform

## Descripción

Este proyecto es una aplicación backend desarrollada en **Spring Boot** con enfoque **reactivo (WebFlux)** y acceso a datos mediante **R2DBC**, que simula parte del sistema core de ecommerce de Inditex.

La aplicación expone un endpoint REST que permite consultar el precio aplicable a un producto en una fecha determinada, según su marca y reglas de prioridad en la tabla `PRICES`.

La base de datos utilizada es **H2 en memoria**, inicializada automáticamente con datos de prueba al arrancar la aplicación.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring WebFlux
- Spring Data R2DBC
- H2 Database (en memoria)
- JUnit 5 / Reactor Test
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

## Endpoint expuesto

**GET /api/prices**

Consulta el precio aplicable para una marca, producto y fecha específicos.

### Parámetros

| Nombre        | Tipo     | Descripción                          |
|---------------|----------|--------------------------------------|
| `date`        | `string` | Fecha de aplicación (formato ISO-8601, ej. `2020-06-14T10:00:00`) |
| `productId`   | `long`   | Identificador del producto           |
| `brandId`     | `long`   | Identificador de la marca            |

### Respuesta

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
