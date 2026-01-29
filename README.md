# crm-server

Spring Boot + MySQL backend for CRM.

## Requirements
- Java 17+
- Maven 3.9+
- MySQL 8+

## Configure DB
Create a database (example):
```sql
CREATE DATABASE crm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Create `src/main/resources/application-local.yml` (this file is gitignored):
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/crm?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Singapore
    username: root
    password: <YOUR_PASSWORD>
```

## Run
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

API base: `http://localhost:8080/api`

## Auth
This backend uses **JWT Bearer** auth.

- Login: `POST /api/auth/login` → returns `{ token }`
- Me: `GET /api/auth/me`
- Pass `Authorization: Bearer <token>` to all `/api/**` endpoints.

Seeded admin user:
- username: `admin`
- password: `admin123`

Set a strong secret in env:
```bash
export JWT_SECRET="<your-long-secret>"
```

## Endpoints
- `POST /api/auth/login`
- `GET /api/auth/me`

- `GET/POST /api/customers`
- `GET/PUT/DELETE /api/customers/{id}`
- `GET/POST /api/orders`
- `GET/PUT/DELETE /api/orders/{id}`
- `GET/POST /api/visits`
- `GET/PUT/DELETE /api/visits/{id}`
- `GET/POST /api/finance-records`
- `GET/PUT/DELETE /api/finance-records/{id}`

- `GET/POST /api/employees` (ADMIN only)
- `GET/PUT/DELETE /api/employees/{id}` (ADMIN only)

CORS is enabled for local dev.
