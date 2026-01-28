package pl.aac.app.projektzal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.aac.app.projektzal.dto.TicketMapper;
import pl.aac.app.projektzal.dto.TicketResponseDto;
import pl.aac.app.projektzal.exception.TicketNotFoundException;
import pl.aac.app.projektzal.model.Priority;
import pl.aac.app.projektzal.model.Status;
import pl.aac.app.projektzal.model.Ticket;
import pl.aac.app.projektzal.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testy jednostkowe dla TicketService
 * Uzywamy Mockito do mockowania repozytorium
 */
@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketService ticketService;

    private Ticket testTicket;
    private TicketResponseDto testResponseDto;

    @BeforeEach
    void setUp() {
        // przygotowanie danych testowych
        testTicket = new Ticket();
        testTicket.setId(1L);
        testTicket.setTitle("Test ticket");
        testTicket.setDescription("Opis testowy");
        testTicket.setPriority(Priority.HIGH);
        testTicket.setStatus(Status.NEW);
        testTicket.setCreatedAt(LocalDateTime.now());

        testResponseDto = new TicketResponseDto(
                1L, "Test ticket", "Opis testowy",
                "HIGH", "NEW", LocalDateTime.now());
    }

    // Test pobierania wszystkich ticketow
    @Test
    void testFindAll() {
        // given - przygotowanie
        List<Ticket> tickets = Arrays.asList(testTicket);
        when(ticketRepository.findAll()).thenReturn(tickets);
        when(ticketMapper.toResponseDto(any(Ticket.class))).thenReturn(testResponseDto);

        // when - wykonanie
        List<TicketResponseDto> result = ticketService.findAll();

        // then - sprawdzenie
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    // Test pobierania ticketa po ID - sukces
    @Test
    void testFindByIdSuccess() {
        // given
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(ticketMapper.toResponseDto(testTicket)).thenReturn(testResponseDto);

        // when
        TicketResponseDto result = ticketService.findById(1L);

        // then
        assertNotNull(result);
        assertEquals("Test ticket", result.getTitle());
        verify(ticketRepository, times(1)).findById(1L);
    }

    // Test pobierania ticketa po ID - nie znaleziono (wyjatek)
    @Test
    void testFindByIdNotFound() {
        // given
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then - oczekujemy wyjatku
        assertThrows(TicketNotFoundException.class, () -> {
            ticketService.findById(999L);
        });

        verify(ticketRepository, times(1)).findById(999L);
    }

    // Test usuwania ticketa
    @Test
    void testDelete() {
        // given
        when(ticketRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ticketRepository).deleteById(1L);

        // when
        ticketService.delete(1L);

        // then
        verify(ticketRepository, times(1)).deleteById(1L);
    }

    // Test usuwania nieistniejacego ticketa
    @Test
    void testDeleteNotFound() {
        // given
        when(ticketRepository.existsById(999L)).thenReturn(false);

        // when & then
        assertThrows(TicketNotFoundException.class, () -> {
            ticketService.delete(999L);
        });
    }
}
