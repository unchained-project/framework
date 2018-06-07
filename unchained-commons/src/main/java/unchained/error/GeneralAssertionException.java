package unchained.error;

import unchained.assertion.Assertion;

/**
 * This exception is thrown to indicate a general assertion failure. This happens when you set up an
 * assertion which fails, but do not provide a specific exception that you want to match to the failure.
 */
public class GeneralAssertionException extends RuntimeException {

    private static final long serialVersionUID = -6123469051556768170L;

    public GeneralAssertionException(String message, Assertion<?> assertion) {
        super("Expected: " + assertion.targetName() + " " + message);
    }
}
