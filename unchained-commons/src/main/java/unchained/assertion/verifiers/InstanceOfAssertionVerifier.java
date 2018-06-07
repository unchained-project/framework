package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class InstanceOfAssertionVerifier<O, Q extends O> implements AssertionVerifier<O> {

    private final Class<Q> type;

    public InstanceOfAssertionVerifier(final Class<Q> type) {
        this.type = type;
    }

    @Override
    public boolean test(final O value) {
        return type.isInstance(value);
    }

    @Override
    public String describe() {
        return "instance of " + type;
    }

    @Override
    public String describeNegated() {
        return "not instance of " + type;
    }

    @Override
    public Type getType() {
        return type;
    }

}
