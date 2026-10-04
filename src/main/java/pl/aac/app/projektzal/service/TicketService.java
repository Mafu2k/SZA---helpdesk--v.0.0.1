package pl.aac.app.projektzal.service;

import org.springframework.stereotype.Service;
import pl.aac.app.projektzal.dto.TicketMapper;
import pl.aac.app.projektzal.dto.TicketRequestDto;
import pl.aac.app.projektzal.dto.TicketResponseDto;
import pl.aac.app.projektzal.exception.InvalidPriorityException;
import pl.aac.app.projektzal.exception.InvalidStatusTransitionException;
import pl.aac.app.projektzal.exception.TicketNotFoundException;
import pl.aac.app.projektzal.model.Priority;
import pl.aac.app.projektzal.model.Status;
import pl.aac.app.projektzal.model.Ticket;
import pl.aac.app.projektzal.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<TicketResponseDto> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public TicketResponseDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        return ticketMapper.toResponseDto(ticket);
    }

    public TicketResponseDto create(TicketRequestDto dto) {
        validatePriority(dto.getPriority());
        Ticket ticket = ticketMapper.toEntity(dto);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponseDto(saved);
    }

    public TicketResponseDto update(Long id, TicketRequestDto dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        validatePriority(dto.getPriority());
        ticketMapper.updateEntity(ticket, dto);
        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException(id);
        }
        ticketRepository.deleteById(id);
    }

    public TicketResponseDto changeStatus(Long id, String newStatusStr) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        Status newStatus;
        try {
            newStatus = Status.valueOf(newStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusTransitionException("Nieprawidlowy status: " + newStatusStr);
        }
        validateStatusTransition(ticket.getStatus(), newStatus);
        ticket.setStatus(newStatus);
        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toResponseDto(updated);
    }

    public List<TicketResponseDto> findByStatus(String statusStr) {
        Status status = Status.valueOf(statusStr.toUpperCase());
        List<Ticket> tickets = ticketRepository.findByStatus(status);
        return tickets.stream()
                .map(ticketMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDto> findByPriority(String priorityStr) {
        Priority priority = Priority.valueOf(priorityStr.toUpperCase());
        List<Ticket> tickets = ticketRepository.findByPriorityOrderByDate(priority);
        return tickets.stream()
                .map(ticketMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDto> searchByTitle(String title) {
        List<Ticket> tickets = ticketRepository.findByTitleContainingIgnoreCase(title);
        return tickets.stream()
                .map(ticketMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    private void validatePriority(String priorityStr) {
        try {
            Priority.valueOf(priorityStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPriorityException(priorityStr);
        }
    }

    // Walidacja zmiany statusu - nie mozna wrocic z CLOSED
    private void validateStatusTransition(Status currentStatus, Status newStatus) {
        if (currentStatus == Status.CLOSED && newStatus != Status.CLOSED) {
            throw new InvalidStatusTransitionException(
                    currentStatus.name(),
                    newStatus.name());
        }
    }
}
