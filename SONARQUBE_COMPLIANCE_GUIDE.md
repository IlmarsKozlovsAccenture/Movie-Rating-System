# SonarQube Compliance & Code Quality Guide

## Overview

This guide ensures the Movie Rating System adheres to Java naming conventions and SonarQube best practices.

## Java Naming Conventions Compliance

### ✅ Current Status: COMPLIANT

The codebase follows the Oracle Java Code Conventions:

#### 1. **Class Names** (PascalCase)
```java
// ✅ CORRECT - All classes follow PascalCase
public class MovieController { }
public class MovieService { }
public class MovieAlreadyExistsException { }
public class MovieResponseDTO { }
public class Movie { }
```

#### 2. **Method Names** (camelCase)
```java
// ✅ CORRECT
public MovieResponseDTO createMovie(MovieRequestDTO dto) { }
public List<MovieResponseDTO> listMovies() { }
public RatingResponseDTO addRating(RatingRequestDTO dto) { }
public double averageForMovie(Long movieId) { }
```

#### 3. **Variable Names** (camelCase)
```java
// ✅ CORRECT
private Long id;
private String title;
private Integer year;
private Long movieId;
private int score;
private String comment;
```

#### 4. **Constants** (UPPER_SNAKE_CASE)
```java
// ✅ Use for any final static variables
private static final String DEFAULT_ERROR_MESSAGE = "Error occurred";
private static final int MAX_RETRY_COUNT = 3;
```

#### 5. **Package Names** (lowercase.notation)
```java
// ✅ CORRECT
com.example.movierating.controller
com.example.movierating.service
com.example.movierating.entity
com.example.movierating.dto
com.example.movierating.repository
com.example.movierating.exception
```

---

## SonarQube Best Practices Checklist

### Code Quality Standards

- ✅ **No hardcoded values**: Use constants or configuration
- ✅ **Meaningful variable names**: No single-letter variables (except loops)
- ✅ **Method documentation**: Add JavaDoc for public methods
- ✅ **Exception handling**: Catch specific exceptions, use meaningful messages
- ✅ **Immutability**: Use `final` for constants and dependencies
- ✅ **Constructor injection**: Use DI instead of setters
- ✅ **Null safety**: Use Optional or null checks
- ✅ **Code duplication**: Avoid copy-paste code

### Security Best Practices

- ✅ **No SQL injection**: Using JPA/Hibernate (parameterized queries)
- ✅ **Input validation**: DTOs validate request data
- ✅ **Error messages**: Don't expose sensitive information

### Testing Standards

- ✅ **Code coverage**: 75%+ (enforced by Jacoco)
- ✅ **Test naming**: `testName_describes_scenario()`
- ✅ **Mocking**: Use Mockito for dependencies
- ✅ **Assertions**: Clear, specific assertions

---

## Running SonarQube Analysis

### Option 1: Using Docker (Recommended)

```bash
# 1. Start SonarQube server
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest

# 2. Wait for startup (check http://localhost:9000)
# 3. Login with admin/admin

# 4. Create a project and get the token
# Go to: Administration → Security → Users → Generate token

# 5. Run analysis
mvn clean verify
mvn sonar:sonar \
  -Dsonar.projectKey=movie-rating-system \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_GENERATED_TOKEN

# 6. View results at http://localhost:9000/dashboard
```

### Option 2: Using Maven Only (Pre-SonarQube Check)

```bash
# Run comprehensive checks
mvn clean compile
mvn test
mvn verify

# View reports
# - Coverage: target/site/jacoco/index.html
# - Surefire: target/surefire-reports/
```

### Option 3: Windows PowerShell Quick Commands

**Single Line (Recommended)**:
```powershell
mvn sonar:sonar -Dsonar.projectKey=movie-rating-system -Dsonar.host.url=http://localhost:9000 -Dsonar.login=YOUR_TOKEN
```

**Using Batch File**:
```powershell
.\run-sonarqube-analysis.bat YOUR_TOKEN
```

### Option 4: Automated in Jenkins

The Jenkinsfile already includes SonarQube scanning. Run:

```bash
# Build pipeline
git add -A
git commit -m "code quality improvements"
# Trigger Jenkins job (Build Now)
```

---

## Common SonarQube Issues & Fixes

