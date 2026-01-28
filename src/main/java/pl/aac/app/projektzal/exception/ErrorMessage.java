package pl.aac.app.projektzal.exception;

import java.time.LocalDateTime;

/**
 * Klasa reprezentujaca format odpowiedzi bledu w API
 */
public class ErrorMessage {

    private int status; // kod HTTP
    private String error; // nazwa bledu
    private String message; // komunikat
    private LocalDateTime timestamp; // czas wystapienia

    // Konstruktor
    public ErrorMessage(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Gettery

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
