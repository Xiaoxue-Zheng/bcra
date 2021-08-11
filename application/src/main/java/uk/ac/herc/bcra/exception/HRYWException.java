package uk.ac.herc.bcra.exception;

public class HRYWException extends RuntimeException{

    public HRYWException() {
        super();
    }

    public HRYWException(String message) {
        super(message);
    }

    public HRYWException(String message, Throwable cause) {
        super(message, cause);
    }

    public HRYWException(Throwable cause) {
        super(cause);
    }

    protected HRYWException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
