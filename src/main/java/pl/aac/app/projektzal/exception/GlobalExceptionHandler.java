package pl.aac.app.projektzal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Globalna obsluga wyjatkow w aplikacji
 * Przechwytuje wyjatki i zwraca odpowiedni format bledu
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Obsluga bledu: ticket nie znaleziony
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleTicketNotFound(TicketNotFoundException ex) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Obsluga bledu: nieprawidlowa zmiana statusu
    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ErrorMessage> handleInvalidStatusTransition(InvalidStatusTransitionException ex) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Obsluga bledu: nieprawidlowy priorytet
    @ExceptionHandler(InvalidPriorityException.class)
    public ResponseEntity<ErrorMessage> handleInvalidPriority(InvalidPriorityException ex) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Obsluga pozostalych bledow
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
