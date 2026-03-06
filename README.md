# inroxy

FHIR R4 proxy for Swedish 1177 Vardguiden. Maps 1177's internal APIs to standard FHIR resources.

## Running

```bash
./gradlew bootRun
```

The server starts on port 8091 with FHIR endpoints at `/fhir/*`.

## Authentication

inroxy passes through your 1177 session cookies. Log in to [e-tjanster.1177.se](https://e-tjanster.1177.se) with BankID, then copy all cookies from your browser:

1. Open DevTools (F12) -> Network tab
2. Click any request to e-tjanster.1177.se
3. Copy the full `Cookie` header value from the request headers

Pass the cookies in your request:

```bash
curl -H 'Cookie: <paste all cookies here>' http://localhost:8091/fhir/Patient/me
```

## Available endpoints

- `GET /fhir/Patient/me` - your name from 1177
- `GET /fhir/Appointment` - booked appointments
- `GET /fhir/DocumentReference` - inbox messages
