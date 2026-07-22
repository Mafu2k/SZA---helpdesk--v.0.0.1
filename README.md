# SZA — Helpdesk (ticketing)

System zgłoszeń serwisowych (helpdesk) udostępniający REST API do zarządzania ticketami:
tworzenie, zmiana statusu, priorytety i walidacja dozwolonych przejść między stanami.
Projekt zaliczeniowy.

## Stack

- Java + Spring Boot
- Maven

## Uruchomienie

```bash
./mvnw spring-boot:run
```

API dostępne pod `http://localhost:8080`.

## Główne elementy

- `TicketController` — endpointy REST
- `Priority` oraz statusy z walidacją przejść (`InvalidStatusTransitionException`)
- Globalna obsługa błędów (`GlobalExceptionHandler`)
