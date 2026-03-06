# inroxy

Read-only FHIR R4 proxy that maps Sweden's 1177 Vardguiden e-tjanster REST APIs into a standard FHIR REST interface.

## FHIR Resources

| FHIR Resource | 1177 Source |
|---|---|
| Patient | User profile |
| Appointment | Appointments |
| DocumentReference | Inbox messages |
| Encounter | Journal entries |
| Organization | Care units |
| Observation | Lab results |
| Condition | Diagnoses |
| DiagnosticReport | Journal entries |
| MedicationStatement | Medications |
| MedicationRequest | Prescriptions |
| Immunization | Immunizations |

## Authentication

The proxy forwards cookies from incoming requests to 1177 APIs. Authenticate with 1177 first, then pass the session cookies in your FHIR requests.

## Running

```bash
./gradlew bootRun
```

The FHIR endpoint is available at `http://localhost:8080/fhir/`.

### Capability statement

```bash
curl http://localhost:8080/fhir/metadata
```

### Docker

```bash
docker build -t inroxy .
docker run -p 8080:8080 inroxy
```

## Architecture

```
Request -> FHIR Provider -> Service -> VardguidenClient -> 1177 API
                                           |
                                        Mapper -> FHIR Resource
```

Cookie passthrough enables authenticated access without storing credentials.

## Tech Stack

- Kotlin + Spring Boot 3
- HAPI FHIR R4
- Spring WebFlux (WebClient for upstream calls)
- Spring AI MCP server
- Java 21, Gradle
