# AI Agent Instructions for Toronto Open Data Provider Service

## Project Overview
This is a Spring Boot application that provides RESTful API services for Toronto Open Data. The project serves as a centralized data service provider interface for Toronto's open data resources, enabling easy access and integration with Toronto's public datasets.

### Core Objectives
- Provide RESTful APIs for accessing Toronto Open Data
- Enable efficient data retrieval and filtering
- Support various data formats (JSON, CSV, etc.)
- Implement caching and rate limiting for optimal performance

## Key Technologies
- Java 17
- Spring Boot 3.5.7
- Spring Data JPA
- H2 Database
- Lombok
- Maven 3.9.11

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/toronto/opendata/
│   │       └── dataportal/              # Root package
│   │           ├── config/              # Application configuration
│   │           ├── controller/          # REST controllers
│   │           ├── service/            # Business logic services
│   │           ├── repository/         # Data access layer
│   │           ├── model/             # Domain models
│   │           ├── dto/               # Data Transfer Objects
│   │           ├── exception/         # Custom exceptions
│   │           └── util/              # Utility classes
│   └── resources/
│       └── application.properties        # Spring Boot configuration
└── test/
    └── java/
        └── com/torontoopendata/
            └── dataserviceprovider/      # Test classes
```

## Development Workflow

### Building the Project
- Use the Maven wrapper for consistent builds across environments:
  ```
  ./mvnw clean install    # Unix
  mvnw.cmd clean install  # Windows
  ```

### Running Tests
- Run tests using Maven:
  ```
  ./mvnw test            # Unix
  mvnw.cmd test         # Windows
  ```

### Key Files
- `DataserviceproviderApplication.java`: Main Spring Boot application class
- `HelloController.java`: Example REST controller demonstrating endpoint implementation
- `pom.xml`: Project dependencies and build configuration

## Patterns and Conventions
1. **Package Structure**:
   - Controllers go in feature-specific packages (e.g., `com.torontoopendata.Hello`)
   - Core application code lives in `com.torontoopendata.dataserviceprovider`

2. **Dependency Injection**:
   - Use Spring's constructor injection pattern
   - Mark Spring components with appropriate annotations (`@Service`, `@Controller`, etc.)

3. **RESTful Endpoints**:
   - Controllers use `@RestController` annotation
   - Endpoints use appropriate HTTP method annotations (`@GetMapping`, `@PostMapping`, etc.)
   - Example: `HelloController.java` demonstrates REST endpoint implementation

## Common Tasks

### Adding a New REST Endpoint
1. Create a new controller class in appropriate package
2. Annotate with `@RestController`
3. Define endpoints using HTTP method annotations
4. Implement endpoint methods

### Adding Dependencies
1. Add new dependencies in `pom.xml`
2. Use Maven wrapper to update:
   ```
   ./mvnw dependency:resolve  # Unix
   mvnw.cmd dependency:resolve # Windows
   ```

## Integration Points
- H2 Database: In-memory database configured via Spring Boot
- Spring Data JPA: Used for database operations
- Spring Boot Web: Handles RESTful endpoints

## Testing Guidelines
1. Place tests in parallel package structure under `src/test`
2. Use `@SpringBootTest` for integration tests
3. Follow naming pattern: `*Tests.java` for test classes