package pl.aac.app.projektzal.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.aac.app.projektzal.dto.TicketRequestDto;
import pl.aac.app.projektzal.dto.TicketResponseDto;
import pl.aac.app.projektzal.exception.TicketNotFoundException;
import pl.aac.app.projektzal.service.TicketService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

        @Mock
        private TicketService ticketService;

        @InjectMocks
        private TicketController ticketController;

        private TicketResponseDto testResponseDto;

        @BeforeEach
        void setUp() {
                // przygotowanie danych testowych
                testResponseDto = new TicketResponseDto(
                                1L, "Test", "Opis", "HIGH", "NEW", LocalDateTime.now());
        }

        // Test GET /api/tickets - lista ticketow
        @Test
        void testGetAllTickets() {
                // given
                List<TicketResponseDto> tickets = Arrays.asList(testResponseDto);
                when(ticketService.findAll()).thenReturn(tickets);

                // when
                ResponseEntity<List<TicketResponseDto>> response = ticketController.getAllTickets(null, null, null);

                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(1, response.getBody().size());
                verify(ticketService, times(1)).findAll();
        }

        // Test GET /api/tickets/{id} - sukces
        @Test
        void testGetTicketById() {
                // given
                when(ticketService.findById(1L)).thenReturn(testResponseDto);

                // when
                ResponseEntity<TicketResponseDto> response = ticketController.getTicketById(1L);

                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals("Test", response.getBody().getTitle());
        }

        // Test GET /api/tickets/{id} - nie znaleziono (wyjatek)
        @Test
        void testGetTicketByIdNotFound() {
                // given
                when(ticketService.findById(999L)).thenThrow(new TicketNotFoundException(999L));

                // when & then - oczekujemy wyjatku
                assertThrows(TicketNotFoundException.class, () -> {
                        ticketController.getTicketById(999L);
                });
        }

        // Test POST /api/tickets - tworzenie
        @Test
        void testCreateTicket() {
                // given
                TicketRequestDto requestDto = new TicketRequestDto("Nowy", "Opis", "MEDIUM");
                TicketResponseDto responseDto = new TicketResponseDto(
                                2L, "Nowy", "Opis", "MEDIUM", "NEW", LocalDateTime.now());
                when(ticketService.create(any(TicketRequestDto.class))).thenReturn(responseDto);

                // when
                ResponseEntity<TicketResponseDto> response = ticketController.createTicket(requestDto);

                // then
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals("Nowy", response.getBody().getTitle());
        }

        // Test DELETE /api/tickets/{id}
        @Test
        void testDeleteTicket() {
                // given
                doNothing().when(ticketService).delete(1L);

                // when
                ResponseEntity<Void> response = ticketController.deleteTicket(1L);

                // then
                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
                verify(ticketService, times(1)).delete(1L);
        }
}
