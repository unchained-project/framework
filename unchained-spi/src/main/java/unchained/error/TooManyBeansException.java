package unchained.error;

public class TooManyBeansException extends ContextException {

    public TooManyBeansException(Class<?> type) {
        super(String.format("Too many beans of type <%s> were found", type));
    }

}
