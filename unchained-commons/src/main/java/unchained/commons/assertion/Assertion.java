package unchained.commons.assertion;

/**
 * <p>This interface declares the contract for a testable assertion that is bound to a target.</p>
 *
 * <p>The target in most cases is a method parameter or a function of a method parameter.</p>
 *
 * @param <O>
 */
public interface Assertion<O> {

    /**
     * @return the name of the target this assertion is bound to
     */
    String targetName();

    /**
     * @return {@code true} if this assertion should throw an error in case of failure. This check
     * must be performed before consulting the {@link #exceptionProducer()} method to ask for an error.
     */
    boolean shouldThrowErrors();

    /**
     * @return returns the exception producer which will ultimately yield the ready-to-throw exception.
     * One reason behind using an exception producer is to limit the distance of the throwing place of the
     * exception and that of the actual place of error, thus, yielding a meaningful stack trace.
     */
    AssertionExceptionProducer<O> exceptionProducer();

    /**
     * This method should be called to test the assertion predicate set forth by this assertion against
     * a concrete value.
     * @param value the value for which the test is being performed.
     * @return The result of the assertion.
     */
    AssertionResult<O> test(O value);

}

