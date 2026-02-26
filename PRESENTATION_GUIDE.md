# Movie Rating System - Presentation Guide

## Overview
This document guides you through demonstrating the Movie Rating System, highlighting the two key working functions and the SonarQube code quality report.

---

## Demo Structure (15-20 minutes)

### Part 1: Introduction (2 minutes)
- **Project Name**: Movie Rating System
- **Technology**: Spring Boot 2.7, Maven, H2, JPA
- **Purpose**: REST API for managing movies and user ratings
- **Key Metrics**:
  - 42+ Test Cases
  - 75%+ Code Coverage
  - SonarQube Integration
  - Jenkins CI/CD Pipeline

### Part 2: Live Demo - Function 1 (5 minutes)
**Function: Calculate Average Movie Rating**

**Endpoint**: `GET /api/movies/{id}/average`

#### Step-by-step Demo:
1. **Start the application**
   ```bash
   mvn spring-boot:run
   # Wait for "Tomcat started on port 8080" message
   ```

2. **Create a movie** (using Postman)
   - Request: `POST http://localhost:8080/api/movies`
   - Headers: `Content-Type: application/json`
   - Body:
     ```json
     {
       "title": "Inception",
       "year": 2010
     }
     ```
   - Response: Returns movie with ID (e.g., `{"id": 1, "title": "Inception", "year": 2010}`)
   - **Note the movie ID for next steps**

3. **Add multiple ratings to the movie**
   - Request: `POST http://localhost:8080/api/ratings`
   - Headers: `Content-Type: application/json`
   - Body 1: `{"movieId": 1, "score": 5, "comment": "Amazing!"}`
   - Body 2: `{"movieId": 1, "score": 4, "comment": "Very good"}`
   - Body 3: `{"movieId": 1, "score": 3, "comment": "Good"}`
   - Each returns a rating confirmation

4. **Calculate average**
   - Request: `GET http://localhost:8080/api/movies/1/average`
   - Response: `4.0` (average of 5, 4, 3 = 12/3 = 4.0)
   - **Demonstrates**: Service layer logic, data aggregation, calculation accuracy

#### Code Highlight:
Show in IDE: `RatingService.averageForMovie()` method
- Shows stream API usage
- Shows null/empty handling
- Demonstrates business logic implementation

### Part 3: Live Demo - Function 2 (5 minutes)
**Function: List All Movies**

**Endpoint**: `GET /api/movies`

#### Step-by-step Demo:
1. **List all movies** (from earlier creation + new ones)
   - Request: `GET http://localhost:8080/api/movies`
   - Response: Array of all movies with IDs and details
   - Shows all previously created movies

2. **Create additional movies** (quick):
   - `POST /api/movies`: `{"title": "The Matrix", "year": 1999}`
   - `POST /api/movies`: `{"title": "Interstellar", "year": 2014}`

3. **Refresh list**
   - Request: `GET http://localhost:8080/api/movies`
   - Response: Shows all 3+ movies
   - **Demonstrates**: List handling, DTO conversion, Spring Data JPA

#### Code Highlight:
Show in IDE: `MovieService.listMovies()` method
- Shows repository abstraction
- Shows DTO conversion pattern
- Demonstrates service layer usage

### Part 4: SonarQube Code Quality Report (5 minutes)

#### Step 1: Generate Reports
```bash
# Terminal window
mvn clean verify        # Generate Jacoco report
mvn jacoco:report       # Ensure HTML report
```

#### Step 2: Show Jacoco Coverage Report
1. **Open in browser**: `target/site/jacoco/index.html`
2. **Highlight**:
   - **Overall Coverage**: Point to 75%+ instruction coverage
   - **By Package**: Show breakdown by package
   - **Covered Classes**: MovieService, RatingService, etc.
   - **Coverage Details**: Show line/branch coverage for key services

#### Step 3: SonarQube Upload (Optional - if SonarQube running)
```bash
# Terminal
mvn sonar:sonar \
  -Dsonar.projectKey=movie-rating-system \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_TOKEN
```

Open SonarQube Dashboard: `http://localhost:9000`

**Show**:
- Code Coverage Percentage
- Technical Debt
- Code Smells
- Security Issues
- Duplicated Code

### Part 5: Test Coverage Explanation (3 minutes)

**Show Test Structure**:
```
src/test/java/com/example/movierating/
├── service/
│   ├── MovieServiceTest.java        (7 cases)
│   └── RatingServiceTest.java       (7 cases)
├── controller/
│   └── MovieControllerTest.java     (6 cases)
├── entity/
│   ├── MovieTest.java               (5 cases)
│   └── RatingTest.java              (5 cases)
├── dto/
│   ├── MovieDTOTest.java            (4 cases)
│   └── RatingDTOTest.java           (4 cases)
└── integration/
    └── MovieControllerIntegrationTest.java (4 cases)
```

