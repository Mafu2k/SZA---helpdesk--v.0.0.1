package pl.aac.app.projektzal.dto;

import java.time.LocalDateTime;

/**
 * DTO do zwracania danych zgloszenia w API
 * Zawiera wszystkie pola potrzebne klientowi
 */
public class TicketResponseDto {

    private Long id; // identyfikator
    private String title; // tytul
    private String description; // opis
    private String priority; // priorytet
    private String status; // status
    private LocalDateTime createdAt; // data utworzenia

    // Konstruktor domyslny
    public TicketResponseDto() {
    }

    // Konstruktor z parametrami
    public TicketResponseDto(Long id, String title, String description,
            String priority, String status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
