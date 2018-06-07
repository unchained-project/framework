package unchained.error;

public class FactoryNotFoundException extends FactoryException {

    public FactoryNotFoundException(Class<?> type) {
        super(message(type));
    }

    public FactoryNotFoundException(Class<?> type, Throwable cause) {
        super(message(type), cause);
    }

    private static String message(Class<?> type) {
        return String.format("No suitable factory for type <%s> could be found", type);
    }

}
