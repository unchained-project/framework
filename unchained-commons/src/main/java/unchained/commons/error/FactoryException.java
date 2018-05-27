package unchained.commons.error;

public class FactoryException extends FrameworkException {

    public FactoryException(String message) {
        super(message);
    }

    public FactoryException(String message, Throwable cause) {
        super(message, cause);
    }

}
