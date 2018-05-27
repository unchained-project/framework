package unchained.commons.assertion.impl;

import unchained.commons.assertion.Assertion;
import unchained.commons.assertion.AssertionExceptionProducer;
import unchained.commons.assertion.AssertionResult;
import unchained.commons.assertion.AssertionVerifier;

public class DefaultAssertion<O> implements Assertion<O> {

    private static final AssertionExceptionProducer NOOP_EXCEPTION_PRODUCER = result -> null;
    private final String targetName;
    private final AssertionExceptionProducer<O> exceptionProducer;
    private final AssertionVerifier<O> verifier;

    public DefaultAssertion(String targetName, AssertionVerifier<O> verifier) {
        this(targetName, verifier, null);
    }

    public DefaultAssertion(String targetName, AssertionVerifier<O> verifier,
                            AssertionExceptionProducer<O> exceptionProducer) {
        this.targetName = targetName;
        this.exceptionProducer = exceptionProducer;
        this.verifier = verifier;
    }

    @Override
    public String targetName() {
        return targetName;
    }

    @Override
    public boolean shouldThrowErrors() {
        return exceptionProducer != null;
    }

    @Override
    public AssertionExceptionProducer<O> exceptionProducer() {
        return exceptionProducer == null ? NOOP_EXCEPTION_PRODUCER : exceptionProducer;
    }

    @Override
    public AssertionResult<O> test(O value) {
        final String error;
        if (!verifier.test(value)) {
            error = verifier.describe();
        } else {
            error = null;
        }
        return new ImmutableAssertionResult<>(this, error, value);
    }

}
