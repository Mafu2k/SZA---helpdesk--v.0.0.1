package pl.aac.app.projektzal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Encja reprezentujaca zgloszenie w systemie Helpdesk
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // tytul zgloszenia

    @Column(length = 1000)
    private String description; // opis problemu

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority; // priorytet

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // status zgloszenia

    @Column(nullable = false)
    private LocalDateTime createdAt; // data utworzenia

    // Konstruktor domyslny wymagany przez JPA
    public Ticket() {
    }

    // Konstruktor z parametrami
    public Ticket(String title, String description, Priority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = Status.NEW; // domyslnie nowe
        this.createdAt = LocalDateTime.now(); // aktualna data
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
