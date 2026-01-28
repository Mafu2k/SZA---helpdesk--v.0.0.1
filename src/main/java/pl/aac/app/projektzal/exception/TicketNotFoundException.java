package pl.aac.app.projektzal.exception;

/**
 * Wyjatek rzucany gdy ticket o podanym ID nie istnieje
 */
public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super("Nie znaleziono zgloszenia o ID: " + id);
    }

    public TicketNotFoundException(String message) {
        super(message);
    }
}
