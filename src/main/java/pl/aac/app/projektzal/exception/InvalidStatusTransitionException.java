package pl.aac.app.projektzal.exception;

/**
 * Wyjatek rzucany przy nieprawidlowej zmianie statusu ticketa
 * np. z CLOSED na IN_PROGRESS
 */
public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(String currentStatus, String newStatus) {
        super("Nieprawidlowa zmiana statusu z " + currentStatus + " na " + newStatus);
    }

    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}
