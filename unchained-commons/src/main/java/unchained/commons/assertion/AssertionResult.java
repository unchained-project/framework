package unchained.commons.assertion;

/**
 * This interface encapsulates the result of an assertion once it has been evaluated against a concrete value.
 * @param <O> the type of the value against which the assertion has been performed.
 */
public interface AssertionResult<O> {

    /**
     * @return a human readable representation of the error in value that is the result of the assertion.
     */
    String getError();

    /**
     * @return the assertion whose evaluation has resulted in this assertion result.
     */
    Assertion<O> getAssertion();

    /**
     * @return {@code true} if this assertion has resulted in an error.
     */
    default boolean hasErrors() {
        return getError() != null && !getError().isEmpty();
    }

    /**
     * @return the value against which the assertion has been evaluated.
     */
    O getValue();

    /**
     * Reports the result of the assertion. If the assertion has not resulted in any errors, or if
     * it has been set up to not throw any errors in case of problems, this method will do nothing.
     * Otherwise, it will consult the exception producer, and should that be a non-{@code null} value,
     * it will be thrown.
     */
    default void report() {
        if (hasErrors() && getAssertion().shouldThrowErrors()) {
            final AssertionExceptionProducer<O> producer = getAssertion().exceptionProducer();
            final RuntimeException exception = producer.produce(this);
            if (exception != null) {
                throw exception;
            }
        }
    }

}

