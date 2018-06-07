package unchained.error;

public class UtilityClassInstantiationException extends UnsupportedOperationException {

    public UtilityClassInstantiationException(Class<?> utiltyClass) {
        super("Class " + utiltyClass.getCanonicalName() + " is a utility class and cannot be instantiated.");
    }
}
