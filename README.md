# Toronto Open Data Provider Service (Monorepo)

> **Microservices architecture for Toronto Open Data access**

This monorepo contains the complete Toronto Open Data Provider Service, migrated from a monolithic architecture to microservices.

## 🏗️ Architecture

```
┌─────────────────────┐
│  React Native App   │  Frontend (Mobile/Web)
└──────────┬──────────┘
           │ HTTPS
┌──────────▼──────────────────────────────────┐
│      API Gateway (Port 8080)                │  Public-facing BFF layer
│  • Request routing                          │
│  • Response wrapping (ApiResponse)          │
│  • CORS handling                            │
│  • Google Maps API key management           │
└──────────┬──────────────────────────────────┘
           │ Feign Client (HTTP)
┌──────────▼──────────────────────────────────┐
│      Core Service (Port 8081)               │  Business logic layer
│  • Cultural Hotspots processing            │
│  • Geospatial calculations (Haversine)     │
│  • CSV data operations                      │
│  • GeoJSON transformations                  │
│  • CKAN API integration                     │
└──────────┬──────────────────────────────────┘
           │
┌──────────▼──────────────────────────────────┐
│         Data Layer                          │
│  • CSV files (current)                      │
│  • H2 Database (configured)                 │
│  • Future: PostgreSQL                       │
└─────────────────────────────────────────────┘
```

## 📁 Repository Structure

```
toronto-opendata-api/  (Monorepo Root - GitHub Repository)
├── toronto-opendata-api-gateway/     ← API Gateway Service
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── toronto-opendata-core-service/    ← Core Business Logic Service
│   ├── src/
│   ├── documentation/                ← Shared documentation
│   ├── pom.xml
│   └── README.md
└── README.md                         ← This file
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.9.11+
- Ports 8080 and 8081 available

### 1. Start Core Service

```powershell
cd toronto-opendata-core-service
.\mvnw spring-boot:run
```

Service starts on **http://localhost:8081**

### 2. Start API Gateway

```powershell
# In a new terminal
cd toronto-opendata-api-gateway
.\mvnw spring-boot:run
```

Gateway starts on **http://localhost:8080**

### 3. Access the Services

- **Gateway Swagger UI**: http://localhost:8080/swagger-ui.html
- **Core Service Swagger UI**: http://localhost:8081/swagger-ui.html
- **Gateway API**: http://localhost:8080/api/*
- **Core Service API**: http://localhost:8081/api/*

## 📡 API Endpoints

All public-facing requests go through the **API Gateway** (port 8080):

### Cultural Hotspots
```http
GET /api/cultural-hotspots              # Get all hotspots
GET /api/cultural-hotspots/{id}         # Get by ID
GET /api/cultural-hotspots/search?name=library
```

### Map & GeoJSON
```http
GET /api/map/points                     # Get map points
GET /api/map/geojson                    # Get GeoJSON format
GET /api/map/bounds?minLat=...          # Filter by bounds
GET /api/map/nearby?lat=43.65&lon=-79.38&radius=5
```

### Configuration
```http
GET /api/config/maps-key                # Get Google Maps API key
```

## 🔧 Configuration

### Environment Variables

**API Gateway** (`toronto-opendata-api-gateway/.env`):
```bash
GOOGLE_MAPS_API_KEY=your_key_here
CORE_SERVICE_URL=http://localhost:8081  # Feign client URL
```

**Core Service** (`toronto-opendata-core-service/.env`):
```bash
SERVER_PORT=8081
```

### Application Properties

Both services use `application.properties` for Spring Boot configuration.

## 🧪 Testing the Microservices

### Using PowerShell Scripts

See `toronto-opendata-core-service/documentation/MICROSERVICES_TESTING_GUIDE.md` for comprehensive testing instructions.

### Manual Testing

```powershell
# Test Core Service directly
curl http://localhost:8081/api/cultural-hotspots

# Test through Gateway
curl http://localhost:8080/api/cultural-hotspots

