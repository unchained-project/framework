package unchained.commons.assertion.impl;

import unchained.commons.assertion.Assertion;
import unchained.commons.assertion.AssertionResult;

public abstract class AbstractAssertionResult<O> implements AssertionResult<O> {

    private final Assertion<O> assertion;

    public AbstractAssertionResult(Assertion<O> assertion) {
        this.assertion = assertion;
    }

    @Override
    public Assertion<O> getAssertion() {
        return assertion;
    }

}
