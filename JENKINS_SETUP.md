# Jenkins Setup Guide

## Prerequisites

- Jenkins 2.300+ installed
- Maven 3.6+ configured
- Git installed
- SonarQube server (optional, for code quality)

## Step 1: Create Jenkins Pipeline Job

### 1.1 Create New Pipeline Job

1. **Jenkins Dashboard** → **New Item**
2. Enter job name: `movie-rating-system`
3. Select: **Pipeline**
4. Click: **OK**

### 1.2 Configure Pipeline

1. **General Tab**:
   - Description: "Movie Rating System - Maven Spring Boot App"
   - Check "GitHub project" (if using GitHub)
   - Project URL: Your repo URL

2. **Pipeline Tab**:
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: Your Git repo URL
   - Credentials: Configure GitHub credentials
   - Branch: `feature/movie-rating-system`
   - Script Path: `Jenkinsfile`

3. **Build Triggers** (optional):
   - GitHub hook trigger for GITScm polling
   - **OR** Poll SCM: `H * * * *` (every hour)

4. **Save**

## Step 2: Configure Credentials

### 2.1 Add Sonar Token

1. **Jenkins Dashboard** → **Manage Jenkins** → **Manage Credentials**
2. **Stores scoped to Jenkins** → **Jenkins** → **Global credentials**
3. **Add Credentials**:
   - Kind: **Secret text**
   - Secret: `<your-sonarqube-token>`
   - ID: `sonar-token`
   - Description: "SonarQube authentication token"
   - **Create**

### 2.2 Add GitHub Credentials (if needed)

1. **Add Credentials**:
   - Kind: **Username with password**
   - Username: Your GitHub username
   - Password: Your GitHub personal access token
   - ID: `github-credentials`
   - **Create**

## Step 3: Install Required Plugins

### 3.1 Required Plugins

In **Jenkins Dashboard** → **Manage Jenkins** → **Manage Plugins**:

**Available Tab**, search and install:
1. **Pipeline** - For Jenkinsfile support
2. **Git** - For Git integration
3. **Maven Integration** - For Maven builds
4. **SonarQube Scanner** - For code quality
5. **JUnit Plugin** - For test reports
6. **HTML Publisher Plugin** - For HTML reports
7. **Jacoco Plugin** - For coverage reports

**Install without restart** or **Restart Jenkins**

### 3.2 Configure SonarQube Server (Optional)

1. **Manage Jenkins** → **Configure System**
2. Scroll to **SonarQube Servers**
3. **Add SonarQube**:
   - Name: `SonarQube`
   - Server URL: `http://localhost:9000`
   - Server authentication token: (create in SonarQube first)
   - **Save**

## Step 4: Configure Maven

### 4.1 Jenkins Global Tool Configuration

1. **Manage Jenkins** → **Global Tool Configuration**
2. Scroll to **Maven**
3. **Add Maven**:
   - Name: `Maven 3.8`
   - MAVEN_HOME: `/usr/share/maven` (or your Maven path)
   - **Save**

### 4.2 Configure Java

1. Scroll to **JDK**
2. **Add JDK**:
   - Name: `Java 17`
   - JAVA_HOME: `/usr/lib/jvm/java-17-openjdk` (adjust for your system)
   - **Save**

## Step 5: Update Jenkinsfile

Ensure the `Jenkinsfile` in repo includes:

```groovy
pipeline {
  agent any
  
  environment {
    SONAR_TOKEN = credentials('sonar-token')
    SONAR_HOST_URL = 'http://localhost:9000'
  }
  
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn -B test'
      }
    }
    stage('Verify Coverage') {
      steps {
        sh 'mvn -B verify'
      }
    }
    stage('SonarQube Scan') {
      steps {
        sh 'mvn -B sonar:sonar'
      }
    }
  }
  
  post {
    always {
      archiveArtifacts artifacts: 'target/*.jar'
      junit testResults: 'target/surefire-reports/*.xml'
      publishHTML([
        reportDir: 'target/site/jacoco',
        reportFiles: 'index.html',
        reportName: 'Jacoco Coverage'
      ])
    }
  }
}
```

## Step 6: Run First Build

1. **Jenkins Dashboard** → Select `movie-rating-system`
2. **Build Now**
3. **Monitor Console Output**
4. Watch for:
   - Build success
   - Tests passing
   - Coverage > 75%
   - SonarQube upload (if enabled)

## Step 7: View Reports

After successful build:

1. **Console Output**: Full build log
2. **Test Results**: Test report
3. **Jacoco Coverage**: `target/site/jacoco/index.html`
4. **SonarQube Dashboard**: Link in build output

