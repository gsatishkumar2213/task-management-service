# Task Management Service

Reactive microservice with Spring WebFlux, R2DBC, and PostgreSQL.

## Tech Stack

- Spring Boot 4.0.2, WebFlux, R2DBC, PostgreSQL, Java 21

## Setup

1. Create DB: `CREATE DATABASE task_management_db;`
2. Create ENUM: `CREATE TYPE task_status AS ENUM (...);`
3. Create table: [schema SQL]
4. Config: Update application.properties
5. Run: `mvn spring-boot:run`

## API

- POST /tasks - Create
- GET /tasks - Get all
- GET /tasks/{id} - Get by ID
- PUT /tasks/{id} - Update
- DELETE /tasks/{id} - Delete

[rest of endpoints]

## Key Learning

- Non-blocking reactive operations with Mono/Flux
- R2DBC for async database access
- Backpressure handling