package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class NotNullAssertionVerifier<O> implements AssertionVerifier<O> {

    @Override
    public boolean test(O value) {
        return value != null;
    }

    @Override
    public String describe() {
        return "is not null";
    }

    @Override
    public String describeNegated() {
        return "is null";
    }

    @Override
    public Type getType() {
        return null;
    }

}
