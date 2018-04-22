package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class NegatedAssertionVerifier<O> implements AssertionVerifier<O> {

    private final AssertionVerifier<O> original;

    public NegatedAssertionVerifier(AssertionVerifier<O> original) {
        this.original = original;
    }

    @Override
    public boolean test(O value) {
        return !original.test(value);
    }

    @Override
    public String describe() {
        return original.describeNegated();
    }

    @Override
    public String describeNegated() {
        return original.describe();
    }

    @Override
    public Type getType() {
        return original.getType();
    }

}
