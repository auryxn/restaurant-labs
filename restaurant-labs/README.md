# Restaurant Labs — Spring Boot WebApp

One project for Lab 3, Lab 4 and Lab 5.

## Domain

Two related entities:

- `Restaurant`
- `MenuItem` (`ManyToOne` restaurant relation)

## Implemented requirements

### Lab 3 — Simple Spring Boot WebApp

- CRUD for 2 related DB entities.
- Separate web controllers:
  - `RestaurantController`
  - `MenuItemController`
- Separate REST controllers:
  - `RestaurantRestController`
  - `MenuItemRestController`
- Search all / partial results and search by ID.
- 3-layer architecture:
  - Controller
  - Service
  - Repository
- Different DTOs for create, update and read use cases.
- Server-side validation via Jakarta Validation.
- Validation errors are displayed on Thymeleaf forms.
- Results and requests are available from web pages.

### Lab 4 — Logging, Exceptions, AOP

- SLF4J logging with Logback through Spring Boot.
- Log file: `logs/restaurant-labs.log`.
- INFO, DEBUG, WARN and ERROR levels are used.
- Logging covers REST requests, services and errors.
- Custom exceptions:
  - `ResourceNotFoundException`
  - `BadRequestException`
- Global error handling with `@ControllerAdvice` and `@ExceptionHandler`.
- API errors return structured JSON with correct HTTP statuses.
- AOP aspect `LoggingAspect` logs method execution time for controllers/services.

### Lab 5 — JWT + RBAC

- Stateless JWT authentication for `/api/**` endpoints.
- Spring Security RBAC:
  - `ROLE_USER` — read access.
  - `ROLE_MANAGER` — create/update.
  - `ROLE_ADMIN` — delete.
- Passwords are stored with BCrypt.
- JWT filter validates Bearer tokens.
- `@EnableMethodSecurity` is enabled.
- Service methods are protected with `@PreAuthorize`.

> Note: the lab text mentions Spring Boot 4.x, Spring Security 7.x and Java 25. The project is generated for Spring Boot 4 and Java 25. If Java 25 is not installed locally, install it or temporarily change the Gradle toolchain in `build.gradle.kts` to your JDK version for local testing.

## Demo users

Seeded automatically on startup:

| Username | Password | Role |
|---|---|---|
| `user` | `user1234` | USER |
| `manager` | `manager1234` | MANAGER |
| `admin` | `admin1234` | ADMIN |

## Run

```bash
./gradlew bootRun
```

Open:

- Web app: http://localhost:8080/restaurants
- H2 console: http://localhost:8080/h2-console

H2 settings:

- JDBC URL: `jdbc:h2:mem:restaurantdb`
- User: `sa`
- Password: empty

## API examples

Register:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"newuser","password":"password123"}'
```

Login:

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"admin1234"}' | jq -r .token)
```

Read restaurants:

```bash
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/restaurants
```

Create restaurant:

```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"name":"New Place","address":"Main street 1","cuisine":"Fusion"}'
```

## Project structure

```text
src/main/java/com/auryxn/restaurantlabs
├── aspect
├── config
├── controller
│   └── api
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
└── service
```
