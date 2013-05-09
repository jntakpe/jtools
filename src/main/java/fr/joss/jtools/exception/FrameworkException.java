package fr.joss.jtools.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Exceptions gérées par le framework
 *
 * @author jntakpe
 */
public abstract class FrameworkException extends RuntimeException {

    ErrorCode errorCode;

    Object[] params;

    FrameworkException() {
    }

    FrameworkException(String message) {
        super(message);
    }

    FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    FrameworkException(Throwable cause) {
        super(cause);
    }

    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) {
        synchronized (s) {
            s.println(this);
            s.println("\t-------------------------------");
            if (getErrorCode() != null) {
                s.println("\t" + getErrorCode() + ":" + getErrorCode().getClass().getName());
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (int i = 0; i < trace.length; i++)
                s.println("\tat " + trace[i]);

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public FrameworkException setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public Object[] setParams(Object... params) {
        this.params = params;
        return params;
    }

}
