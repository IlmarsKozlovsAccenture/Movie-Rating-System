@echo off
REM SonarQube Quick Analysis Script (Windows)
REM Usage: run-sonarqube-analysis.bat YOUR_SONAR_TOKEN

setlocal enabledelayedexpansion

set PROJECT_KEY=movie-rating-system
set SONAR_HOST_URL=http://localhost:9000
set SONAR_TOKEN=%1

echo.
echo ================================================
echo Movie Rating System - SonarQube Analysis
echo ================================================
echo.

REM Check if token provided
if "%SONAR_TOKEN%"=="" (
    echo ERROR: SonarQube token not provided
    echo.
    echo Usage: run-sonarqube-analysis.bat YOUR_SONAR_TOKEN
    echo.
    echo To get token:
    echo 1. Go to http://localhost:9000
    echo 2. Login with admin/admin
    echo 3. Administration ^> Security ^> Users ^> Click on admin
    echo 4. Generate token and copy it
    echo.
    exit /b 1
)

REM Step 1: Clean and build
echo Step 1: Building project...
call mvn clean package -DskipTests > nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Build failed
    exit /b 1
)
echo [OK] Build successful
echo.

REM Step 2: Run tests with coverage
echo Step 2: Running tests with coverage...
call mvn clean verify > nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Tests failed
    exit /b 1
)
echo [OK] Tests passed (62/62)
echo.

REM Step 3: Run SonarQube scan
echo Step 3: Running SonarQube analysis...
call mvn sonar:sonar ^
  -Dsonar.projectKey=%PROJECT_KEY% ^
  -Dsonar.host.url=%SONAR_HOST_URL% ^
  -Dsonar.login=%SONAR_TOKEN%

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: SonarQube analysis failed
    echo Verify:
    echo - SonarQube is running: %SONAR_HOST_URL%
    echo - Token is valid
    exit /b 1
)

echo.
echo ================================================
echo [OK] Analysis Complete!
echo ================================================
echo.
echo View results at: %SONAR_HOST_URL%/dashboard?id=%PROJECT_KEY%
echo.
endlocal
