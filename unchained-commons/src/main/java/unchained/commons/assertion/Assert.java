package unchained.commons.assertion;

import unchained.commons.annotation.FunctionalClass;
import unchained.commons.assertion.error.AssertionException;
import unchained.commons.assertion.error.GeneralAssertionException;
import unchained.commons.assertion.impl.DefaultAssertion;
import unchained.commons.error.FunctionalClassInstantiationException;

/**
 * <p>This class provides a set of functions (static methods) which can be used to assert conditions.</p>
 * @see #assertThat(Object, String, AssertionVerifier)
 * @see #withoutThrowingErrors()
 * @see #failingWith(AssertionExceptionProducer)
 */
@FunctionalClass
public class Assert {

    private Assert() {
        throw new FunctionalClassInstantiationException(getClass());
    }

    /**
     * <p>Sets up an assertion that does not throw any exceptions upon failure.</p>
     * <p>For example:</p>
     * <pre><code>
     *     Assertion&lt;Integer&gt; assertion = withoutThrowingErrors().assertThat(x, "x", isLessThan(10));
     * </code></pre>
     * @param <O> the type of the value this assertion will be checking.
     * @return the continuation of the assertion DSL.
     */
    public static <O> AssertThat<O> withoutThrowingErrors() {
        return (value, name, predicate) -> new DefaultAssertion<>(name, predicate);
    }

    /**
     * <p>Sets up the exception producer for the assertion.</p>
     * <p>For example:</p>
     * <pre><code>
     *     failingWith(() -> new ItemNotFoundException()).assertThat(items.contains(item), "contains(item)", is(true));
     * </code></pre>
     * @param producer the exception producer to be used when the assertion fails.
     * @param <O> the type of the value this assertion will be checking.
     * @return the continuation of the assertion DSL.
     */
    public static <O> AssertThat<O> failingWith(AssertionExceptionProducer<O> producer) {
        return (value, name, predicate) -> {
            Assertion<O> assertion = new DefaultAssertion<>(name, predicate, producer);
            final AssertionResult<O> result = assertion.test(value);
            try {
                result.report();
            } catch (RuntimeException e) {
                throw new AssertionException(value, e);
            }
            return assertion;
        };
    }

    /**
     * <p>This will be the most commonly used method of the assertions method provided herein. This method
     * should be called to set up an assertion about a named target.</p>
     * <p>For example:</p>
     * <pre><code>
     *     assertThat(options, "options", isNotNull());
     * </code></pre>
     * @param value     the value being checked
     * @param name      the name of the target, which will be used for error reporting.
     * @param predicate the predicate which will verify the assertion
     * @return the assertion that was created by this statement, which can be later tested.
     */
    public static <O> Assertion<O> assertThat(O value, String name, AssertionVerifier<O>
            predicate) {
        //noinspection unchecked
        final AssertionExceptionProducer<O> producer = result -> new GeneralAssertionException
                (result.getError(), result.getAssertion());
        Assertion<O> assertion = new DefaultAssertion<>(name, predicate, producer);
        final AssertionResult<O> result = assertion.test(value);
        try {
            result.report();
        } catch (RuntimeException e) {
            throw new AssertionException(value, e);
        }
        return assertion;
    }

    public interface AssertThat<O> {

        /**
         * <p>This method should be called to set up an assertion about a named target.</p>
         * <p>For example:</p>
         * <pre><code>
         *     assertThat(options, "options", isNotNull());
         * </code></pre>
         * @param value     the value being checked
         * @param name      the name of the target, which will be used for error reporting.
         * @param predicate the predicate which will verify the assertion
         * @return the assertion that was created by this statement, which can be later tested.
         */
        Assertion<O> assertThat(O value, String name, AssertionVerifier<O> predicate);

    }

}
