package pl.aac.app.projektzal.exception;

/**
 * Wyjatek rzucany przy nieprawidlowym priorytecie
 */
public class InvalidPriorityException extends RuntimeException {

    public InvalidPriorityException(String priority) {
        super("Nieprawidlowy priorytet: " + priority + ". Dozwolone: LOW, MEDIUM, HIGH, CRITICAL");
    }

    public InvalidPriorityException(String message, boolean custom) {
        super(message);
    }
}
