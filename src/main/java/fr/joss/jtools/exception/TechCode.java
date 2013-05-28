package fr.joss.jtools.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * Codes d'erreurs relatifs aux exceptions techniques
 *
 * @author jntakpe
 */
public enum TechCode implements ErrorCode {

    CONSTRAINT_VIOLATION(DataIntegrityViolationException.class, "Violation de contrainte d'intégrité."),
    OPTIMISTIC_LOCKING(OptimisticLockingFailureException.class, "Mise à jour concurrente détectée."),
    NO_RESULT(EmptyResultDataAccessException.class, "Aucun résultat trouvé.");

    private final Class<?> sourceException;

    private final String message;

    private TechCode(Class<?> sourceException, String message) {
        this.sourceException = sourceException;
        this.message = message;
    }

    public Class<?> getSourceException() {
        return sourceException;
    }

    public String getMessage() {
        return message;
    }

}
