package unchained.error;

public class NamespaceInstantiationException extends UnsupportedOperationException {

    public NamespaceInstantiationException(Class<?> namespace) {
        super("Class " + namespace.getCanonicalName() + " represents a namespace and cannot be instantiated.");
    }
}
