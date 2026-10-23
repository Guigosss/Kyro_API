@echo off
setlocal
cd /d "%~dp0"

set "PG_CONTAINER=kyro-postgres"
set "API_CONTAINER=kyro-api"
set "NETWORK=kyro-network"
set "IMAGE=kyro-api:1.0"
set "DB_NAME=kyro"
set "DB_USER=postgres"

echo.
echo === Verification de Docker ===
docker info >nul 2>&1
if errorlevel 1 (
    echo ERREUR : Docker ne repond pas. Lance Docker Desktop et attends qu'il soit pret.
    goto :fail
)

echo.
echo === Configuration Java ===
set "JAVA_HOME=C:\Users\g.vandevelde\.jdks\openjdk-26.0.2"
set "PATH=%JAVA_HOME%\bin;%PATH%"
java -version
if errorlevel 1 (
    echo ERREUR : Java est introuvable dans le PATH.
    goto :fail
)

echo.
echo === Secrets ===
set "DATASOURCE_PASSWORD="
set "JWT_SECRET="
set /p DATASOURCE_PASSWORD=Mot de passe PostgreSQL :
set /p JWT_SECRET=Secret JWT (32 caracteres minimum) :

if not defined DATASOURCE_PASSWORD (
    echo ERREUR : le mot de passe PostgreSQL est obligatoire.
    goto :fail
)
if not defined JWT_SECRET (
    echo ERREUR : JWT_SECRET est obligatoire.
    goto :fail
)

echo.
echo === Construction du JAR Spring Boot ===
call mvnw.cmd clean package -DskipTests
if errorlevel 1 (
    echo ERREUR : la compilation Maven a echoue.
    goto :fail
)

echo.
echo === Construction de l'image Docker ===
docker build -t %IMAGE% .
if errorlevel 1 (
    echo ERREUR : docker build a echoue.
    goto :fail
)

echo.
echo === Demarrage de PostgreSQL ===
docker inspect %PG_CONTAINER% >nul 2>&1
if errorlevel 1 (
    echo ERREUR : le conteneur %PG_CONTAINER% n'existe pas. Verifie son nom avec : docker ps -a
    goto :fail
)
docker start %PG_CONTAINER% >nul 2>&1

echo.
echo === Preparation du reseau Docker ===
docker network inspect %NETWORK% >nul 2>&1
if errorlevel 1 (
    docker network create %NETWORK%
    if errorlevel 1 (
        echo ERREUR : impossible de creer le reseau Docker.
        goto :fail
    )
)

docker network connect %NETWORK% %PG_CONTAINER% >nul 2>&1

echo.
echo === Remplacement du conteneur API ===
docker rm -f %API_CONTAINER% >nul 2>&1

docker run -d ^
  --name %API_CONTAINER% ^
  --network %NETWORK% ^
  -p 8080:8080 ^
  -e DATASOURCE_URL=jdbc:postgresql://%PG_CONTAINER%:5432/%DB_NAME% ^
  -e DATASOURCE_USERNAME=%DB_USER% ^
  -e DATASOURCE_PASSWORD ^
  -e JWT_SECRET ^
  %IMAGE%
if errorlevel 1 (
    echo ERREUR : le conteneur API n'a pas pu demarrer.
    goto :fail
)

echo.
echo === Attente du demarrage (10 s) ===
timeout /t 10 /nobreak >nul

echo.
echo === Etat du conteneur ===
docker ps -a --filter "name=%API_CONTAINER%"

echo.
echo === Derniers logs ===
docker logs --tail 100 %API_CONTAINER%

echo.
echo Pour suivre les logs : docker logs -f %API_CONTAINER%
echo API : http://localhost:8080
goto :end

:fail
echo.
echo *** Le script s'est arrete a cause d'une erreur (voir ci-dessus). ***

:end
echo.
pause
endlocal