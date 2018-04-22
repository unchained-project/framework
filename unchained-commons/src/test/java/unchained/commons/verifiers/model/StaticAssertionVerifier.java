package unchained.commons.verifiers.model;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class StaticAssertionVerifier<O> implements AssertionVerifier<O> {

    private final boolean result;
    private final List<O> values;

    public StaticAssertionVerifier(final boolean result) {
        this.result = result;
        values = new LinkedList<>();
    }

    @Override
    public boolean test(final O value) {
        values.add(value);
        return result;
    }

    public List<O> getValues() {
        return values;
    }

    @Override
    public String describe() {
        return "is something";
    }

    @Override
    public String describeNegated() {
        return "is not something";
    }

    @Override
    public Type getType() {
        return Object.class;
    }

}