## Troubleshooting

### Build Fails at Maven

**Error**: "mvn: command not found"
```
Solution:
1. Configure Maven in Global Tool Configuration
2. Or specify full path in Jenkinsfile:
   sh '/usr/share/maven/bin/mvn clean package'
```

### Tests Fail

**Error**: "Tests failing locally but passed before"
```
Solution:
1. Check Java version: java -version
2. Clean build: mvn clean test
3. Check for hard-coded paths or localhost assumptions
```

### Coverage Below 75%

**Error**: "Jacoco check fails - coverage 60%"
```
Solution:
1. Run locally: mvn verify
2. Review jacoco report: target/site/jacoco/index.html
3. Add more tests for uncovered lines
```

### SonarQube Connection Failed

**Error**: "Cannot connect to SonarQube"
```
Solution:
1. Verify SonarQube is running: curl http://localhost:9000
2. Check credential token is valid
3. Review Jenkins logs: Manage Jenkins → System Log
```

### Git Clone Failed

**Error**: "Permission denied" or "Repository not found"
```
Solution:
1. Verify GitHub credentials configured
2. Check repository URL is correct
3. Test SSH key or PAT token
4. Verify branch exists: git branch -a
```

## Daily Use

### Trigger a Build

**Option 1: Manual**
- Jenkins Dashboard → Job → **Build Now**

**Option 2: GitHub Webhook**
1. GitHub repo → **Settings** → **Webhooks**
2. Add webhook: `http://jenkins-url/github-webhook/`
3. Trigger: Push events

**Option 3: SCM Polling**
- Already configured in job (Check every hour)

### View Reports

After each build:
1. **Test results**: Job → **Latest Build** → **Test Result**
2. **Coverage**: Job → **Latest Build** → **Jacoco Coverage Report**
3. **SonarQube**: Link in build description

### Track Metrics Over Time

1. **Jenkins Dashboard** → Job
2. **Trends**: See coverage trend graph
3. Compare builds for improvement/regression

## Best Practices

### Jenkins

- ✅ Use declarative pipeline (Jenkinsfile in repo)
- ✅ Separate build, test, verify stages
- ✅ Archive artifacts for traceability
- ✅ Publish test/coverage reports
- ✅ Set email notifications for failures
- ✅ Use credentials for secrets (never hardcode)

### Jenkinsfile

- ✅ Version control (commit Jenkinsfile)
- ✅ Fail fast at each stage
- ✅ Parallel stages when possible
- ✅ Clear stage names
- ✅ Post-build cleanup

### Build Times

Typical build times:
- Build: 30-45s
- Tests: 20-30s
- Coverage: 10-15s
- SonarQube: 30-60s
- **Total**: ~2-3 minutes

## Advanced Configuration

### Email Notifications

1. **Manage Jenkins** → **Configure System**
2. **Email Notification**:
   - SMTP server: `smtp.gmail.com`
   - Default user email suffix: `@example.com`
   - **Test by sending email**

3. Add to Jenkinsfile:
```groovy
post {
  failure {
    emailext(
      subject: "Build Failed: ${env.JOB_NAME}",
      body: "Build ${env.BUILD_NUMBER} failed",
      to: "team@example.com"
    )
  }
}
```

### Slack Notifications

1. Install **Slack Notification Plugin**
2. Create Slack webhook URL
3. Configure in Jenkins
4. Add to Jenkinsfile:
```groovy
post {
  failure {
    slackSend(color: 'danger', message: "Build failed")
  }
  success {
    slackSend(color: 'good', message: "Build passed")
  }
}
```

### Performance Optimization

```groovy
// Parallel stages
stages {
  stage('Build & Test') {
    parallel {
      stage('Build') {
        steps {
          sh 'mvn -B -DskipTests clean package'
        }
      }
      stage('Test') {
        steps {
          sh 'mvn -B test'
        }
      }
    }
  }
}
```

## Quick Checklist

Before deploying to production:

- [ ] Build passes consistently
- [ ] Tests pass (100% success rate)
- [ ] Coverage > 75%
- [ ] No SonarQube critical issues
- [ ] No security vulnerabilities
- [ ] Artifacts archive successfully
- [ ] Reports publish without errors
- [ ] Email/Slack notifications working
- [ ] Git webhooks configured
- [ ] Team has access to Jenkins job

## Support

For Jenkins issues:
- Check logs: **Manage Jenkins** → **System Log**
- Review build console output
- Test locally: Clone repo, run `mvn` commands
- Verify credentials and permissions
- Check plugin versions are compatible
