# TMS API

TMS API is a backend-only REST API for a Transport Management System. It provides authentication, user management, vehicle and driver management, customer management, shipment orders, trip planning, assignment, pickup and delivery confirmation, shipment tracking, delivery proof upload, cost tracking, invoice management, dashboard summaries, PDF reports, WebSocket live shipment status, RabbitMQ notification queue, and Redis shipment tracking cache.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT Authentication
- PostgreSQL
- Redis
- RabbitMQ
- WebSocket STOMP
- Swagger/OpenAPI
- JasperReports
- Maven
- Lombok
- Bean Validation

## Folder Structure

```text
src/main/java/com/bytecorener/tmsapi
├── config
├── controller
├── dto
├── entity
├── enumtype
├── exception
├── mapper
├── queue
├── repository
├── security
├── service
│   └── impl
└── websocket
```

## Architecture

The project uses clean layered architecture:

- Controller layer handles REST requests and responses.
- Service layer defines business contracts.
- Service implementation layer contains business logic.
- Repository layer handles database access through Spring Data JPA.
- DTO layer separates API payloads from entities.
- Entity layer maps database tables.
- Config layer contains Spring Security, Redis, RabbitMQ, WebSocket, and OpenAPI configuration.
- Security layer handles JWT authentication.
- Exception layer handles global API errors.
- Mapper layer converts entities to API responses.
- Queue layer publishes and consumes RabbitMQ notifications.
- WebSocket layer publishes live shipment status updates.
- Report layer is represented by `ReportService` and JasperReports implementation.

## Database Configuration

PostgreSQL is configured in `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tms_api
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

Create a PostgreSQL database named `tms_api`, then adjust username and password as needed.

## Redis Configuration

Redis is used to cache shipment tracking data.

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

Cached tracking keys use this format:

```text
shipment:tracking:{shipmentOrderId}
```

## RabbitMQ Configuration

RabbitMQ is used for shipment notification messages.

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

app.rabbitmq.exchange=tms.exchange
app.rabbitmq.notification-queue=tms.notification.queue
app.rabbitmq.notification-routing-key=tms.notification
```

## JWT Authentication Flow

1. Register a user through `POST /api/v1/auth/register`.
2. Login through `POST /api/v1/auth/login`.
3. The login response returns a JWT token.
4. Send the token in protected requests:

```http
Authorization: Bearer {token}
```

## Roles

Available roles:

- `ADMIN`
- `DISPATCHER`
- `DRIVER`
- `CUSTOMER`

Access summary:

- Admin can manage users and system data.
- Dispatcher can manage operational shipment, trip, driver, vehicle, cost, invoice, dashboard, and report workflows.
- Driver can confirm pickup, confirm delivery, upload delivery proof, and view assigned shipment tracking.
- Customer can create shipment orders and view shipment tracking and invoices.

## API Response Format

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {},
  "timestamp": "2026-05-17T10:00:00"
}
```

## API Endpoints

### Authentication

- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`

### Users

- `POST /api/v1/users`
- `GET /api/v1/users`
- `GET /api/v1/users/{id}`
- `PUT /api/v1/users/{id}`
- `DELETE /api/v1/users/{id}`

### Vehicles

- `POST /api/v1/vehicles`
- `GET /api/v1/vehicles`
- `GET /api/v1/vehicles/{id}`
- `PUT /api/v1/vehicles/{id}`
- `DELETE /api/v1/vehicles/{id}`

### Drivers

- `POST /api/v1/drivers`
- `GET /api/v1/drivers`
- `GET /api/v1/drivers/{id}`
- `PUT /api/v1/drivers/{id}`

### Customers

- `POST /api/v1/customers`
- `GET /api/v1/customers`
- `GET /api/v1/customers/{id}`
- `PUT /api/v1/customers/{id}`

### Shipments

- `POST /api/v1/shipments`
- `GET /api/v1/shipments`
- `GET /api/v1/shipments/{id}`
- `GET /api/v1/shipments/{id}/tracking`
- `POST /api/v1/shipments/{id}/pickup-confirmation`
- `POST /api/v1/shipments/{id}/delivery-confirmation`
- `POST /api/v1/shipments/{id}/delivery-proof`

### Trips

- `POST /api/v1/trips`
- `GET /api/v1/trips`
- `GET /api/v1/trips/{id}`
- `POST /api/v1/trips/{id}/assignment`

### Costs

- `POST /api/v1/costs`
- `GET /api/v1/costs/shipment/{shipmentOrderId}`

### Invoices

- `POST /api/v1/invoices`
- `GET /api/v1/invoices`
- `POST /api/v1/invoices/{id}/paid`

### Dashboard

- `GET /api/v1/dashboard/summary`

### Reports

- `GET /api/v1/reports/shipments/{shipmentOrderId}/pdf`

## Example Request Bodies

### Register User

```json
{
  "fullName": "Admin User",
  "email": "admin@example.com",
  "password": "password123",
  "role": "ADMIN",
  "active": true
}
```

### Login

```json
{
  "email": "admin@example.com",
  "password": "password123"
}
```

### Create Vehicle

```json
{
  "plateNumber": "B1234TMS",
  "vehicleType": "Box Truck",
  "capacityKg": 5000,
  "status": "AVAILABLE"
}
```

### Create Shipment Order

```json
{
  "customerId": 1,
  "pickupAddress": "Warehouse A, Jakarta",
  "deliveryAddress": "Customer Site B, Bandung",
  "cargoDescription": "Electronic goods",
  "weightKg": 1200
}
```

### Create Trip Plan

```json
{
  "shipmentOrderId": 1,
  "plannedPickupAt": "2026-05-17T09:00:00",
  "plannedDeliveryAt": "2026-05-18T15:00:00"
}
```

### Assign Driver and Vehicle

```json
{
  "driverId": 1,
  "vehicleId": 1
}
```

### Confirm Pickup or Delivery

```json
{
  "note": "Shipment has been confirmed by the driver"
}
```

### Create Cost

```json
{
  "shipmentOrderId": 1,
  "description": "Fuel cost",
  "amount": 750000
}
```

### Create Invoice

```json
{
  "shipmentOrderId": 1,
  "totalAmount": 2500000
}
```

## WebSocket Usage

WebSocket endpoint:

```text
/ws
```

Subscribe to all shipment updates:

```text
/topic/shipments
```

Subscribe to a specific shipment:

```text
/topic/shipments/{shipmentOrderId}
```

Publish manual status message:

```text
/app/shipments/status
```

Message format:

```json
{
  "shipmentOrderId": 1,
  "orderNumber": "SHP-12345678",
  "status": "IN_TRANSIT",
  "note": "Shipment is moving to destination"
}
```

Shipment pickup and delivery confirmation endpoints also publish live status updates.

## PDF Report

JasperReports is used to generate a basic shipment PDF report.

Endpoint:

```text
GET /api/v1/reports/shipments/{shipmentOrderId}/pdf
```

The report contains:

- Order number
- Customer name
- Pickup address
- Delivery address
- Current shipment status

## Swagger/OpenAPI

Swagger UI is available at:

```text
/swagger-ui.html
```

OpenAPI JSON is available at:

```text
/api-docs
```

## Future Frontend Integration Notes

A future frontend can integrate with this API by:

- Calling REST endpoints using JWT Bearer tokens.
- Using WebSocket STOMP for live shipment status updates.
- Uploading delivery proof files with multipart form data.
- Downloading PDF reports from the report endpoint.
- Reading standard API responses from the shared response format.
