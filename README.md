# SZA — Helpdesk (system zgłoszeń)

Backend systemu helpdesk (ticketing) udostępniający REST API do obsługi zgłoszeń
serwisowych. Umożliwia tworzenie zgłoszeń, zmianę ich statusu w kontrolowanym cyklu
życia, nadawanie priorytetów oraz walidację danych wejściowych. Projekt zaliczeniowy.

## Funkcjonalności

- Pełny CRUD zgłoszeń (utworzenie, podgląd, edycja, usunięcie)
- Cykl życia zgłoszenia oparty o statusy z walidacją dozwolonych przejść
- Priorytety zgłoszeń
- Warstwa DTO oddzielająca model bazodanowy od API (`TicketRequestDto`, `TicketResponseDto`, `TicketMapper`)
- Globalna obsługa błędów zwracająca spójny format odpowiedzi (`GlobalExceptionHandler`, `ErrorMessage`)
- Wyjątki domenowe: `TicketNotFoundException`, `InvalidStatusTransitionException`, `InvalidPriorityException`

## Model domenowy

**Statusy zgłoszenia:** `NEW → IN_PROGRESS → RESOLVED → CLOSED`
(przejścia poza dozwolonymi rzucają `InvalidStatusTransitionException`).

**Priorytety:** `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`.

## REST API

Bazowy adres: `/api/tickets`

| Metoda | Ścieżka                    | Opis                          |
|--------|----------------------------|-------------------------------|
| GET    | `/api/tickets`             | lista wszystkich zgłoszeń     |
| GET    | `/api/tickets/{id}`        | pojedyncze zgłoszenie         |
| POST   | `/api/tickets`             | utworzenie zgłoszenia         |
| PUT    | `/api/tickets/{id}`        | aktualizacja zgłoszenia       |
| PATCH  | `/api/tickets/{id}/status` | zmiana statusu                |
| DELETE | `/api/tickets/{id}`        | usunięcie zgłoszenia          |

## Stack

- Java + Spring Boot (Spring Web, Spring Data JPA)
- Maven

## Struktura projektu

```
src/main/java/pl/aac/app/projektzal/
├── controller/   # TicketController — endpointy REST
├── service/      # TicketService — logika biznesowa i walidacja przejść
├── repository/   # TicketRepository — dostęp do danych (JPA)
├── model/        # Ticket, Status, Priority
├── dto/          # TicketRequestDto, TicketResponseDto, TicketMapper
└── exception/    # wyjątki domenowe + GlobalExceptionHandler
```

## Uruchomienie

Wymagania: Java 17+ oraz Maven (lub dołączony `mvnw`).

```bash
./mvnw spring-boot:run
```

API dostępne pod `http://localhost:8080`.

## Autor

Łukasz Janicki

## Licencja

MIT — szczegóły w pliku [LICENSE](LICENSE).
