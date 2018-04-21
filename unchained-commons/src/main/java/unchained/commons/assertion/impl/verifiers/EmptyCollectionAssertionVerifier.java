package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;
import java.util.Collection;

public class EmptyCollectionAssertionVerifier<O extends Collection>
        implements AssertionVerifier<O> {

    @Override
    public boolean test(O value) {
        return value.isEmpty();
    }

    @Override
    public String describe() {
        return "is empty";
    }

    @Override
    public String describeNegated() {
        return "is not empty";
    }

    @Override
    public Type getType() {
        return Collection.class;
    }

}
