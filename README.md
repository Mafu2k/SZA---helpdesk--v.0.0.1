# SZA Helpdesk

Projekt zaliczeniowy: backend prostego systemu zgłoszeń serwisowych (ticketów) w Spring Boocie.
Udostępnia REST API do zakładania zgłoszeń, nadawania im priorytetu i przeprowadzania ich przez
kolejne statusy.

Zgłoszenie przechodzi kolejno przez `NEW`, `IN_PROGRESS`, `RESOLVED` i `CLOSED`. Serwis pilnuje,
żeby nie dało się przeskoczyć etapu ani cofnąć statusu. Taka próba kończy się błędem 400 ze
spójnym formatem odpowiedzi z `GlobalExceptionHandler`. Priorytety to `LOW`, `MEDIUM`, `HIGH`
i `CRITICAL`.

Encja `Ticket` nie wychodzi poza warstwę serwisu, bo API operuje na DTO (`TicketRequestDto`,
`TicketResponseDto`).

## Uruchomienie

```bash
./mvnw spring-boot:run   # API na http://localhost:8080
./mvnw test              # testy jednostkowe serwisu i kontrolera
```

Baza to H2 w pamięci, więc po restarcie wszystko znika. Konsola H2 jest pod `/h2-console`
(JDBC URL `jdbc:h2:mem:helpdeskdb`, użytkownik `sa`).

## API

| Metoda | Ścieżka | Opis |
|--------|---------|------|
| GET | `/api/tickets` | lista zgłoszeń |
| GET | `/api/tickets/{id}` | jedno zgłoszenie |
| POST | `/api/tickets` | nowe zgłoszenie |
| PUT | `/api/tickets/{id}` | edycja |
| PATCH | `/api/tickets/{id}/status` | zmiana statusu |
| DELETE | `/api/tickets/{id}` | usunięcie |

## Licencja

MIT
