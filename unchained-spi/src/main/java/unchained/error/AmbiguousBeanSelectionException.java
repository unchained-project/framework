package unchained.error;

public class AmbiguousBeanSelectionException extends ContextException {

    public AmbiguousBeanSelectionException(String beanName) {
        super("Too many beans with name <" + beanName + "> were found");
    }

    public AmbiguousBeanSelectionException(Class<?> beanType) {
        super("Too many beans with type <" + beanType + "> were found");
    }

    public AmbiguousBeanSelectionException(String beanName, Class<?> beanType) {
        super("Too many beans with name <" + beanName + "> and type <" + beanType + "> were found");
    }

}
