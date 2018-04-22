package unchained.commons.assertion;

import unchained.commons.assertion.impl.verifiers.ConjunctiveAssertionVerifier;
import unchained.commons.assertion.impl.verifiers.DisjunctiveAssertionVerifier;
import unchained.commons.assertion.impl.verifiers.NegatedAssertionVerifier;

import java.lang.reflect.Type;

/**
 * This interface provides a contract for verifying whether or not an assertion holds against a
 * concrete value.
 * @param <O> the type of the value against which the assertion is being verified.
 */
public interface AssertionVerifier<O> {

    /**
     * This method will be called to test whether or not the assertion holds.
     * @param value the value against which the test will be conducted.
     * @return {@code true} if the assertion holds.
     */
    boolean test(O value);

    /**
     * This method will be called whenever the assertion fails, to describe the assertion it was
     * trying to verify in simple, human-readable terms.
     * @return a human readable description of the assertion.
     */
    String describe();

    /**
     * This method will be called whenever the opposite of the assertion fails, to describe the
     * negated version of the assertion it was trying to test.
     * @return a human readable description of the opposite of the assertion.
     */
    String describeNegated();

    /**
     * Allows for conjunction of multiple assertions. The assertion will "short-circuit" the moment
     * any of the verifications fail.
     * @param that the other assertion.
     * @return an assertion which when called will evaluate first the current assertion, then the one
     * being conjuncted.
     */
    default AssertionVerifier<O> and(AssertionVerifier<? extends O> that) {
        return new ConjunctiveAssertionVerifier<>(this, that);
    }

    /**
     * Allows for disjunction of multiple assertions. The assertion will "short-circuit" the moment
     * any of the verifications holds.
     * @param that the other assertion being tested.
     * @return an assertion which will first evaluate this assertion, and if it doesn't hold, will call
     * the next one.
     */
    default AssertionVerifier<O> or(AssertionVerifier<? extends O> that) {
        return new DisjunctiveAssertionVerifier<>(this, that);
    }

    /**
     * @return An assertion which checks the opposite fact as that proposed by the current assertion.
     */
    default AssertionVerifier<O> negate() {
        return new NegatedAssertionVerifier<>(this);
    }

    /**
     * @return the type of object that is appraised with this verifier.
     */
    Type getType();

}

