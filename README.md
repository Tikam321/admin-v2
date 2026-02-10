# Simple Admin V2 - Backend

A robust, scalable Admin Panel backend built with **Spring Boot 3** and **Java 17**. This project demonstrates modern enterprise architecture patterns, including custom JWT authentication, advanced search with Elasticsearch, and dynamic query generation with QueryDSL.

## 🚀 Tech Stack

*   **Core:** Java 17, Spring Boot 3.3.7
*   **Database:** H2 (In-Memory) for rapid development
*   **Search Engine:** Elasticsearch (Spring Data Elasticsearch)
*   **ORM / Querying:** Spring Data JPA, **QueryDSL 5.0**
*   **Security:** Custom **JWT** Implementation (No Spring Security dependency)
*   **Build Tool:** Gradle (with Version Catalog `libs.versions.toml`)
*   **Utilities:** Lombok, Jackson (JSR310)

---

## 🏗 Architecture & Design Patterns

### 1. Authentication & Security
We implemented a **custom security framework** from scratch to demonstrate low-level understanding of stateless authentication.
*   **Login:** `POST /api/auth/login` verifies credentials (SHA-256 salted hash) and issues a **JWT**.
*   **Refresh Token:** Implemented a secure Refresh Token flow (`POST /api/auth/refresh-token`) with database storage and expiration handling.
*   **Filter Layer:** `AuthFilter` intercepts requests, validates JWTs, and populates a `ThreadLocal` context.
*   **Context Propagation:** `UserContext` allows any service to access the currently authenticated user's info (`userId`, `role`) without passing parameters.

### 2. Advanced Search (Elasticsearch)
*   **Policy History:** Audit logs are stored and searched via Elasticsearch.
*   **Hybrid Querying:** Uses `NativeQuery` to combine Boolean logic, Range filters (Dates), and Full-text search.
*   **Type Safety:** Custom converters handle `java.util.Date` vs `LocalDate` mapping issues.

### 3. Dynamic SQL (QueryDSL)
*   Used for complex filtering in `ThemeManagement`.
*   Allows type-safe construction of dynamic queries (e.g., `themeVersion.themeManagementId.eq(id)`).
*   Optimized pagination logic (Offset vs Count).

---

## 🔑 Key Features

### 1. Theme Management
*   Manage UI themes with version control.
*   **Entities:** `ThemeManagement` -> `ThemeVersion` -> `ThemeFileMetadata`.
*   **API:** Fetch paginated lists of themes and their specific versions.

### 2. Policy History (Audit Logs)
*   Tracks changes to system policies (WHO changed WHAT and WHEN).
*   Searchable by date range, keyword, and user.

### 3. User & Admin Management
*   **Separation of Concerns:**
    *   `usr` table: Global User Identity (Email, Password Hash).
    *   `admin_user` table: Admin-specific roles and permissions.

---

## 🛠 Setup & Running

### Prerequisites
*   Java 17+
*   Gradle (Wrapper included)
*   Elasticsearch (Running on localhost:9200)

### Installation
1.  Clone the repository.
2.  Build the project:
    ```bash
    ./gradlew clean build
    ```
3.  Run the application:
    ```bash
    ./gradlew bootRun
    ```

### Default Credentials (Seeded in `data.sql`)
The application auto-seeds 10,000 users for testing.

*   **Login URL:** `POST http://localhost:8080/api/auth/login`
*   **Email:** `user1@test.com`
*   **Password:** `password123`

---

## 📚 Documentation Links
*   [Authentication Flow (Diagrams)](AUTH_FLOW.md)
*   [Request Lifecycle (Filter vs Interceptor)](REQUEST_FLOW.md)
*   [JWT Internals Explained](JWT_EXPLAINED.md)

---

## 📂 Project Structure
```
src/main/java/com/tikam/simple_admin_v2
├── config/          # App Config (Filters, Elasticsearch)
├── controller/      # REST Controllers
├── document/        # Elasticsearch Documents
├── dto/             # Data Transfer Objects
├── entity/          # JPA Entities
├── repository/      # JPA & Elastic Repositories
├── service/         # Business Logic
└── util/            # Utilities (JwtUtils, PasswordUtils)
```
