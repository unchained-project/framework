package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;
import unchained.utils.StringUtils;

import java.lang.reflect.Type;

public class SameInstanceAssertionVerifier<O> implements AssertionVerifier<O> {

    private final O instance;

    public SameInstanceAssertionVerifier(O instance) {
        this.instance = instance;
    }

    @Override
    public boolean test(O value) {
        return value == instance;
    }

    @Override
    public String describe() {
        return "is the same as " + StringUtils.toString(instance);
    }

    @Override
    public String describeNegated() {
        return "is not the same as " + StringUtils.toString(instance);
    }

    @Override
    public Type getType() {
        return instance.getClass();
    }

}
