# Estik API

Estik is a REST API for managing IT assets and support tickets across facilities and departments. It centralizes computer specifications, equipment allocations, department movement records, and user access in a Spring Boot application backed by PostgreSQL.

This repository contains the backend and its Docker infrastructure. A frontend is not included.

## Contents

- [Features and workflow](#features-and-workflow)
- [Technology stack](#technology-stack)
- [Project structure](#project-structure)
- [Run locally](#run-locally)
- [Run with Docker Compose](#run-with-docker-compose)
- [Create the first administrator](#create-the-first-administrator)
- [Authentication and permissions](#authentication-and-permissions)
- [API reference](#api-reference)
- [Backups](#backups)
- [Build and tests](#build-and-tests)
- [Implementation notes](#implementation-notes)

## Features and workflow

- **Facilities and departments:** organize locations and the departments within them. Department names are unique within each facility.
- **Computer inventory:** store a computer's name, processor, memory, storage type and capacity, operating system, assembler, facility, and department.
- **Equipment allocations:** maintain a catalog of items and the quantity assigned to each department. Each item/department pair is unique.
- **Movement records:** automatically record computer placement and transfers between departments. Records are included in department details.
- **Support tickets:** associate requests with their author and facility, track priority and status, and retrieve the current user's tickets.
- **User administration:** register users, activate accounts, and assign `USER` or `SUPER` roles.
- **Search and pagination:** query collection endpoints using Spring Data pagination and resource-specific filters.

A typical setup starts with an active administrator, followed by facilities, departments, and inventory. Computers reference an existing user as their assembler. Activated users can then submit tickets for an existing facility.

New tickets start as `OPEN`. Administrators can update their status to `IN_PROGRESS`, `RESOLVED`, or `CLOSED`; the implementation does not enforce a fixed transition order. The author is taken from the authenticated user, rather than from the request body.

## Technology stack

Versions below are declared in the repository; they are not a claim of runtime compatibility testing.

| Component | Version / purpose |
| --- | --- |
| Java | 25 |
| Spring Boot | 4.1.1 |
| Spring MVC | JSON REST controllers |
| Spring Data JPA / Hibernate | Persistence and schema updates |
| Spring Security | Stateless authentication, authorization, and BCrypt passwords |
| JJWT | 0.12.6, signed JWT access tokens |
| Jakarta Validation | Request validation |
| Lombok | Generated accessors and constructors |
| springdoc OpenAPI | 2.8.5, API documentation dependency |
| Maven Wrapper | Maven 3.9.16 |
| PostgreSQL | Docker image `postgres:15-alpine` |
| Docker Compose | API, database, pgAdmin, and scheduled backups |

## Project structure

All commands below run from the directory containing this README and `pom.xml`. If you opened the parent `sistema-estik-api` workspace, enter `estik` first.

```text
.
├── src/main/java/com/marcos/estik/
│   ├── EstikApplication.java    # Application entry point
│   ├── controller/             # HTTP routes and request handling
│   ├── service/                # Business logic and DTO mapping
│   ├── repository/             # Spring Data JPA queries
│   ├── domain/
│   │   ├── entity/             # Database entities and relationships
│   │   ├── dto/                # Request and response records
│   │   └── enums/              # Roles, statuses, priorities, and asset values
│   ├── infra/security/         # Security chain, JWT filter, and password encoder
│   └── handler/                # REST exception handlers
├── src/main/resources/application.properties
├── src/test/java/com/marcos/estik/EstikApplicationTests.java
├── .mvn/wrapper/               # Maven distribution configuration
├── backup/
│   ├── backup.sh              # PostgreSQL dump and retention script
│   └── dockerfile             # Cron-based backup container
├── docker-compose.yml
├── dockerfile                 # Multi-stage API image
├── pom.xml
├── mvnw
└── mvnw.cmd
```

Requests pass through the security filter before reaching a controller. Controllers validate supported request DTOs and delegate to services, which load and persist entities through repositories and map results to response DTOs.

Hibernate uses `spring.jpa.hibernate.ddl-auto=update`. The database must already exist; Hibernate manages its tables. No versioned migration scripts or initial data seed are included.

## Run locally

### Prerequisites

- JDK 25 with `JAVA_HOME` pointing to the JDK.
- A running PostgreSQL instance, with PostgreSQL 15 matching the supplied container configuration.
- Internet access for the Maven Wrapper and dependency downloads on the first build.

A separate Maven installation is not required.

### 1. Prepare the database

The default local configuration connects to `jdbc:postgresql://localhost:5432/dev` with username and password `postgres`.

Using `psql` with an existing local PostgreSQL server:

```sh
psql -h localhost -U postgres -d postgres -c "CREATE DATABASE dev;"
```

Run this only if `dev` does not already exist. Alternatively, point the application at an existing database using the environment variables below.

### 2. Configure and start the application

Windows PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5432/dev'
$env:SPRING_DATASOURCE_USERNAME = 'postgres'
$env:SPRING_DATASOURCE_PASSWORD = 'postgres'
$keyBytes = New-Object byte[] 48
$keyGenerator = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$keyGenerator.GetBytes($keyBytes)
$keyGenerator.Dispose()
$env:SECRET_KEY = [Convert]::ToBase64String($keyBytes)
.\mvnw.cmd spring-boot:run
```

Linux / macOS, with OpenSSL available for key generation:

```bash
export SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/dev'
export SPRING_DATASOURCE_USERNAME='postgres'
export SPRING_DATASOURCE_PASSWORD='postgres'
export SECRET_KEY="$(openssl rand -base64 48)"
sh ./mvnw spring-boot:run
```

The API listens on [http://localhost:8080](http://localhost:8080). There is no controller for the root URL; use a documented endpoint to interact with the API.

| Environment variable | Meaning |
| --- | --- |
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC connection URL |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `SECRET_KEY` | JWT HMAC signing secret; at least 32 UTF-8 bytes |
| `SERVER_PORT` | Optional HTTP port override; default is `8080` |

Keep a stable secret across restarts if existing tokens must remain valid. The checked-in fallback secret and database credentials are development defaults. A `.env` file is used by Docker Compose; the application does not explicitly load it when started through Maven or `java -jar`.

## Run with Docker Compose

Docker Engine or Docker Desktop with Compose is required. Java and Maven run inside the API build container.

### 1. Set the Compose variables

Create or review a local `.env` next to `docker-compose.yml` with these values:

```dotenv
DB_NAME=estik-db
DB_USER=postgres
DB_USERNAME=postgres
DB_PASSWORD=postgres
PGADMIN_EMAIL=admin@example.com
PGADMIN_PASSWORD=replace-with-a-local-admin-password
SECRET_KEY=replace-with-a-random-secret-of-at-least-32-bytes
```

Replace the two placeholder values before starting. `.env` is ignored by Git.

The supplied Compose file hardcodes the database service to `estik-db` and `postgres` / `postgres`. Keep `DB_NAME`, `DB_USER`, `DB_USERNAME`, and `DB_PASSWORD` aligned with those values for this configuration. `DB_USER` is used by the health check, while `DB_USERNAME` is used by the API and backup service. Changing only `.env` does not change the database container's initialization settings.

### 2. Start the services

Both build files are named lowercase `dockerfile`. The following inline override selects their exact filenames without editing the Compose file.

Windows PowerShell:

```powershell
@'
services:
  app-api:
    build:
      context: .
      dockerfile: dockerfile
  postgres-backup:
    build:
      context: ./backup
      dockerfile: dockerfile
'@ | docker compose -f docker-compose.yml -f - up --build -d
```

Linux / macOS:

```bash
docker compose -f docker-compose.yml -f - up --build -d <<'YAML'
services:
  app-api:
    build:
      context: .
      dockerfile: dockerfile
  postgres-backup:
    build:
      context: ./backup
      dockerfile: dockerfile
YAML
```

Check service status and API logs:

```sh
docker compose ps
docker compose logs -f app-api
```

| Service | Address / purpose |
| --- | --- |
| API | [http://localhost:8080](http://localhost:8080) |
| PostgreSQL | `localhost:5432`, database `estik-db` |
| pgAdmin | [http://localhost:5050](http://localhost:5050), credentials from `.env` |
| Backup worker | Daily dumps into the host's `./backups` directory |

When adding the database server in pgAdmin, use host `postgres-db`, port `5432`, database `estik-db`, and the database credentials. Within containers, `localhost` refers to that container.

To stop the stack while retaining its named database and pgAdmin volumes:

```sh
docker compose down
```

Avoid adding `-v` unless you intend to remove the named volumes and their data.

## Create the first administrator

There is no preconfigured administrator. Registration always creates an inactive account with role `USER`, so the first administrator requires a one-time database update.

1. Send `POST /auth/register` with the following JSON, using an API client and a password of your choice:

   ```json
   {
     "username": "admin",
     "password": "replace-with-your-own-password"
   }
   ```

2. Connect to the application's database. For the Compose stack:

   ```sh
   docker compose exec postgres-db psql -U postgres -d estik-db
   ```

   For the default local setup:

   ```sh
   psql -h localhost -U postgres -d dev
   ```

3. Activate and promote the account you just registered:

   ```sql
   UPDATE users
   SET active = TRUE, role = 'SUPER'
   WHERE username = 'admin';
   ```

   Confirm that exactly one row was updated, then exit `psql` with `\q`.

4. Send `POST /auth/login` with the same username and password. The response contains:

   ```json
   {
     "accessToken": "<signed-jwt>"
   }
   ```

5. Include the token on protected requests:

   ```http
   Authorization: Bearer <signed-jwt>
   ```

After this bootstrap, a `SUPER` user can activate other accounts with `PUT /users/{id}/active` and `{"active":true}`, and change roles with `PUT /users/{id}/role` and `{"role":"SUPER"}` or `{"role":"USER"}`.

## Authentication and permissions

Passwords are hashed with BCrypt. JWTs expire after 24 hours and contain the username as subject, plus `id` and `role` claims. The filter reloads the user from the database for authenticated requests. There is no refresh-token or logout endpoint.

`SUPER` users receive both `ROLE_SUPER` and `ROLE_USER`. The following permissions reflect `SecurityConfig`, rather than the descriptive Swagger annotations:

| Access | Routes |
| --- | --- |
| Public | `POST /auth/login`, `POST /auth/register`, configured Swagger/OpenAPI paths, `/error` |
| `USER` or `SUPER` | `POST /tickets`, `GET /facilities`, `GET /tickets/me`, `GET /tickets/{id}`, `PUT /tickets/{id}`, `DELETE /tickets/{id}` |
| `SUPER` | All remaining implemented routes, including ticket listing and status changes |

The current ticket-by-ID operations do not check ticket ownership. Any user granted those routes can access, edit, or delete a ticket by its ID; only `/tickets/me` filters results by the current user. Account deactivation blocks login, but the JWT filter does not explicitly recheck `isEnabled()` for an already issued token.

Browser CORS access is configured for `http://localhost:5173`, `http://localhost:5174`, and `http://localhost:3000`, with `Authorization` and `Content-Type` headers.

## API reference

The API uses JSON request bodies. Preserve the spelling **`departaments`** in paths and **`departamentId`** in payloads: these are the names implemented in the code.

### Routes

| Resource | Endpoints |
| --- | --- |
| Authentication | `POST /auth/register`, `POST /auth/login`, `PUT /auth/{id}` |
| Users | `GET /users`, `GET /users/{id}`, `PUT /users/{id}/role`, `PUT /users/{id}/active` |
| Facilities | `GET /facilities`, `POST /facilities`, `GET /facilities/{id}`, `PUT /facilities/{id}`, `DELETE /facilities/{id}` |
| Departments | `GET /departaments`, `POST /departaments`, `GET /departaments/{id}`, `PUT /departaments/{id}`, `DELETE /departaments/{id}` |
| Items | `GET /items`, `POST /items`, `GET /items/{id}`, `PUT /items/{id}`, `DELETE /items/{id}` |
| Department allocations | `GET /departaments/items`, `POST /departaments/items`, `GET /departaments/items/{id}`, `PUT /departaments/items/{id}`, `DELETE /departaments/items/{id}` |
| Computers | `GET /pcs`, `POST /pcs`, `GET /pcs/{id}`, `PUT /pcs/{id}`, `DELETE /pcs/{id}` |
| Tickets | `GET /tickets`, `POST /tickets`, `GET /tickets/me`, `GET /tickets/{id}`, `PUT /tickets/{id}`, `DELETE /tickets/{id}`, `PUT /tickets/{id}/status` |

Movement records are returned in `GET /departaments/{id}`; there is no standalone records controller.

### Request bodies

Use existing IDs returned by earlier requests instead of assuming that IDs start at `1`.

| Create / update resource | Example JSON |
| --- | --- |
| Facility | `{"name":"Head Office","code":"HQ"}` |
| Department | `{"name":"IT","facilityId":1}` |
| Item | `{"name":"Keyboard","code":"KB-001","description":"USB keyboard"}` |
| Department allocation | `{"quantity":5,"departamentId":1,"itemId":1}` |
| Ticket | `{"facilityId":1,"title":"Computer will not start","description":"The workstation does not power on.","priority":"URGENT"}` |
| Ticket status | `{"status":"IN_PROGRESS"}` |

Computer creation or update:

```json
{
  "name": "PC-001",
  "processor": "Intel Core i5",
  "memory": "16 GB",
  "storageType": "NVME",
  "storageCapacity": 512,
  "facilityId": 1,
  "departamentId": 1,
  "assemblerId": 1,
  "os": "WINDOWS"
}
```

The schema does not specify a unit for `storageCapacity`; use a consistent convention in clients. Supply a facility that matches the selected department, since the service loads those IDs independently.

| Field | Accepted enum values |
| --- | --- |
| Role | `USER`, `SUPER` |
| Ticket priority | `MILD`, `MODERATE`, `URGENT` |
| Ticket status | `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED` |
| Storage type | `SSD`, `NVME`, `HDD` |
| Operating system | `WINDOWS`, `UBUNTU` |
| Movement direction | `RECEIVED`, `SENT` |

### Pagination and filtering

Collection endpoints return Spring Data `Page` responses. Use zero-based `page`, `size`, and `sort` parameters:

```http
GET /items?q=keyboard&page=0&size=20&sort=name,asc
GET /pcs?assembler=1&page=0&size=20
GET /tickets?q=network&priority=URGENT&page=0&size=20&sort=createdAt,desc
```

The `q` filter performs a case-insensitive substring search on names, usernames, or ticket titles, depending on the resource. For department allocations, it searches the item's name. Computers are an exception: their `q` filter matches the exact name and can be combined with an `assembler` user ID. `/tickets/me` supports pagination but has no search or priority filter.

### Responses and errors

Creation endpoints normally return `201 Created`, reads and updates return `200 OK`, and deletes return `204 No Content`.

Handled application errors use `{"message":"..."}`. Validation errors use `{"fields":{"fieldName":"validation message"}}`.

| Status | Current handler behavior |
| --- | --- |
| `400` | DTO validation errors |
| `403` | Bad credentials, inactive login, or data integrity violations |
| `404` | Handled missing entities/resources or number-format errors |
| `405` | Unsupported HTTP method |
| `409` | Handled duplicate-entity errors |

Security-filter responses and unhandled exceptions may use different response bodies. In particular, a missing ticket currently throws a generic runtime exception rather than the handled entity-not-found exception.

### OpenAPI

The repository includes springdoc and permits these documentation paths:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [OpenAPI JSON](http://localhost:8080/v3/api-docs)

Their runtime availability has not been verified. The code references a `bearerAuth` security requirement but does not declare its security scheme. If Swagger's authorization control is unavailable, send the Bearer header using an API client. Consult the route and permission tables above when annotations differ from implementation.

## Backups

The backup container runs `backup.sh` at midnight daily, using `America/Sao_Paulo` as its configured timezone. It creates PostgreSQL custom-format dumps with `pg_dump -F c`:

```text
backups/estik_YYYY-MM-DD_HH-MM-SS.dump
```

After a successful dump, it removes matching backup files older than 30 days according to `find -mtime +30`. The backup service targets `estik-db` and writes to a host bind mount; `backups/` is ignored by Git.

To run a backup manually or inspect scheduled backup output:

```sh
docker compose exec postgres-backup /backup.sh
docker compose exec postgres-backup cat /var/log/backup.log
```

The log file is created by the scheduled invocation, so it may not exist before the first scheduled run. Dumps use PostgreSQL's custom format and should be restored with `pg_restore`; no automated restore workflow is included.

## Build and tests

With the same database and environment configuration used for local startup:

Windows PowerShell:

```powershell
.\mvnw.cmd test
.\mvnw.cmd clean package
java -jar target/estik-0.0.1-SNAPSHOT.jar
```

Linux / macOS:

```bash
sh ./mvnw test
sh ./mvnw clean package
java -jar target/estik-0.0.1-SNAPSHOT.jar
```

The current test suite contains one `@SpringBootTest` context-load test. It uses the application's database configuration; no isolated test database or test profile is provided. Use a disposable development database because Hibernate schema updates also apply when the test context starts.

The API Docker image runs `mvn clean package -DskipTests` during its build, then runs the resulting JAR on a Java 25 JRE.

## Implementation notes

This README documents the source as it stands. Startup commands and tests were not executed as part of the documentation review.

- **Database selection:** local defaults use `dev`; Compose uses `estik-db`. A database-not-found error usually indicates that the connection URL and initialized database differ.
- **Docker initialization:** PostgreSQL initialization variables apply when its data directory is first created. An existing `pgdata` volume retains its database and credentials.
- **Ports:** the supplied stack publishes `5432`, `5050`, and `8080`. A local PostgreSQL instance or another API can conflict with these bindings.
- **Credential updates:** `PUT /auth/{id}` changes the loaded entity but does not explicitly save it or declare a transaction in the service. Persistence of this operation should be verified before relying on it.
- **Ticket payloads:** the general ticket request contains a `status` field, but creation always uses `OPEN` and general updates ignore that field. Use the dedicated status endpoint. Ticket creation also returns a `Location` header using `/ticket/{id}`, while the implemented detail route is `/tickets/{id}`.
- **Movement semantics:** creating a PC records `SENT` for its department. Moving it records `RECEIVED` for the previous department and `SENT` for the destination. Item allocations do not create movement records.
- **Deletion behavior:** entity relationships include cascading deletion and orphan removal. Facility and department deletion can remove related inventory, tickets, or movement records; those records are not an immutable audit trail.
- **Dependency troubleshooting:** if startup or documentation fails with class-loading errors, inspect the declared Spring Boot and springdoc versions together. Their compatibility was not validated during this review.

## License

No license file is included, and the Maven license metadata is empty. This repository does not currently declare a license.
