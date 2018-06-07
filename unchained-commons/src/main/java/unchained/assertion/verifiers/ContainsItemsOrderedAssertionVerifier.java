package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class ContainsItemsOrderedAssertionVerifier<O extends Collection<I>, I> implements AssertionVerifier<O> {

    private final I[] items;

    public ContainsItemsOrderedAssertionVerifier(final I[] items) {
        this.items = items;
    }

    @Override
    public boolean test(final O value) {
        final Iterator<I> iterator = value.iterator();
        for (I item : items) {
            if (!iterator.hasNext() || !Objects.deepEquals(item, iterator.next())) {
                return false;
            }
        }
        return !iterator.hasNext();
    }

    @Override
    public String describe() {
        return "contains all the items " + Arrays.toString(items);
    }

    @Override
    public String describeNegated() {
        return "does not contain all the items " + Arrays.toString(items);
    }

    @Override
    public Type getType() {
        return Collection.class;
    }

}
