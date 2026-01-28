package pl.aac.app.projektzal.dto;

/**
 * DTO do tworzenia i aktualizacji zgloszenia
 * Nie zawiera id, status, createdAt - te sa ustawiane automatycznie
 */
public class TicketRequestDto {

    private String title; // tytul zgloszenia
    private String description; // opis problemu
    private String priority; // priorytet jako String

    // Konstruktor domyslny
    public TicketRequestDto() {
    }

    // Konstruktor z parametrami
    public TicketRequestDto(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    // Gettery i settery

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
}
