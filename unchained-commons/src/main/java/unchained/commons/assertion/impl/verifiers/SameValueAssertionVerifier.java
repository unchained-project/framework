package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;
import unchained.commons.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.Objects;

public class SameValueAssertionVerifier<O> implements AssertionVerifier<O> {

    private final O other;

    public SameValueAssertionVerifier(O other) {
        this.other = other;
    }

    @Override
    public boolean test(O value) {
        return Objects.deepEquals(value, other);
    }

    @Override
    public String describe() {
        return "is <" + StringUtils.toString(other) + ">";
    }

    @Override
    public String describeNegated() {
        return "is not " + StringUtils.toString(other);
    }

    @Override
    public Type getType() {
        return other.getClass();
    }

}
