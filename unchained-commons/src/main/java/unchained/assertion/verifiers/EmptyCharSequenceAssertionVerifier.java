package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class EmptyCharSequenceAssertionVerifier<O extends CharSequence>
        implements AssertionVerifier<O> {

    @Override
    public boolean test(O value) {
        return value.length() == 0;
    }

    @Override
    public String describe() {
        return "is an empty string";
    }

    @Override
    public String describeNegated() {
        return "is not an empty string";
    }

    @Override
    public Type getType() {
        return CharSequence.class;
    }

}
