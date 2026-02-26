# Movie Rating System

A Spring Boot REST API application for managing movies and their ratings. Built with Maven, featuring comprehensive unit and integration tests, Jacoco code coverage tracking, and SonarQube integration.

## Project Overview

**Technology Stack**:
- Java 17
- Spring Boot 2.7.12
- Spring Data JPA
- H2 Database (in-memory)
- Maven 3.x
- JUnit 5 & Mockito (testing)
- Jacoco (code coverage)
- SonarQube (code quality)
- Jenkins (CI/CD)

**Key Features**:
- REST API for movie and rating management
- Service layer with business logic (MovieService, RatingService)
- DTO pattern for data transfer
- Comprehensive test suite (42+ test cases)
- 75%+ code coverage with Jacoco
- SonarQube integration for code quality
- Jenkins pipeline for CI/CD

## Quick Start

### Prerequisites
- Java 17+ installed
- Maven 3.6+

### Build

```bash
# Build the project (skip tests for speed)
mvn clean package -DskipTests

# Build with tests
mvn clean package
```

### Run

**Option 1: Using Maven**
```bash
mvn spring-boot:run
```

**Option 2: Using JAR directly**
```bash
java -jar target/movie-rating-system-0.0.1-SNAPSHOT.jar
```

The app runs on http://localhost:8080

### Test

```bash
# Run all tests
mvn test

# Run with coverage verification (minimum 75%)
mvn verify

# Generate coverage report
mvn jacoco:report
# View at: target/site/jacoco/index.html
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/movies` | Create a new movie |
| `GET` | `/api/movies` | List all movies |
| `POST` | `/api/ratings` | Add a rating to a movie |
| `GET` | `/api/movies/{id}/average` | Get average rating for a movie |

### Example Requests (using Postman or curl)

**Create a Movie**
```json
POST /api/movies
Content-Type: application/json

{
  "title": "Inception",
  "year": 2010
}
```

**Add a Rating**
```json
POST /api/ratings
Content-Type: application/json

{
  "movieId": 1,
  "score": 5,
  "comment": "Excellent movie!"
}
```

**Get Movie Average Rating**
```
GET /api/movies/1/average
```

**List All Movies**
```
GET /api/movies
```

## Project Structure

```
src/
├── main/java/com/example/movierating/
│   ├── MovieRatingApplication.java      # Main entry point
│   ├── controller/
│   │   └── MovieController.java         # REST endpoints
│   ├── service/
│   │   ├── MovieService.java            # Movie business logic
│   │   └── RatingService.java           # Rating calculation logic
│   ├── entity/
│   │   ├── Movie.java                   # JPA entity
│   │   └── Rating.java                  # JPA entity
│   ├── dto/
│   │   ├── MovieDTO.java                # Data transfer object
│   │   └── RatingDTO.java               # Data transfer object
│   ├── repository/
│   │   ├── MovieRepository.java         # JPA repository
│   │   └── RatingRepository.java        # JPA repository
│   └── resources/
│       └── application.properties       # Configuration
└── test/java/com/example/movierating/
    ├── service/                         # Unit tests
    ├── controller/                      # Controller tests (MockMvc)
    ├── entity/                          # Entity tests
    ├── dto/                             # DTO tests
    └── integration/                     # Integration tests
```

## Test Coverage

**42+ Test Cases** covering:

| Category | Count | Coverage Focus |
|----------|-------|-----------------|
| Service Unit Tests | 14 | Business logic, edge cases |
| Controller Tests | 6 | REST endpoints, HTTP status |
| Entity Tests | 10 | Constructors, equals/hashCode |
| DTO Tests | 8 | Data serialization |
| Integration Tests | 4 | End-to-end workflows |

**Target**: 75%+ instruction coverage (enforced by Jacoco)

## Code Quality

### Jacoco Code Coverage
```bash
mvn verify  # Enforces 75% minimum
mvn jacoco:report  # Generates HTML report
```

### SonarQube Analysis

See [SONAR_SETUP.md](SONAR_SETUP.md) for detailed setup.

**Quick Scan**:
```bash
mvn sonar:sonar \
  -Dsonar.projectKey=movie-rating-system \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_TOKEN
```

## Jenkins Pipeline

The project includes a `Jenkinsfile` for CI/CD:

**Stages**:
1. Build - Compiles code
2. Test - Runs unit/integration tests
3. Verify Coverage - Jacoco 75% check
4. SonarQube Scan - Code quality analysis
5. Post-Build - Archives and publishes reports

**Setup**:
1. Create a Pipeline job in Jenkins
2. Point to repository with this `Jenkinsfile`
3. Configure credentials: `sonar-token`
4. Run pipeline

## Demo Functions (2 Key Features)

### 1. Calculate Average Movie Rating
- **Endpoint**: `GET /api/movies/{id}/average`
- **Shows**: Service layer, data aggregation, floating-point calculations
- **Test Case**: `RatingServiceTest.averageForMovie_returnsAverage()`

### 2. List All Movies
- **Endpoint**: `GET /api/movies`
- **Shows**: Service layer, DTO conversion, list handling
- **Test Case**: `MovieServiceTest.listMovies_multipleMovies()`

## Compliance

✅ **Java Naming Conventions**: Package names, class names, method names follow Java standards  
✅ **Best Practices**: Dependency injection, service layer, DTO pattern  
✅ **Test Coverage**: 75%+ with unit and integration tests  
✅ **Code Quality**: SonarQube configured for CI/CD  
✅ **CI/CD**: Jenkins pipeline with Sonar integration  

## Troubleshooting

### Tests Fail to Run
```bash
# Ensure all dependencies are downloaded
mvn clean install

# Run specific test
mvn test -Dtest=MovieServiceTest
```

### Coverage Below 75%
```bash
# Generate detailed report
mvn clean test jacoco:report

# Review: target/site/jacoco/index.html
```

### Build Issues
```bash
# Force rebuild with clean
mvn clean package

# Check Java version
java -version  # Must be 17+
```

## Next Steps

1. **Configure SonarQube**: See [SONAR_SETUP.md](SONAR_SETUP.md)
2. **Setup Jenkins**: Create pipeline job with `Jenkinsfile`
3. **Run Tests**: `mvn clean verify`
4. **Review Coverage**: Open `target/site/jacoco/index.html` in browser
5. **Deploy**: Package and run as JAR or on application server

## Files Overview

| File | Purpose |
|------|---------|
| `pom.xml` | Maven build configuration with Jacoco & Sonar plugins |
| `Jenkinsfile` | CI/CD pipeline definition |
| `sonar-project.properties` | SonarQube project configuration |
| `SONAR_SETUP.md` | Detailed SonarQube setup guide |
| `README.md` | This file |

## License

This project is provided for educational purposes.
# Movie-Rating-System