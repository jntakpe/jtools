package fr.joss.jtools.exception;

/**
 * Exception encapsulant les exceptions techniques (accès DB ou accès fichier ...)
 *
 * @author jntakpe
 */
public class TechException extends FrameworkException {

    public TechException(ErrorCode errorCode, Object... params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    public TechException(String message, ErrorCode errorCode, Object... params) {
        super(message);
        this.errorCode = errorCode;
        this.params = params;
    }

    public TechException(Throwable cause, ErrorCode errorCode, Object... params) {
        super(cause);
        this.errorCode = errorCode;
        this.params = params;
    }

    public TechException(String message, Throwable cause, ErrorCode errorCode, Object... params) {
        super(message, cause);
        this.errorCode = errorCode;
        this.params = params;
    }
}
