# SZA Helpdesk

Projekt zaliczeniowy: backend prostego systemu zgłoszeń serwisowych (ticketów) w Spring Boocie.
Udostępnia REST API do zakładania zgłoszeń, nadawania im priorytetu i przeprowadzania ich przez
kolejne statusy.

Statusy to `NEW`, `IN_PROGRESS`, `RESOLVED` i `CLOSED`, a priorytety `LOW`, `MEDIUM`, `HIGH`
i `CRITICAL`. Zamkniętego zgłoszenia nie da się otworzyć ponownie, a nieznany status albo
priorytet też jest odrzucany. Takie błędy wracają jako 400 we wspólnym formacie z
`GlobalExceptionHandler`.

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

## Do zrobienia

- Pełna maszyna stanów. Teraz można np. przejść z `NEW` od razu do `RESOLVED`.
- Trwała baza (PostgreSQL) zamiast H2 w pamięci.

## Licencja

MIT
