package unchained.assertion;

import unchained.assertion.Assertion;
import unchained.assertion.AssertionResult;

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
