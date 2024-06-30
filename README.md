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
   - Dieses Projekt verwendet Java Version 21. Sie können die JDK von OpenLogic herunterladen: [OpenLogic OpenJDK Downloads](https://www.openlogic.com/openjdk-downloads).

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
   - Das Projekt verwendet Gradle Kotlin zur Verwaltung der Abhängigkeiten. Alle notwendigen Abhängigkeiten sind in der `build.gradle.kts` Datei definiert.

4. **Starten der Anwendung**:
   ```bash
   ./gradlew bootRun
   ```

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
  - Enthält die Konfigurationsklassen für die Anwendung, z.B. Datenbankkonfigurationen und Sicherheitseinstellungen. Diese Klassen sorgen für eine zentrale Verwaltung und leichte Anpassbarkeit der Anwendungskonfiguration.

- **Controller**:
  - Die Controller-Klassen handhaben die HTTP-Anfragen und -Antworten. Sie definieren die API-Endpunkte und steuern den Datenfluss zwischen Service und View. Diese Schicht sorgt für eine klare Trennung der Verantwortlichkeiten und ermöglicht eine einfache Erweiterung der API.

- **DTO (Data Transfer Object)**:
  - DTOs werden verwendet, um Daten zwischen Schichten zu übertragen. Sie enthalten nur die notwendigen Felder, die für die jeweilige Operation erforderlich sind, was die Sicherheit und Effizienz erhöht.

- **Exception**:
  - Diese Schicht enthält die Ausnahmebehandlung. Es werden benutzerdefinierte Ausnahme-Handler definiert, um eine konsistente Fehlerbehandlung zu gewährleisten. Dies verbessert die Robustheit und Benutzerfreundlichkeit der Anwendung.

- **Model**:
  - Definiert die Datenmodelle, die den Datenbanktabellen entsprechen. Diese Klassen werden von den Repository-Klassen verwendet. Die Verwendung von Modellen ermöglicht eine klare Strukturierung und Verwaltung der Daten.

- **Repository**:
  - Die Repository-Schicht enthält die Schnittstellen, die CRUD-Operationen für die Datenmodelle definieren. Spring Data MongoDB stellt die Implementierungen zur Verfügung, was die Entwicklungszeit reduziert und die Wartbarkeit verbessert.

- **Service**:
  - Die Service-Schicht enthält die Geschäftslogik der Anwendung. Sie vermittelt zwischen den Controller-Klassen und den Repository-Klassen. Die Verwendung von Services fördert die Wiederverwendbarkeit und Testbarkeit der Geschäftslogik.

### Vorteile der Architektur
Diese Architektur trennt die verschiedenen Verantwortlichkeiten der Anwendung in einzelne Schichten, was die Wartbarkeit und Erweiterbarkeit des Codes erleichtert.

## Nebenläufigkeitstransparenz

In diesem Projekt wird Nebenläufigkeitstransparenz durch zwei Hauptmechanismen erreicht: Locking-Mechanismen in den Service-Klassen und eingebaute Methodiken von MongoDB.

### Locking-Mechanismen in den Service-Klassen

Die Service-Klassen verwenden synchronisierte Methoden oder explizite Lock-Objekte, um sicherzustellen, dass nur ein Thread gleichzeitig auf kritische Abschnitte des Codes zugreifen kann. Dies verhindert Inkonsistenzen und gewährleistet, dass jede Operation vollständig abgeschlossen ist, bevor eine andere beginnt.

### MongoDB Mechanismen zur Nebenläufigkeitstransparenz

MongoDB verwendet mehrere Mechanismen, um Nebenläufigkeit zu gewährleisten:

- **Optimistic Concurrency Control (OCC)**:
  - MongoDB unterstützt OCC, das darauf basiert, dass beim Lesen und Aktualisieren eines Dokuments eine Versionsnummer überprüft wird. Wenn die Versionsnummer beim Schreiben nicht mehr übereinstimmt, wurde das Dokument zwischenzeitlich geändert, und der Schreibvorgang wird abgelehnt.

- **Atomic Operations**:
  - Einzelne Schreiboperationen in MongoDB sind atomar. Das bedeutet, dass eine Schreiboperation entweder vollständig abgeschlossen wird oder gar nicht, wodurch Inkonsistenzen verhindert werden.

- **Locks**:
  - MongoDB verwendet intern verschiedene Arten von Locks auf unterschiedlichen Granularitätsebenen (z.B. auf Dokument- oder Collection-Ebene), um sicherzustellen, dass nur ein Prozess gleichzeitig auf eine Ressource zugreifen kann.

Diese Mechanismen zusammen sorgen dafür, dass das System auch bei hoher Parallelität konsistent bleibt und keine Daten verloren gehen oder inkonsistent werden.

### Vorteile der Nebenläufigkeitstransparenz
Durch die Implementierung von Locking-Mechanismen und die Nutzung der MongoDB-Methoden wird sichergestellt, dass Datenintegrität und Konsistenz auch in einer hochparallelen Umgebung gewährleistet sind. Dies verbessert die Zuverlässigkeit und Stabilität der Anwendung.

## Validierung der Eingaben

Die Validierung der Eingaben wird serverseitig durchgeführt, um die Integrität und Konsistenz der Daten zu gewährleisten. Jede Anfrage an die API wird überprüft, um sicherzustellen, dass die übermittelten Daten den definierten Regeln und Constraints entsprechen. Dies verhindert, dass ungültige oder fehlerhafte Daten in das System gelangen. Folgende Aspekte werden validiert:

- **Datenformate**: Überprüfung, ob die übermittelten Daten dem erwarteten Format entsprechen (z.B. Datentypen, String-Längen).
- **Pflichtfelder**: Sicherstellung, dass alle erforderlichen Felder vorhanden sind und nicht null sind.
- **Constraints**: Einhaltung von definierten Regeln, wie z.B. Mindest- und Höchstwerte für numerische Felder.

## Kommunikationsschnittstellen und Protokolle

### API-Endpunkte
Die API verwendet HTTP und basiert auf dem REST-Architekturstil.

- **PUT /api/v1/blackboard/display/{name}**: Fügt neue Daten zum Blackboard hinzu.
- **PUT /api/v1/blackboard/clear/{name}**: Löscht das Blackboard.
- **POST /api/v1/blackboard/create**: Erstellt ein neues Blackboard.
- **GET /api/v1/blackboard/read/{name}**: Liest den Inhalt eines Blackboards.
- **GET /api/v1/blackboard/list**: Listet alle Blackboards auf.
- **DELETE /api/v1/blackboard/delete/{name}**: Löscht ein Blackboard.
- **DELETE /api/v1/blackboard/delete/all**: Löscht alle Blackboards.

Für eine vollständige Beschreibung der API-Schnittstellen und zulässigen Parameter besuchen Sie bitte die Swagger-Beschreibung unter [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (nur verfügbar, wenn das Projekt läuft).

### Fehlerverhalten
Bei Fehlern werden entsprechende HTTP-Statuscodes und Fehlermeldungen zurückgegeben, z.B.:
- **404 Not Found**: Wenn ein Blackboard nicht gefunden wurde.
- **400 Bad Request**: Bei ungültigen Eingabedaten.
- **409 Conflict**: Wenn ein Konflikt bei der Datenverarbeitung auftritt, z.B. wenn ein Blackboard mit demselben Namen bereits existiert.
- **500 Internal Server Error**: Bei internen Serverfehlern.
