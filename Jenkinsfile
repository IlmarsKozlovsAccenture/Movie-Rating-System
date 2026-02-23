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
        sh 'mvn -B sonar:sonar -Dsonar.projectKey=movie-rating-system -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}'
      }
    }
  }
  
  post {
    always {
      archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
      junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
      publishHTML([
        reportDir: 'target/site/jacoco',
        reportFiles: 'index.html',
        reportName: 'Jacoco Coverage Report'
      ])
    }
    success {
      echo 'Pipeline completed successfully!'
    }
    failure {
      echo 'Pipeline failed!'
    }
  }
}
