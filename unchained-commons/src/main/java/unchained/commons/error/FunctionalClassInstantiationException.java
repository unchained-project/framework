package unchained.commons.error;

public class FunctionalClassInstantiationException extends UnsupportedOperationException {

    public FunctionalClassInstantiationException(Class<?> functionalClass) {
        super("Class " + functionalClass.getCanonicalName() + " is a functional class and cannot be instantiated.");
    }

}