# Test map endpoints
curl "http://localhost:8080/api/map/nearby?lat=43.65&lon=-79.38&radius=5"
```

## 📚 Documentation

Comprehensive documentation is in `toronto-opendata-core-service/documentation/`:

- **[MICROSERVICES_QUICK_START.md](toronto-opendata-core-service/documentation/MICROSERVICES_QUICK_START.md)** - Getting started guide
- **[MICROSERVICES_TESTING_GUIDE.md](toronto-opendata-core-service/documentation/MICROSERVICES_TESTING_GUIDE.md)** - Complete testing guide
- **[MIGRATION_PROGRESS.md](toronto-opendata-core-service/documentation/MIGRATION_PROGRESS.md)** - Migration status and details
- **[API_QUICK_REFERENCE.md](toronto-opendata-core-service/documentation/API_QUICK_REFERENCE.md)** - API endpoints reference

## 🏗️ Service Details

### API Gateway (Port 8080)
**Technology Stack:**
- Spring Boot 3.5.7
- Spring Cloud OpenFeign (for inter-service communication)
- Swagger/OpenAPI

**Responsibilities:**
- Public-facing API endpoints
- Request routing to Core Service
- Response wrapping with `ApiResponse<T>`
- CORS configuration
- API key management
- Static resource serving (map.html)

### Core Service (Port 8081)
**Technology Stack:**
- Spring Boot 3.5.7
- Spring Data JPA
- H2 Database
- Apache Commons CSV
- Swagger/OpenAPI

**Responsibilities:**
- Business logic processing
- Geospatial calculations (Haversine distance)
- CSV data parsing and operations
- GeoJSON transformation
- CKAN API integration
- Data validation and transformation

## � Migration Status

✅ **Complete** - Migrated from monolith to microservices architecture

| Component | Status | Location |
|-----------|--------|----------|
| Cultural Hotspots API | ✅ Migrated | Core Service |
| Map & GeoJSON API | ✅ Migrated | Core Service |
| CSV Operations | ✅ Migrated | Core Service |
| CKAN Integration | ✅ Migrated | Core Service |
| API Key Config | ✅ Migrated | Gateway |
| Exception Handling | ✅ Migrated | Core Service |
| OpenAPI Documentation | ✅ Migrated | Both Services |
| Data Files | ✅ Migrated | Core Service |
| Documentation | ✅ Migrated | Core Service |

## 🛠️ Development Workflow

### Adding New Features

1. **Determine service ownership:**
   - Public-facing/BFF logic → API Gateway
   - Business logic/data processing → Core Service

2. **Create feature branch:**
   ```bash
   git checkout -b feature/new-feature
   ```

3. **Make changes in appropriate service:**
   ```bash
   cd toronto-opendata-core-service  # or toronto-opendata-api-gateway
   # Make your changes
   ```

4. **Test locally:**
   ```bash
   # Start both services
   # Run tests
   .\mvnw test
   ```

5. **Commit and push:**
   ```bash
   git add .
   git commit -m "feat: add new feature to core service"
   git push origin feature/new-feature
   ```

### Building for Production

```powershell
# Build both services
cd toronto-opendata-core-service
.\mvnw clean package

cd ../toronto-opendata-api-gateway
.\mvnw clean package

# JARs will be in each service's target/ directory
```

## 🔐 Security Considerations

- API Gateway handles CORS and public exposure
- Core Service should not be directly accessible from internet
- Use environment variables for sensitive configuration
- `.env` files are gitignored

## 🚀 Deployment

### Development
- Run both services locally on different ports
- Gateway on 8080, Core on 8081

### Production (Future)
- Deploy Core Service internally (not public-facing)
- Deploy Gateway with public access
- Update `CORE_SERVICE_URL` in Gateway config
- Consider: Docker, Kubernetes, Azure App Service, AWS ECS

## 🤝 Contributing

1. Create feature branch from `main`
2. Make changes in appropriate service
3. Test thoroughly (both services)
4. Commit with conventional commit messages
5. Create Pull Request

## 📄 License

Data from [Toronto Open Data](https://open.toronto.ca/)  
License: Open Government Licence – Toronto

## 👤 Author

**Charles King Leung Li**
- GitHub: [@charles-king-leung-li](https://github.com/charles-king-leung-li)

---

**Need Help?**
- See service-specific READMEs in each folder
- Check documentation in `toronto-opendata-core-service/documentation/`
- Review migration guide: `MIGRATION_PROGRESS.md`
