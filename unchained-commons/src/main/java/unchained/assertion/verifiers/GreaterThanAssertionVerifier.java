package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class GreaterThanAssertionVerifier<O extends Comparable<O>> implements AssertionVerifier<O> {

    private final O pivot;

    public GreaterThanAssertionVerifier(final O pivot) {
        this.pivot = pivot;
    }

    @Override
    public boolean test(final O value) {
        return pivot.compareTo(value) < 0;
    }

    @Override
    public String describe() {
        return "is greater than " + pivot;
    }

    @Override
    public String describeNegated() {
        return "is less than or equal to " + pivot;
    }

    @Override
    public Type getType() {
        return pivot.getClass();
    }

}
