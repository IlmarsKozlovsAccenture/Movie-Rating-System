pipeline {
  agent any
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
    stage('Verify Coverage & Package') {
      steps {
        sh 'mvn -B verify'
      }
    }
  }
  post {
    always {
      archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
    }
  }
}
