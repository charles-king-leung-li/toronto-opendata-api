# Toronto Open Data Core Service

> **Business logic and data processing microservice for Toronto Open Data**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

This is the **Core Business Logic Service** that handles all data processing, geospatial calculations, CSV operations, and business logic for the Toronto Open Data platform.

## 🏗️ Architecture

```
┌─────────────────────┐
│   API Gateway       │  Public-facing layer (Port 8080)
│   (Port 8080)       │
└──────────┬──────────┘
           │ Feign Client
┌──────────▼──────────────────────────────────┐
│      Core Service (Port 8081)               │  ← This Service
│  • Business logic processing               │
│  • Geospatial calculations (Haversine)     │
│  • CSV data operations                      │
│  • GeoJSON transformations                  │
│  • CKAN API integration                     │
│  • Exception handling                       │
└──────────┬──────────────────────────────────┘
           │
┌──────────▼──────────────────────────────────┐
│         Data Layer                          │
│  • CSV files (current)                      │
│  • H2 Database (configured)                 │
│  • Future: PostgreSQL                       │
└─────────────────────────────────────────────┘
```

## 🚀 Quick Start

### Prerequisites
- **Java 17+**
- **Maven 3.9.11+**
- Port 8081 available

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/charles-king-leung-li/toronto-opendata-core-service.git
cd toronto-opendata-core-service
```

2. **Start the service**
```bash
# Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# Or with installed Maven
mvn spring-boot:run
```

The Core Service will start on **http://localhost:8081**

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17 | Programming language |
| Spring Boot | 3.5.7 | Application framework |
| Spring Data JPA | 3.5.7 | Data access layer |
| H2 Database | Latest | In-memory database |
| Apache Commons CSV | 1.10.0 | CSV parsing |
| SpringDoc OpenAPI | 2.2.0 | API documentation |
| Lombok | Latest | Boilerplate reduction |

## Running the Application

### Prerequisites
- Java 17
- Maven 3.9+

### Start the Server
```bash
./mvnw spring-boot:run
```

The Core Service will start on **port 8081**.

## 📡 API Endpoints

**Note:** These endpoints are meant to be called by the API Gateway, not directly by clients.

### Cultural Hotspots
```http
GET /api/cultural-hotspots              # Get all hotspots from CSV
GET /api/cultural-hotspots/{id}         # Get hotspot by ID
GET /api/cultural-hotspots/search?name={query}  # Search by name
```

### Map & GeoJSON
```http
GET /api/map/points                     # Get all map points
GET /api/map/geojson                    # Get GeoJSON format  
GET /api/map/bounds?minLat={lat}&minLon={lon}&maxLat={lat}&maxLon={lon}
GET /api/map/nearby?lat={lat}&lon={lon}&radius={km}  # Haversine distance
```

### CSV Operations
```http
GET /api/csv/cultural-hotspots          # Stream CSV data
GET /api/csv/validate?filePath={path}   # Validate CSV file
GET /api/csv/headers?filePath={path}    # Get CSV headers
```

### API Documentation
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/v3/api-docs

## ⚙️ Configuration

### application.properties
```properties
# Server Configuration
server.port=8081

# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# OpenAPI Configuration
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### H2 Console
Access the database console at: **http://localhost:8081/h2-console**
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave blank)

## 📦 Project Structure
```
src/main/java/com/toronto/opendata/
├── core/                               # Core business logic
│   ├── CoreServiceApplication.java     # Main application
│   ├── controller/                     # REST endpoints
│   │   ├── CulturalHotSpotController.java
│   │   ├── MapController.java
│   │   └── CSVController.java
│   ├── service/                        # Business logic
│   │   ├── CulturalHotSpotService.java
│   │   ├── MapService.java             # Geospatial calculations
│   │   ├── CSVReaderService.java
│   │   └── CkanService.java
│   ├── model/                          # Domain models
│   │   ├── CulturalHotSpotModel.java
│   │   ├── MultiPointModel.java
│   │   └── CkanPackageResponse.java
│   ├── dto/                            # Data Transfer Objects
│   │   ├── MapPointDTO.java
│   │   ├── GeoJsonFeatureDTO.java
│   │   └── GeoJsonFeatureCollectionDTO.java
│   ├── util/                           # Utilities
│   │   └── GeometryParser.java         # GeoJSON parsing
│   └── config/
│       └── RestTemplateConfig.java
└── coreservice/                        # Additional services
    ├── config/
    │   └── OpenApiConfig.java          # Swagger configuration
    ├── dto/
    │   ├── ApiResponse.java            # Response wrapper
    │   └── CulturalHotSpotDTO.java
    ├── exception/                      # Exception handling
    │   ├── GlobalExceptionHandler.java
    │   ├── ApiError.java
    │   └── ResourceNotFoundException.java
    └── transformer/
        └── CulturalHotSpotTransformer.java  # DTO transformations

src/main/resources/
├── application.properties              # Configuration
└── data/                              # CSV data files
    ├── points-of-interest-05-11-2025.csv
    ├── Places of Worship - 4326.csv
    ├── tpl-branch-general-information-2023.json
    └── neighbourhood-profiles-2021-158-model.xlsx
```

