package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;
import java.util.Collection;

public class CollectionSizeAssertionVerifier<O extends Collection<?>> implements AssertionVerifier<O> {

    private final int size;

    public CollectionSizeAssertionVerifier(int size) {
        this.size = size;
    }

    @Override
    public boolean test(O value) {
        return value.size() == size;
    }

    @Override
    public String describe() {
        return "has size " + size;
    }

    @Override
    public String describeNegated() {
        return "does not have size " + size;
    }

    @Override
    public Type getType() {
        return Collection.class;
    }

}
