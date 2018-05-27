package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;

public class StringPatternAssertionVerifier implements AssertionVerifier<String> {

    private final String pattern;

    public StringPatternAssertionVerifier(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean test(final String value) {
        return value.matches(pattern);
    }

    @Override
    public String describe() {
        return "matches " + pattern;
    }

    @Override
    public String describeNegated() {
        return "does not match " + pattern;
    }

    @Override
    public Type getType() {
        return String.class;
    }

}