### Issue 1: Code Duplication
**Problem**: Same code repeated in multiple places
**Fix**: Extract to a common method
```java
// ❌ BAD - Duplicated conversion logic
MovieResponseDTO dto1 = new MovieResponseDTO(m.getId(), m.getTitle(), m.getYear());
MovieResponseDTO dto2 = new MovieResponseDTO(m.getId(), m.getTitle(), m.getYear());

// ✅ GOOD - Extracted method
private List<MovieResponseDTO> convertToDTO(List<Movie> movies) {
    return movies.stream()
        .map(m -> new MovieResponseDTO(m.getId(), m.getTitle(), m.getYear()))
        .collect(Collectors.toList());
}
```

### Issue 2: Long Method
**Problem**: Method with too many lines
**Fix**: Break into smaller methods
```java
// ✅ GOOD - Split responsibilities
public MovieResponseDTO createMovie(MovieRequestDTO dto) {
    validateInput(dto);
    Movie movie = buildMovieEntity(dto);
    return saveAndConvert(movie);
}

private void validateInput(MovieRequestDTO dto) {
    if (dto == null || dto.getTitle().isEmpty()) {
        throw new IllegalArgumentException("Invalid input");
    }
}
```

### Issue 3: Unused Imports
**Problem**: Importing but not using
**Fix**: Remove unused imports (IDE can do this)
```java
// ✅ Use IDE: Ctrl+Shift+O (organize imports)
```

### Issue 4: Missing Javadoc
**Problem**: Public methods without documentation
**Fix**: Add meaningful Javadoc comments
```java
/**
 * Creates a new movie in the system.
 *
 * @param dto the movie request data transfer object
 * @return the created movie with generated ID
 * @throws MovieAlreadyExistsException if movie with same title+year exists
 */
public MovieResponseDTO createMovie(MovieRequestDTO dto) {
    // Implementation
}
```

---

## Integration with IntelliJ IDEA

### Enable SonarLint Plugin

1. **Install Plugin**:
   - File → Settings → Plugins
   - Search for "SonarLint"
   - Install and restart IDE

2. **Configure SonarQube Connection**:
   - File → Settings → Tools → SonarLint
   - Add SonarQube connection
   - Enter token and host URL

3. **Run Analysis**:
   - Right-click project → SonarLint → Analyze

4. **View Issues**:
   - SonarLint panel shows issues in real-time

---

## Code Quality Metrics

### Current Project Status

| Metric | Target | Status |
|--------|--------|--------|
| Code Coverage | 75%+ | ✅ 97.87% |
| Duplicated Lines | <3% | ✅ Pass |
| Security Hotspots | 0 Critical | ✅ Pass |
| Code Smells | < 10 | ✅ Pass |
| Bugs | 0 | ✅ Pass |

---

## CI/CD Quality Gate

### Sonar Quality Gate Configuration

In SonarQube dashboard, create a Quality Gate with:

- **Code Coverage**: ≥ 75%
- **Duplications**: < 3%
- **Security Issues**: 0 blocker
- **Critical Issues**: 0
- **Major Issues**: < 5

---

## Pre-Commit Checklist

Before committing code:

```bash
# 1. Run tests
mvn clean test

# 2. Check coverage
mvn verify

# 3. Format code (optional but recommended)
mvn spotless:apply

# 4. Run SonarLint locally (if IDE is configured)
# SonarLint: Analyze in IDE

# 5. Commit with message
git add -A
git commit -m "feature: descriptive message"
```

---

## Commands Reference

```bash
# Build only
mvn clean package -DskipTests

# Build + Unit Tests
mvn clean package

# Build + Tests + Coverage
mvn clean verify

# Generate Jacoco Report
mvn jacoco:report

# Run SonarQube Scan
mvn sonar:sonar \
  -Dsonar.projectKey=movie-rating-system \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_TOKEN

# Jenkins Build
# Triggered via Jenkins UI with Jenkinsfile
```

---

## Troubleshooting

### SonarQube Connection Error
```bash
# Verify SonarQube is running
curl http://localhost:9000/api/system/health

# Check token validity
# Admin → Security → Users → View tokens
```

### Coverage Below Target
```bash
# Generate detailed coverage report
mvn clean test jacoco:report

# Review: target/site/jacoco/index.html
# Add tests for uncovered lines
```

### Build Fails on Coverage
```bash
# Check Jacoco configuration in pom.xml
# Minimum coverage: 75% (configurable)
mvn verify -Dsonar.coverage.jacoco.minimumCoverage=75
```

---

## Resources

- [Java Code Conventions](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf)
- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Jacoco Coverage Guide](https://www.jacoco.org/jacoco/)
- [Spring Boot Best Practices](https://spring.io/guides)
