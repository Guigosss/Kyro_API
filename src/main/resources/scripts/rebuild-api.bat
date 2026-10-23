@echo off
setlocal

REM A executer depuis la racine du projet kyro_api.
REM Necessite Docker Desktop et le Maven Wrapper (mvnw.cmd).

echo.
echo === Configuration Java ===
if exist "C:\Users\g.vandevelde\.jdks\openjdk-26.0.2\bin\java.exe" (
    set "JAVA_HOME=C:\Users\g.vandevelde\.jdks\openjdk-26.0.2"
    set "PATH=%JAVA_HOME%\bin;%PATH%"
)

echo.
echo === Secrets et configuration ===
set /p DB_PASSWORD=Mot de passe PostgreSQL :
set /p JWT_SECRET=Secret JWT :

if "%DB_PASSWORD%"=="" (
    echo ERREUR : le mot de passe PostgreSQL est obligatoire.
    exit /b 1
)
if "%JWT_SECRET%"=="" (
    echo ERREUR : JWT_SECRET est obligatoire.
    exit /b 1
)

echo.
echo === Construction du JAR Spring Boot ===
call mvnw.cmd clean package -DskipTests
if errorlevel 1 (
    echo ERREUR : la compilation Maven a echoue.
    exit /b 1
)

echo.
echo === Construction de l'image Docker ===
docker build -t kyro-api:1.0 .
if errorlevel 1 (
    echo ERREUR : docker build a echoue.
    exit /b 1
)

echo.
echo === Preparation du reseau Docker ===
docker network inspect kyro-network >nul 2>&1
if errorlevel 1 (
    docker network create kyro-network
    if errorlevel 1 (
        echo ERREUR : impossible de creer le reseau Docker.
        exit /b 1
    )
)

echo.
echo === Connexion de PostgreSQL au reseau ===
docker network connect kyro-network kyro-postgres >nul 2>&1
REM Une erreur ici peut simplement signifier que le conteneur est deja connecte.

echo.
echo === Remplacement du conteneur API ===
docker rm -f kyro-api >nul 2>&1

docker run -d ^
  --name kyro-api ^
  --network kyro-network ^
  -p 8080:8080 ^
  -e "DATASOURCE_URL=jdbc:postgresql://kyro-postgres:5432/kyro" ^
  -e "DATASOURCE_USERNAME=postgres" ^
  -e "DATASOURCE_PASSWORD=%DB_PASSWORD%" ^
  -e "JWT_SECRET=%JWT_SECRET%" ^
  kyro-api:1.0

if errorlevel 1 (
    echo ERREUR : le conteneur API n'a pas pu demarrer.
    exit /b 1
)

echo.
echo === Conteneur lance. Derniers logs : ===
timeout /t 3 /nobreak >nul
docker logs --tail 100 kyro-api
echo.
echo Pour suivre les logs : docker logs -f kyro-api
echo API : http://localhost:8080
endlocal
