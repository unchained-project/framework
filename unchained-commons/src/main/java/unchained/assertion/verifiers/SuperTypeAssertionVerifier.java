package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class SuperTypeAssertionVerifier<O extends Class<?>> implements AssertionVerifier<O> {

    private final Class<?> superType;

    public SuperTypeAssertionVerifier(final Class<?> superType) {
        this.superType = superType;
    }

    @Override
    public boolean test(final O value) {
        return superType.isAssignableFrom(value);
    }

    @Override
    public String describe() {
        return "can be assigned to " + superType;
    }

    @Override
    public String describeNegated() {
        return "cannot be assigned to " + superType;
    }

    @Override
    public Type getType() {
        return Class.class;
    }
}
