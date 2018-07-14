package unchained.error;

public abstract class ContextException extends FrameworkException {

    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }

}
