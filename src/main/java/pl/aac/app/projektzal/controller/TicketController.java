package pl.aac.app.projektzal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.aac.app.projektzal.dto.TicketRequestDto;
import pl.aac.app.projektzal.dto.TicketResponseDto;
import pl.aac.app.projektzal.service.TicketService;

import java.util.List;

/**
 * Kontroler REST API dla zarzadzania ticketami
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // GET /api/tickets - pobieranie listy ticketow z opcjonalnymi filtrami
    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String title) {

        List<TicketResponseDto> tickets;

        // filtrowanie po statusie
        if (status != null && !status.isEmpty()) {
            tickets = ticketService.findByStatus(status);
        }
        // filtrowanie po priorytecie
        else if (priority != null && !priority.isEmpty()) {
            tickets = ticketService.findByPriority(priority);
        }
        // wyszukiwanie po tytule
        else if (title != null && !title.isEmpty()) {
            tickets = ticketService.searchByTitle(title);
        }
        // wszystkie tickety
        else {
            tickets = ticketService.findAll();
        }

        return ResponseEntity.ok(tickets);
    }

    // GET /api/tickets/{id} - pobieranie pojedynczego ticketa
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id) {
        TicketResponseDto ticket = ticketService.findById(id);
        return ResponseEntity.ok(ticket);
    }

    // POST /api/tickets - tworzenie nowego ticketa
    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto dto) {
        TicketResponseDto created = ticketService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/tickets/{id} - aktualizacja ticketa
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDto> updateTicket(
            @PathVariable Long id,
            @RequestBody TicketRequestDto dto) {
        TicketResponseDto updated = ticketService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/tickets/{id}/status - zmiana statusu
    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponseDto> changeStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        TicketResponseDto updated = ticketService.changeStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/tickets/{id} - usuwanie ticketa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
