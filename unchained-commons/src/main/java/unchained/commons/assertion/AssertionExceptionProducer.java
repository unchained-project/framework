package unchained.commons.assertion;

/**
 * <p>This interface provides a factory for producing exceptions whenever needed.</p>
 * <p>The purpose of the exception producer is to allow for the place of throwing the
 * exception to be closer to that of the place of error, thus preserving as much of
 * the stack trace as possible.</p>
 *
 * @param <O>
 */
@FunctionalInterface
public interface AssertionExceptionProducer<O> {

    /**
     * Produces a new exception based on the result of the assertion. Should return {@code null}
     * if the assertion doesn't have any {@link AssertionResult#hasErrors() errors}.
     * @param result the result of the assertion for which this exception producer is being consulted.
     * @return a RuntimeException which will be thrown once the report is complete. If {@code null} it
     * is an indication that no errors should be reported.
     */
    RuntimeException produce(AssertionResult<O> result);
}

