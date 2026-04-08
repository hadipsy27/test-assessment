# Labs Test CCD — Technical Assessment

A RESTful API built with **Spring Boot 3** for a technical assessment. The application manages **Products** with full CRUD operations and uses **JWT-based authentication** with role-based access control (ADMIN / USER). Responses are cached using **Redis**.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5 |
| Database | MySQL |
| Cache | Redis |
| Auth | Spring Security + JWT (JJWT 0.11.5) |
| Build Tool | Maven |
| Utilities | Lombok, Spring Validation |

---

## Prerequisites

- Java 17+
- Maven 3.8+
- MySQL (running on port `3306`)
- Redis (running on port `6379`)

---

## Configuration

Edit `src/main/resources/application.properties` to match your local environment:

```properties
# Server
server.port=8081

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/test-assessment?useSSL=false&serverTimezone=Asia/Jakarta
spring.datasource.username=root
spring.datasource.password=

# JWT
security.jwt.secret-key=your_secret_key_here
security.jwt.expiration-time=86400000   # 1 day in ms
```

Redis is configured in `application.yml`:

```yaml
spring:
  redis:
    host: localhost
    port: 6379
  cache:
    type: redis
```

> Make sure to create the `test-assessment` database in MySQL before starting the app. JPA will auto-create/update the tables on startup (`ddl-auto=update`).

---

## Running the Application

```bash
# Clone the repository
git clone <repository-url>
cd Technical-test-ccd

# Run with Maven wrapper
./mvnw spring-boot:run
```

Or on Windows:

```cmd
mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8081`.

---

## API Endpoints

### Authentication — `/api/auth`

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register a new user | ❌ |
| `POST` | `/api/auth/login` | Login and receive JWT token | ❌ |

#### Register Request Body
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "secret123",
  "role": "USER"
}
```

#### Login Request Body
```json
{
  "username": "johndoe",
  "password": "secret123"
}
```

#### Login Response
```json
{
  "status": 200,
  "message": "Success Login",
  "data": {
    "token": "<JWT_TOKEN>",
    "type": "Bearer",
    "username": "johndoe",
    "role": "USER",
    "expiresIn": 86400000
  }
}
```

---

### Products — `/api/products`

> Protected endpoints require the `Authorization: Bearer <token>` header.

| Method | Endpoint | Description | Role Required |
|---|---|---|---|
| `GET` | `/api/products` | Get all products (paginated) | ❌ Public |
| `GET` | `/api/products/{id}` | Get product by ID | ADMIN / USER |
| `GET` | `/api/products/search?name=` | Search products by name | ADMIN |
| `POST` | `/api/products/create` | Create a new product | ADMIN |
| `PUT` | `/api/products/update/{id}` | Update a product | ADMIN |
| `DELETE` | `/api/products/{id}` | Delete a product | ADMIN |

#### Get All Products — Query Params

| Param | Default | Description |
|---|---|---|
| `page` | `0` | Page number (0-indexed) |
| `size` | `10` | Items per page |
| `sortBy` | `id` | Field to sort by |
| `sortDir` | `asc` | Sort direction (`asc` / `desc`) |

#### Create / Update Product Request Body
```json
{
  "name": "Product Name",
  "description": "Product description",
  "price": 99000,
  "stock": 50,
  "category": "Electronics"
}
```

---

## Project Structure

```
src/main/java/com/technical/test/
├── component/          # JWT Authentication Filter
├── config/             # Security, Redis, and Application config
├── controller/         # REST Controllers (User, Product)
├── dto/
│   ├── request/        # Request DTOs
│   └── response/       # Response DTOs
├── entity/             # JPA Entities (User, Product)
├── handler/            # Global Exception Handler
├── repository/         # Spring Data JPA Repositories
├── service/            # Business Logic (UserService, ProductService, JwtService)
└── util/               # Response utility
```

---

## Postman Collection

A ready-to-use Postman collection is included:

```
Test assessment CCD.postman_collection.json
```

Import it into Postman to test all endpoints quickly.

---

## Database Schema

### `users` table

| Column | Type |
|---|---|
| id | BIGINT (PK, Auto) |
| username | VARCHAR |
| email | VARCHAR |
| password | VARCHAR (hashed) |
| role | VARCHAR (`ADMIN` / `USER`) |
| created_at | DATETIME |
| updated_at | DATETIME |

### `products` table

| Column | Type |
|---|---|
| id | BIGINT (PK, Auto) |
| name | VARCHAR |
| description | VARCHAR |
| price | DECIMAL |
| stock | INT |
| category | VARCHAR |
| created_at | DATETIME |
| updated_at | DATETIME |
