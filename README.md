# Movie Rating System

This is a small Spring Boot project (Maven) for the movie rating homework.

Quick commands:

```bash
mvn -B -DskipTests clean package    # build
mvn test                            # run tests
mvn verify                          # runs jacoco check
```

Jenkins: use the provided `Jenkinsfile` to create a job that runs the Maven stages.

Sonar: use `sonar-project.properties` with Sonar scanner or configure your CI to run Sonar.
# Movie-Rating-System