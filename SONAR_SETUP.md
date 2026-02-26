# Movie Rating System: SonarQube Setup Guide

## Quick Start Commands

### 1. Build and Run Tests with Coverage Report

```bash
# Complete build with tests and coverage check
mvn clean verify

# Generate Jacoco coverage report
mvn jacoco:report

# View coverage: open target/site/jacoco/index.html
```

### 2. Local Sonar Analysis (requires SonarQube server running)

```bash
# Scan with SonarQube (adjust host URL and token as needed)
mvn sonar:sonar \
  -Dsonar.projectKey=movie-rating-system \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_SONAR_TOKEN
```

### 3. Jenkins Build

```bash
# Create a Pipeline job in Jenkins
# Use the provided Jenkinsfile as the pipeline definition
# Configure credentials:
#   - sonar-token: Your SonarQube user token
#   - sonar host URL: http://localhost:9000 (or your SonarQube instance)
```

## Coverage Targets

- **Minimum Coverage**: 75% (enforced by Jacoco)
- **Coverage Types Measured**:
  - Line Coverage
  - Branch Coverage
  - Instruction Coverage

## Code Quality Metrics

The application includes:
- **Unit Tests**: 13 test cases
- **Integration Tests**: 4 test scenarios
- **Entity Tests**: 10 test cases
- **DTO Tests**: 8 test cases
- **Controller Tests**: 6 test cases

### Test Coverage Breakdown

| Component | Test Cases | Focus |
|-----------|-----------|-------|
| MovieService | 7 | Create, list, edge cases |
| RatingService | 7 | Add rating, averages, empty ratings |
| MovieController | 6 | REST endpoints with MockMvc |
| Integration | 4 | End-to-end workflows |
| Entities | 10 | Constructors, getters/setters, equals/hashCode |
| DTOs | 8 | Data transfer object behavior |
| **Total** | **42** | Complete coverage |

## SonarQube Installation (Local)

### Using Docker (Recommended)

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
# Access at: http://localhost:9000
# Default credentials: admin/admin
```

### Manual Installation

1. Download from https://www.sonarqube.org/downloads/
2. Extract and run: `bin/[os]/sonar.sh start`
3. Access at: http://localhost:9000

## Generated Reports

After running analysis:
- **Jacoco Report**: `target/site/jacoco/index.html`
- **SonarQube Dashboard**: `http://localhost:9000/dashboard`

## Key Metrics Monitored

- Code Coverage (target: 75%+)
- Cyclomatic Complexity
- Code Smells / Technical Debt
- Security Vulnerabilities
- Duplicated Code

## CI/CD Integration

The Jenkinsfile includes:
1. **Build Stage**: Compiles code
2. **Test Stage**: Runs unit and integration tests
3. **Verify Coverage**: Jacoco enforcement (75% minimum)
4. **SonarQube Scan**: Uploads metrics and generates report
5. **Post-Build**: Archives artifacts and publishes reports

## Troubleshooting

### Coverage Below 75%

1. Check Jacoco report: `mvn jacoco:report`
2. Review excluded classes in pom.xml
3. Add more test cases for uncovered branches
4. Run: `mvn test jacoco:report` to see detailed coverage

### SonarQube Connection Issues

1. Verify SonarQube is running: `curl http://localhost:9000`
2. Check token validity in Jenkins credentials
3. Verify network connectivity to SonarQube host
4. Review Jenkins logs for detailed error messages

## Demo Requirements

For the presentation, the following 2 key functions will be demonstrated:

### Function 1: Average Movie Rating Calculation
- **Endpoint**: `GET /api/movies/{id}/average`
- **Demonstrates**: RatingService calculation logic, data retrieval, float precision handling

### Function 2: List All Movies
- **Endpoint**: `GET /api/movies`
- **Demonstrates**: Service layer abstraction, DTO conversion, list handling

## Files Modified/Created

- `pom.xml`: Added Sonar Maven plugin and Jacoco configuration
- `Jenkinsfile`: Enhanced with SonarQube scan stage
- `sonar-project.properties`: SonarQube project configuration
- Test files: 10+ new test classes for comprehensive coverage
- This guide: `SONAR_SETUP.md`
