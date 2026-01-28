package pl.aac.app.projektzal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.aac.app.projektzal.model.Priority;
import pl.aac.app.projektzal.model.Status;
import pl.aac.app.projektzal.model.Ticket;

import java.util.List;

/**
 * Repozytorium do operacji na ticketach
 * Dziedziczy podstawowe metody CRUD z JpaRepository
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Metoda szukania po nazwie - Spring generuje zapytanie automatycznie
    List<Ticket> findByStatus(Status status);

    // Metoda szukania po priorytecie
    List<Ticket> findByPriority(Priority priority);

    // Zapytanie JPQL - wyszukiwanie ticketow o priorytecie wyzszym lub rownym
    @Query("SELECT t FROM Ticket t WHERE t.priority = :priority ORDER BY t.createdAt DESC")
    List<Ticket> findByPriorityOrderByDate(@Param("priority") Priority priority);

    // Wyszukiwanie po tytule (zawiera fraze)
    List<Ticket> findByTitleContainingIgnoreCase(String title);
}
