package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class LessThanAssertionVerifier<O extends Comparable<O>> implements AssertionVerifier<O> {

    private final O pivot;

    public LessThanAssertionVerifier(final O pivot) {
        this.pivot = pivot;
    }

    @Override
    public boolean test(final O value) {
        return pivot.compareTo(value) > 0;
    }

    @Override
    public String describe() {
        return "is less than " + pivot;
    }

    @Override
    public String describeNegated() {
        return "is greater than or equal to " + pivot;
    }

    @Override
    public Type getType() {
        return pivot.getClass();
    }

}
