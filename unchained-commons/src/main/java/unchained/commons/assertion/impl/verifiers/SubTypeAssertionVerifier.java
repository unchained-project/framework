package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class SubTypeAssertionVerifier<O extends Class<?>> implements AssertionVerifier<O> {

    private final Class<?> subType;

    public SubTypeAssertionVerifier(final Class<?> subType) {
        this.subType = subType;
    }

    @Override
    public boolean test(final O value) {
        return value.isAssignableFrom(subType);
    }

    @Override
    public String describe() {
        return "is a sup-type of " + subType;
    }

    @Override
    public String describeNegated() {
        return "is not a sup-type of " + subType;
    }

    @Override
    public Type getType() {
        return Class.class;
    }

}