**Emphasize**:
- **42+ Total Test Cases**
- **Unit Tests**: Service layer logic with Mockito
- **Integration Tests**: End-to-end workflows
- **Entity Tests**: Object equality and state
- **DTO Tests**: Data serialization
- **Controller Tests**: REST endpoint behavior (MockMvc)

---

## Jenkins Pipeline Overview (2 minutes)

**Show in IDE or browser**: `Jenkinsfile`

**Stages Explained**:
1. **Build**: `mvn clean package`
2. **Test**: `mvn test` (unit & integration)
3. **Verify Coverage**: `mvn verify` (Jacoco 75% check)
4. **SonarQube Scan**: Quality analysis
5. **Post-Build**: Archive artifacts, publish reports

---

## Key Discussion Points

### Architecture
- **Service Layer**: MovieService, RatingService
- **DTO Pattern**: Data transfer objects separate from entities
- **Repository Abstraction**: Spring Data JPA
- **REST Controller**: Clean API design

### Testing Strategy
- **Unit Tests**: Service logic with mocks
- **Integration Tests**: Full stack workflows
- **Coverage Tracking**: Jacoco 75% minimum
- **Test Organization**: By layer (service, controller, entity)

### Code Quality
- **75%+ Coverage**: Well-tested codebase
- **SonarQube**: Automated code quality checks
- **Jenkins**: CI/CD pipeline automation
- **Naming Conventions**: Java standards compliance

---

## Troubleshooting During Demo

### If app won't start:
1. Check port 8080 is free: `netstat -an | grep 8080`
2. Restart application
3. Alternative: Run JAR directly: `java -jar target/movie-rating-system-0.0.1-SNAPSHOT.jar`

### If Postman requests fail:
1. Verify app is running (check console)
2. Check Content-Type headers
3. Verify JSON format
4. Check movie IDs match

### If coverage report missing:
1. Run: `mvn clean verify`
2. Report should appear: `target/site/jacoco/index.html`
3. Regenerate: `mvn jacoco:report`

---

## Demo Checklist

Before presentation:
- [ ] Clone/pull latest code from `feature/movie-rating-system` branch
- [ ] Run `mvn clean install` to download dependencies
- [ ] Build project: `mvn clean package -DskipTests`
- [ ] Generate coverage report: `mvn verify`
- [ ] Open Postman (or curl in terminal)
- [ ] Prepare browser tabs for:
  - [ ] Jacoco report (target/site/jacoco/index.html)
  - [ ] SonarQube dashboard (optional)
- [ ] Test one API call before presenting
- [ ] Have IDE ready to show source code
- [ ] Document any issues for Q&A

---

## Slide/Presentation Outline

1. **Title Slide**: Project name, date, presenter
2. **Overview**: Technology, objectives, metrics
3. **Architecture**: Layer diagram (Controller → Service → Repository)
4. **Demo 1**: Average rating calculation (live)
5. **Demo 2**: List movies (live)
6. **Test Coverage**: 42 test cases, 75%+ coverage
7. **Code Quality**: SonarQube metrics screenshot
8. **Jenkins Pipeline**: 5-stage CI/CD
9. **Key Achievements**: Compliance checklist
10. **Q&A**: Ready for questions

---

## What to Highlight

✅ **Functionality**: Two working REST endpoints
✅ **Testing**: 42+ comprehensive test cases
✅ **Coverage**: 75%+ with Jacoco enforcement
✅ **Quality**: SonarQube integrated into CI/CD
✅ **Best Practices**: Service layer, DTOs, JPA
✅ **Professionalism**: Clean code, documentation
✅ **Automation**: Jenkins pipeline for CI/CD
✅ **Learning**: Demonstrates Spring, testing, and DevOps

---

## Questions to Prepare For

1. **Why 75% coverage?** - Balances speed with quality, covers critical paths
2. **Why DTO pattern?** - Separates API contract from persistence layer
3. **Why services?** - Encapsulates business logic, testable, reusable
4. **Why MockMvc?** - Tests REST endpoints without starting server
5. **Why SonarQube?** - Automated code quality, consistency

---

## Time Breakdown

| Section | Time | Notes |
|---------|------|-------|
| Intro | 2 min | Quick project overview |
| Demo 1 | 5 min | Create movie, add ratings, show average |
| Demo 2 | 5 min | List movies, verify payload |
| Coverage | 3 min | Show Jacoco report highlights |
| SonarQube | 3 min | Dashboard walkthrough |
| Q&A | 2 min | Questions from audience |
| **Total** | **20 min** | Fits standard presentation slot |

---

Good luck with your presentation! 🚀
