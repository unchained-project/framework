package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class IsArrayAssertionVerifier<O> implements AssertionVerifier<O> {

    @Override
    public boolean test(O value) {
        return value != null && value.getClass().isArray();
    }

    @Override
    public String describe() {
        return "is an array";
    }

    @Override
    public String describeNegated() {
        return "is not an array";
    }

    @Override
    public Type getType() {
        return null;
    }

}
