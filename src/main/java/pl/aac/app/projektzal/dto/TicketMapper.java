package pl.aac.app.projektzal.dto;

import org.springframework.stereotype.Component;
import pl.aac.app.projektzal.model.Priority;
import pl.aac.app.projektzal.model.Status;
import pl.aac.app.projektzal.model.Ticket;

/**
 * Klasa do mapowania pomiedzy encja Ticket a DTO
 */
@Component
public class TicketMapper {

    /**
     * Mapowanie z encji Ticket na TicketResponseDto
     */
    public TicketResponseDto toResponseDto(Ticket ticket) {
        // tworzymy nowe DTO z danymi z encji
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setPriority(ticket.getPriority().name()); // enum -> String
        dto.setStatus(ticket.getStatus().name()); // enum -> String
        dto.setCreatedAt(ticket.getCreatedAt());
        return dto;
    }

    /**
     * Mapowanie z TicketRequestDto na nowa encje Ticket
     */
    public Ticket toEntity(TicketRequestDto dto) {
        // tworzymy nowa encje
        Ticket ticket = new Ticket();
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());

        // konwersja String -> enum Priority
        Priority priority = Priority.valueOf(dto.getPriority().toUpperCase());
        ticket.setPriority(priority);

        // domyslne wartosci dla nowego ticketa
        ticket.setStatus(Status.NEW);
        ticket.setCreatedAt(java.time.LocalDateTime.now());

        return ticket;
    }

    /**
     * Aktualizacja istniejacego ticketa danymi z DTO
     */
    public void updateEntity(Ticket ticket, TicketRequestDto dto) {
        // aktualizujemy tylko pola z DTO
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());

        // konwersja String -> enum Priority
        Priority priority = Priority.valueOf(dto.getPriority().toUpperCase());
        ticket.setPriority(priority);
    }
}
