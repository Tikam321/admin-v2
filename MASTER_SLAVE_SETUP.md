# PostgreSQL Master-Slave Replication with Spring Boot

This document details the implementation of a **Master-Slave Database Architecture** for the `simple_admin_v2` project. This setup improves read scalability and provides high availability.

## 1. Architecture Overview

*   **Master Database (`db-master`):**
    *   **Role:** Handles all **WRITE** operations (`INSERT`, `UPDATE`, `DELETE`).
    *   **Port:** `5432`
    *   **Replication:** Acts as the primary source of truth. Streams data changes to the slave.
*   **Slave Database (`db-slave`):**
    *   **Role:** Handles **READ-ONLY** operations (`SELECT`).
    *   **Port:** `5433`
    *   **Replication:** Connects to the master as a `replicator` user and streams changes asynchronously.
*   **Spring Boot Application:**
    *   Uses a `RoutingDataSource` to automatically route queries based on the `@Transactional(readOnly = ...)` annotation.

---

## 2. Docker Configuration

The infrastructure is defined in `docker-compose.yml`.

### Master Service (`db-master`)
*   **Image:** `postgres:15`
*   **Configuration:** Mounts custom `postgresql.conf` and `pg_hba.conf` from `./conf/master/`.
*   **Initialization:** Runs `./init-scripts/create_replication_user.sql` to create the `replicator` user.
*   **Healthcheck:** Ensures the database is ready before the slave attempts to connect.

### Slave Service (`db-slave`)
*   **Image:** `postgres:15`
*   **Startup Script:**
    1.  Waits for the master to be healthy.
    2.  **Clears its own data directory** (`rm -rf /var/lib/postgresql/data/*`).
    3.  Runs `pg_basebackup` to clone the master's data and configuration.
    4.  Fixes file permissions (`chown postgres:postgres`).
    5.  Starts the server as the `postgres` user using `gosu`.

---

## 3. Spring Boot Implementation

The application logic resides in `src/main/java/com/tikam/simple_admin_v2/config/db/`.

### Key Components

1.  **`DataSourceType` (Enum):** Defines `MASTER` and `SLAVE`.
2.  **`RoutingDataSource`:** Extends `AbstractRoutingDataSource`.
    *   Checks `TransactionSynchronizationManager.isCurrentTransactionReadOnly()`.
    *   Returns `DataSourceType.SLAVE` if `readOnly = true`.
    *   Returns `DataSourceType.MASTER` otherwise.
3.  **`DataSourceConfig`:**
    *   Creates `masterDataSource` and `slaveDataSource` beans using `@ConfigurationProperties`.
    *   Configures the `routingDataSource` bean with the map of data sources.
    *   **Crucial:** The primary `dataSource` bean is a `LazyConnectionDataSourceProxy` wrapping the `routingDataSource`. This ensures the routing logic is executed for every transaction.

### Configuration (`application-km-dev.yml`)

```yaml
spring:
  datasource:
    master:
      url: jdbc:postgresql://localhost:5432/mydatabase
      username: postgres
      password: postgres
    slave:
      url: jdbc:postgresql://localhost:5433/mydatabase
      username: postgres
      password: postgres
```

---

## 4. How to Run

### Prerequisites
*   Docker and Docker Compose installed.
*   **Important:** Ensure the `./data` directory is deleted before a fresh start to allow initialization scripts to run.

### Steps
1.  **Clean up old data:**
    ```bash
    docker-compose down
    rm -rf data
    ```
2.  **Start the infrastructure:**
    ```bash
    docker-compose up -d
    ```
3.  **Run the Application:**
    *   **Profile:** `km-dev`
    *   **Command:**
        ```bash
        ./gradlew bootRun --args='--spring.profiles.active=km-dev'
        ```

---

## 5. Verification

### Verify Replication (Database Level)
1.  Connect to the Master:
    ```bash
    docker exec -it postgres-master psql -U postgres -d mydatabase
    ```
2.  Insert a row:
    ```sql
    CREATE TABLE test_rep (id serial primary key, name varchar);
    INSERT INTO test_rep (name) VALUES ('Replication Works!');
    ```
3.  Connect to the Slave:
    ```bash
    docker exec -it postgres-slave psql -U postgres -d mydatabase
    ```
4.  Check for the data:
    ```sql
    SELECT * FROM test_rep;
    -- You should see 'Replication Works!'
    ```

### Verify Routing (Application Level)
*   **Write Operation:** Call an endpoint without `@Transactional(readOnly = true)`.
    *   Logs should show: `Routing to MASTER`
*   **Read Operation:** Call an endpoint annotated with `@Transactional(readOnly = true)`.
    *   Logs should show: `Routing to SLAVE`

---

## 6. Troubleshooting

*   **`FATAL: password authentication failed for user "replicator"`**:
    *   **Cause:** The `create_replication_user.sql` script did not run because the `./data/master` directory was not empty.
    *   **Fix:** Run `docker-compose down`, delete the `./data` folder, and restart.

*   **`jdbcUrl is required` Error:**
    *   **Cause:** The `km-dev` profile is not active, or there is a typo in `application-km-dev.yml`.
    *   **Fix:** Ensure you are running with `--spring.profiles.active=km-dev`.
