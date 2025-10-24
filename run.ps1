# Fashion Store - Quick Start Script
# Windows PowerShell Script

Write-Host "==================================" -ForegroundColor Cyan
Write-Host "Fashion Store - Spring Boot App" -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""

# Check Java version
Write-Host "Checking Java version..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version" | Select-Object -First 1
    Write-Host "✓ Java found: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Java not found! Please install Java 17 or higher." -ForegroundColor Red
    exit 1
}

# Check Maven
Write-Host "Checking Maven..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven" | Select-Object -First 1
    Write-Host "✓ Maven found: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Maven not found! Please install Maven 3.6+." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "==================================" -ForegroundColor Cyan
Write-Host "Building project..." -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan

mvn clean install -DskipTests

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✓ Build successful!" -ForegroundColor Green
    Write-Host ""
    Write-Host "==================================" -ForegroundColor Cyan
    Write-Host "Starting application..." -ForegroundColor Cyan
    Write-Host "==================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Application will be available at: http://localhost:8080" -ForegroundColor Yellow
    Write-Host "Admin credentials: username=admin, password=admin123" -ForegroundColor Yellow
    Write-Host ""
    
    mvn spring-boot:run
} else {
    Write-Host ""
    Write-Host "✗ Build failed! Please check errors above." -ForegroundColor Red
    exit 1
}
