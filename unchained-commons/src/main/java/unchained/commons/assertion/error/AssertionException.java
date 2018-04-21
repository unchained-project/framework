package unchained.commons.assertion.error;

import unchained.commons.utils.StringUtils;

/**
 * This error should be thrown to indicate that an assertion has failed for a value.
 */
public class AssertionException extends RuntimeException {

    private static final long serialVersionUID = 7114099363543214613L;

    public AssertionException(Object value, RuntimeException cause) {
        super("Assertion failed for value: `" + StringUtils.toString(value) + "`", cause);
    }

}
