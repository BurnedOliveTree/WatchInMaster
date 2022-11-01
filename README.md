# WatchIN
Społecznościowy serwis wideo

## Wymagane do lokalnego developmentu
* Java Development Kit 14
* [Node.js](https://nodejs.org/en/) w wersji 14.x LTS lub nowszej ze zintegrowanym [npm](https://www.npmjs.com/)
* [Docker](https://www.docker.com/) zawierający [Docker Compose](https://docs.docker.com/compose/)

## Struktura projektu
Projekt składa się modułów:
* `watchin-cdn` będący serwerem multimediów
* `watchin-common` zawierający wspólne komponenty współdzielone przez moduły
* `watchin-frontend` będący aplikacją kliencką
* `watchin-mailing` obsługujący wysyłkę e-maili
* `watchin-media` wykonujący operacje na plikach multimedialnych
* `watchin-objectstorage` komunikujący się z magazynem plików
* `watchin-queue` obsługujący asynchroniczną kolejkę wiadomości
* `watchin-server` będący głównym serwerem aplikacji

## Importowanie projektu do IDE (IntelliJ IDEA)
* Zaimportować projekt poprzez wskazanie pliku `build.gradle` w głównym folderze projektu
* Zdefiniować konfiguracje uruchomieniowe Spring Boot:
  * Klasa `WatchINServerApplication`
  * Klasa `WatchINContentDeliveryApplication`
* Zdefiniować konfiguracje uruchomieniowe npm:
  * Komenda `run` dla skryptu `start` z pliku konfiguracyjnego `package.json`
* Włączyć procesowanie adnotacji

## Instalacja projektu
* Zainstalować zależności serwerowe wykonując polecenie Gradle _resolveDependencies_ (`./gradlew resolveDependencies`)
* Zainstalować zależności aplikacji klienckiej wykonując polecenie Gradle _installFrontend_ (`./gradlew installFrontend`) lub `npm install` z katalogu `watchin-frontend`
* Każda zmiana definicji klas DTO służących do komunikacji klient-serwer wymaga przebudowania poleceniem Gradle _generateDTO_ (`./gradlew generateDTO`)

## Przygotowanie lokalnego środowiska
* Uruchomić kontenery Docker poleceniem Gradle _devComposeStart_ (`./gradlew devComposeStart`)
* Wykonanać bazodanowe migracje Flyway poleceniem Gradle _flywayMigrate_ (`./gradlew flywayMigrate`)

## Uruchamianie projektu
* W przypadku uruchamiania z poziomu IDE, uruchomić zdefiniowane wcześniej konfiguracje
* W przypadku ręcznego uruchamiania:
  * Uruchomić serwer aplikacyjny poleceniem Gradle _watchin-server:bootRun_ (`./gradlew watchin-server:bootRun`)
  * Uruchomić serwer multimediów poleceniem Gradle _watchin-cdn:bootRun_ (`./gradlew watchin-cdn:bootRun`)
  * Uruchomić aplikację kliencką poleceniem `npm run start` z katalogu `watchin-frontend`
* Serwer aplikacyjny startuje na porcie `8080`, serwer multimediów na porcie `8081`, a aplikacja kliencka na porcie `4200`

## Lokalizacje komponentów aplikacyjnych
| Komponent                    | Lokalizacja                  |
|------------------------------|------------------------------|
| Aplikacja kliencka           | http://localhost:4200        |
| Przeglądarka plików MinIO    | http://localhost:9000        |
| Lokalny serwer E-mail        | http://localhost:1080        |
| Metryki Prometheus           | http://localhost:9090        |
| Konsola RabbitMQ             | http://localhost:15672       |

## Budowanie kontenerów aplikacyjnych
Aplikacja jest w pełni skonteneryzowana. Polecenie Gradle _dockerBuild_ (`./gradlew dockerBuild`) pakuje
komponenty aplikacyjne do lokalnych obrazów `watchin-server:latest` oraz `watchin-cdn:latest`.

Mini-produkcyjne środowisko wykorzystujące zbudowane obrazy może zostać uruchomione lokalnie
za pomocą polecenia Gradle _prodComposeStart_ (`./gradlew prodComposeStart`). Środowisko mini-produkcyjne
dostępne jest na porcie `80`.