## 🎯 Key Features

### Geospatial Calculations
- **Haversine distance** calculations for accurate geographic distances
- **Bounding box filtering** to find locations within map bounds
- **Radius-based searches** for nearby locations
- **GeoJSON transformations** for mapping libraries

### CSV Operations
- CSV file validation and header extraction
- Streaming CSV responses for large datasets
- Data parsing with error handling
- Support for multiple CSV formats

### Exception Handling
- Global exception handler with `@ControllerAdvice`
- Standardized error responses via `ApiError`
- Custom exceptions (`ResourceNotFoundException`)
- Validation error handling

### API Documentation
- Swagger/OpenAPI integration
- Interactive API testing via Swagger UI
- Automatic endpoint documentation
- Request/response schema generation

## 🧪 Testing

### Run Tests
```bash
./mvnw test
```

### Build for Production
```bash
./mvnw clean package
```

JAR file will be in `target/toronto-opendata-core-service-0.0.1-SNAPSHOT.jar`

### Manual Testing

Using `curl`:
```bash
# Get all cultural hotspots
curl http://localhost:8081/api/cultural-hotspots

# Get nearby locations (5km radius)
curl "http://localhost:8081/api/map/nearby?lat=43.65&lon=-79.38&radius=5"

# Get GeoJSON format
curl http://localhost:8081/api/map/geojson
```

## 📊 Data Sources

Currently using CSV files from `src/main/resources/data/`:

| File | Description | Format |
|------|-------------|--------|
| `points-of-interest-05-11-2025.csv` | Cultural hotspots data | CSV |
| `Places of Worship - 4326.csv` | Places of worship locations | CSV |
| `tpl-branch-general-information-2023.json` | Toronto library branches | JSON |
| `neighbourhood-profiles-2021-158-model.xlsx` | Neighborhood demographics | Excel |

### CSV Data Format (Cultural Hotspots)
```csv
_id,SiteName,Address,TourType,Description,ImageURL,geometry
1,"Art Gallery","123 Main St","Arts & Culture","...","{...}","{"coordinates":[[-79.48,43.68]],"type":"MultiPoint"}"
```

### Geometry Format (GeoJSON MultiPoint)
```json
{
  "coordinates": [[-79.48576, 43.685]],
  "type": "MultiPoint"
}
```

## 🛣️ Roadmap

- [ ] Migrate CSV data to PostgreSQL database
- [ ] Implement pagination for all endpoints
- [ ] Add full-text search functionality
- [ ] Implement caching with Redis
- [ ] Add comprehensive data validation
- [ ] Support CKAN API as additional data source
- [ ] Add database migrations (Flyway/Liquibase)
- [ ] Implement async processing for large datasets
- [ ] Add metrics and monitoring (Actuator)
- [ ] Containerize with Docker

## 🔗 Related Repositories

- **[API Gateway](https://github.com/charles-king-leung-li/toronto-opendata-api-gateway)** - Public-facing API Gateway service
- **[React Native Frontend](https://github.com/charles-king-leung-li/TorontoOpenDataReactFE)** - Mobile application

## 📄 License

MIT License - see [LICENSE](LICENSE) file

## 👤 Author

**Charles King Leung Li**
- GitHub: [@charles-king-leung-li](https://github.com/charles-king-leung-li)

## 🙏 Acknowledgments

- Data from [City of Toronto Open Data](https://open.toronto.ca/)
- Built with [Spring Boot](https://spring.io/projects/spring-boot)
- Geospatial calculations using Haversine formula
