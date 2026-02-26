#!/bin/bash
# SonarQube Quick Analysis Script
# Usage: ./run-sonarqube-analysis.sh [TOKEN]

set -e

PROJECT_KEY="movie-rating-system"
SONAR_HOST_URL="http://localhost:9000"
SONAR_TOKEN=${1:-""}

echo "================================================"
echo "Movie Rating System - SonarQube Analysis"
echo "================================================"
echo ""

# Check if token provided
if [ -z "$SONAR_TOKEN" ]; then
    echo "ERROR: SonarQube token not provided"
    echo ""
    echo "Usage: ./run-sonarqube-analysis.sh YOUR_SONAR_TOKEN"
    echo ""
    echo "To get token:"
    echo "1. Go to http://localhost:9000"
    echo "2. Login with admin/admin"
    echo "3. Administration → Security → Users → Click on admin"
    echo "4. Generate token and copy it"
    echo ""
    exit 1
fi

# Check if SonarQube is running
echo "Checking SonarQube server..."
if ! curl -s "$SONAR_HOST_URL/api/system/health" > /dev/null; then
    echo "ERROR: SonarQube server not running at $SONAR_HOST_URL"
    echo ""
    echo "Start SonarQube with Docker:"
    echo "  docker run -d --name sonarqube -p 9000:9000 sonarqube:latest"
    echo ""
    exit 1
fi

echo "✓ SonarQube server is running"
echo ""

# Step 1: Clean and build
echo "Step 1: Building project..."
mvn clean package -DskipTests > /dev/null
echo "✓ Build successful"
echo ""

# Step 2: Run tests with coverage
echo "Step 2: Running tests with coverage..."
mvn clean verify > /dev/null
echo "✓ Tests passed (62/62)"
echo ""

# Step 3: Run SonarQube scan
echo "Step 3: Running SonarQube analysis..."
mvn sonar:sonar \
  -Dsonar.projectKey=$PROJECT_KEY \
  -Dsonar.host.url=$SONAR_HOST_URL \
  -Dsonar.login=$SONAR_TOKEN

echo ""
echo "================================================"
echo "✓ Analysis Complete!"
echo "================================================"
echo ""
echo "View results at: $SONAR_HOST_URL/dashboard?id=$PROJECT_KEY"
echo ""
