package unchained.error;

import unchained.error.ContextException;

public class NoSuchBeanException extends ContextException {

    public NoSuchBeanException(String beanName) {
        super(String.format("No beans with name <%s> could be found", beanName));
    }

    public NoSuchBeanException(Class<?> beanType) {
        super(String.format("No beans of type <%s> could be found", beanType));
    }

    public NoSuchBeanException(String beanName, Class<?> beanType) {
        super(String.format("No beans with name <%s> and type <%s> could be found", beanName, beanType));
    }

}
