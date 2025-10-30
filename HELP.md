# User Aggregation Service

A test task that aggregates user data from multiple databases (PostgreSQL and MySQL) into a unified API endpoint.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [API Documentation](#-api-documentation)
- [Running Tests](#-running-tests)

## Overview
This service provides a single REST API endpoint that queries multiple databases, aggregates results, and returns unified user data. It supports:

- **Multiple data sources**: Connect to unlimited number of databases
- **Several databases**: PostgreSQL, MySQL
- **Flexible field mapping**: Map different column names to unified response format
- **Data deduplication**: Automatically removes duplicate users based on ID
- **Dynamic filtering**: Filter results by username, name, or surname
- **Declarative configuration**: Easy YAML-based data source configuration

## Features

-  **Dynamic filtering** - Filter by username, name, surname (or combination)
-  **SQL injection protection** - Parameterized queries for security
-  **Connection pooling** - HikariCP for optimal performance
-  **OpenAPI documentation** - Interactive Swagger UI
-  **Integration tests** - Comprehensive tests with Testcontainers

##  Prerequisites

- **Java 21** or higher
- **Docker** and **Docker Compose** (for databases)
- **IntelliJ IDEA** (recommended) or any Java IDE
- **Gradle** (included via wrapper)

##  Quick Start

### 1. Clone the Repository

```bash 
git clone <repository-url> cd comparus
```

### 2. Start Database Services

```bash 
docker-compose up -d
```

This will start:
- PostgreSQL on port `5432`
- MySQL on port `3306`
- Both databases will be auto-initialized with test data

### 3. Run the Application

1. Open the project in IntelliJ IDEA
2. Locate `ComparusApplication.java`
3. Click the green ▶️ button next to the `main()` method
4. Or use keyboard shortcut: `Shift + F10`

### 4. Verify Application is Running

Open your browser and navigate to:

- **Swagger UI**: http://localhost:8080/swagger-ui.html

##  API Documentation

### Endpoint: `GET /users`

Retrieve aggregated users from all configured data sources with optional filtering.

#### Query Parameters (all optional)

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `username` | String | Filter by username | `user-1` |
| `name` | String | Filter by first name | `Maria` |
| `surname` | String | Filter by last name | `Ivanova` |

### API Examples

#### Get All Users

```bash 
curl [http://localhost:8080/users](http://localhost:8080/users)
```

#### Filter by Username

```bash 
curl "[http://localhost:8080/users?username=user-1](http://localhost:8080/users?username=user-1)"
```

#### Filter by Name

```bash 
curl "[http://localhost:8080/users?name=Maria](http://localhost:8080/users?name=Maria)"
```

#### Filter by Surname

```bash 
curl "[http://localhost:8080/users?surname=Userenko](http://localhost:8080/users?surname=Userenko)"
```

#### Multiple Filters (AND logic)

```bash 
curl "[http://localhost:8080/users?name=User&surname=Userenko](http://localhost:8080/users?name=User&surname=Userenko)"
```

##  Running Tests

The project includes comprehensive integration tests using Testcontainers.

### Prerequisites for Tests

- Docker must be running
- Internet connection (for pulling Docker images on first run)

### Run Tests in IntelliJ IDEA

#### All Tests
1. Right-click on `src/test/java` directory
2. Select "Run 'All Tests'"
