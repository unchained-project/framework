package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public class ContainsItemsUnorderedAssertionVerifier<O extends Collection<I>, I> implements AssertionVerifier<O> {

    private final I[] items;

    public ContainsItemsUnorderedAssertionVerifier(final I[] items) {
        this.items = items;
    }

    @Override
    public boolean test(final O value) {
        if (value.size() != items.length) {
            return false;
        }
        for (I item : items) {
            if (!value.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String describe() {
        return "has items " + Arrays.toString(items) + " in any order";
    }

    @Override
    public String describeNegated() {
        return "does not have items " + Arrays.toString(items);
    }

    @Override
    public Type getType() {
        return Collection.class;
    }

}
