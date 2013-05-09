package fr.joss.jtools.exception;


/**
 * Exception encapsulant toutes les exceptions métier du framework
 *
 * @author jntakpe
 */
public class BusinessException extends FrameworkException {

    public BusinessException(ErrorCode errorCode, Object... params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    public BusinessException(String message, ErrorCode errorCode, Object... params) {
        super(message);
        this.errorCode = errorCode;
        this.params = params;
    }

    public BusinessException(Throwable cause, ErrorCode errorCode, Object... params) {
        super(cause);
        this.errorCode = errorCode;
        this.params = params;
    }

    public BusinessException(String message, Throwable cause, ErrorCode errorCode, Object... params) {
        super(message, cause);
        this.errorCode = errorCode;
        this.params = params;
    }

}
