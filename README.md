
# Blackboard API Projekt

## Projektübersicht

Dieses Projekt ist ein Java Spring Boot-basiertes API zur Verwaltung von Blackboards. Ein Blackboard ist ein einfaches Modell, das Nachrichten speichern kann. Das Projekt ermöglicht das Erstellen, Lesen, Schreiben und Löschen von Blackboards.

## Autor

- Jonas Berger
- Email: berger.jonas-it21@it.dhbw-ravensburg.de

## Technologie-Stack

Das Projekt wurde mit Java Spring Boot umgesetzt, einem Framework, das die Erstellung von Standalone-Produktionsanwendungen in Java erleichtert.

## Installation

### Voraussetzungen

1. **Java Runtime Environment (JRE) / Java Development Kit (JDK)**:
   - Laden Sie das JDK von der offiziellen Oracle-Website oder einer anderen vertrauenswürdigen Quelle herunter und installieren Sie es.

2. **MongoDB**:
   - Installieren Sie MongoDB entweder lokal oder verwenden Sie Docker. Hier ein Beispiel für die Installation und Ausführung von MongoDB mit Docker:
     ```bash
     docker pull mongo
     docker run -d -p 27017:27017 --name mongodb mongo
     ```

### Schritte zur Installation

1. **Download**:
   - Laden Sie das Projekt als ZIP-Datei herunter und entpacken Sie es.

2. **Projektkonfiguration**:
   - Stellen Sie sicher, dass MongoDB auf dem Standardport 27017 läuft.

3. **Abhängigkeiten**:
   - Das Projekt verwendet Maven zur Verwaltung der Abhängigkeiten. Alle notwendigen Abhängigkeiten sind in der `pom.xml` Datei definiert.

### Starten des Projekts

1. **Klonen Sie das Repository** (falls nicht bereits heruntergeladen):
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Starten Sie die Anwendung**:
   ```bash
   ./mvnw spring-boot:run

## Verwendete Packages

- **spring-boot-starter-data-mongodb**:
  - Dieses Paket wird verwendet, um die Integration mit MongoDB zu erleichtern. Es bietet Repository-Support für MongoDB, was die Datenbankoperationen vereinfacht.

- **spring-boot-starter-validation**:
  - Ermöglicht die Validierung von Beans mittels JSR-303/JSR-380 Anmerkungen. Es wird verwendet, um die Eingabedaten zu überprüfen und sicherzustellen, dass sie den definierten Constraints entsprechen.

- **springdoc-openapi-starter-webmvc-ui:2.5.0**:
  - Generiert automatisch API-Dokumentationen in Form von OpenAPI/Swagger. Dies erleichtert die Dokumentation und das Testen der API-Endpunkte.

- **spring-boot-starter-web**:
  - Grundlegendes Paket für Web-Anwendungen, das Tomcat und Spring MVC enthält. Es wird verwendet, um RESTful Web-Services zu erstellen.

- **lombok**:
  - Eine Bibliothek, die die Boilerplate-Codes wie Getter, Setter, Konstruktoren und ToString-Methoden automatisiert generiert. Es vereinfacht den Code und verbessert die Lesbarkeit.

## Architekturbeschreibung

- **Configuration**:
  - Enthält die Konfigurationsklassen für die Anwendung, z.B. Datenbankkonfigurationen und Sicherheitseinstellungen.

- **Controller**:
  - Die Controller-Klassen handhaben die HTTP-Anfragen und -Antworten. Sie definieren die API-Endpunkte und steuern den Datenfluss zwischen Service und View.

- **DTO (Data Transfer Object)**:
  - DTOs werden verwendet, um Daten zwischen Schichten zu übertragen. Sie enthalten nur die notwendigen Felder, die für die jeweilige Operation erforderlich sind.

- **Exception**:
  - Diese Schicht enthält die Ausnahmebehandlung. Es werden benutzerdefinierte Ausnahme-Handler definiert, um eine konsistente Fehlerbehandlung zu gewährleisten.

- **Model**:
  - Definiert die Datenmodelle, die den Datenbanktabellen entsprechen. Diese Klassen werden von den Repository-Klassen verwendet.

- **Repository**:
  - Die Repository-Schicht enthält die Schnittstellen, die CRUD-Operationen für die Datenmodelle definieren. Spring Data MongoDB stellt die Implementierungen zur Verfügung.

- **Service**:
  - Die Service-Schicht enthält die Geschäftslogik der Anwendung. Sie vermittelt zwischen den Controller-Klassen und den Repository-Klassen.

## Kommunikationsschnittstellen und Protokolle

### API-Endpunkte

- **POST /blackboards**:
  - Erstellt ein neues Blackboard.
  - **Parameter**: `CreateBlackboardDTO` (Name: String, ValidityInSeconds: Long)
  - **Antwort**: Erstelltes Blackboard

- **POST /blackboards/{id}**:
  - Schreibt eine Nachricht auf ein Blackboard.
  - **Parameter**: `id` (String), `DisplayDataDTO` (Data: String)
  - **Antwort**: Erfolgsmeldung

- **GET /blackboards/{id}**:
  - Liest den Inhalt eines Blackboards.
  - **Parameter**: `id` (String)
  - **Antwort**: Inhalt des Blackboards

- **DELETE /blackboards/{id}**:
  - Löscht ein Blackboard.
  - **Parameter**: `id` (String)
  - **Antwort**: Erfolgsmeldung

- **DELETE /blackboards**:
  - Löscht alle Blackboards.
  - **Antwort**: Erfolgsmeldung

- **GET /blackboards**:
  - Listet alle Blackboards auf.
  - **Antwort**: Liste von Blackboards

### Fehlerverhalten

- Bei ungültigen Anfragen oder Fehlern in der Geschäftslogik gibt die API entsprechende HTTP-Statuscodes und Fehlermeldungen zurück. Diese werden durch benutzerdefinierte Ausnahme-Handler verarbeitet und in einem konsistenten Format zurückgegeben.

## Fazit

Dieses Projekt bietet eine robuste Grundlage für die Verwaltung von Blackboards. Es nutzt bewährte Praktiken und Technologien von Spring Boot, um eine skalierbare und wartbare Anwendung zu erstellen.
```

Dies ist eine umfassende README.md-Datei für das Blackboard API Projekt. Sie enthält alle erforderlichen Informationen zur Installation, den verwendeten Paketen, der Architektur und der API-Dokumentation.
