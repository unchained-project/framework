package unchained.assertion.verifiers;

import unchained.assertion.AssertionVerifier;

import java.lang.reflect.Type;
import java.util.Collection;

public class ContainsItemMatchingAssertionVerifier<O extends Collection<I>, I> implements AssertionVerifier<O> {

    private final AssertionVerifier<I> verifier;

    public ContainsItemMatchingAssertionVerifier(
            final AssertionVerifier<I> verifier) {
        this.verifier = verifier;
    }

    @Override
    public boolean test(final O value) {
        return value.stream().anyMatch(verifier::test);
    }

    @Override
    public String describe() {
        return "has an item which " + verifier.describe();
    }

    @Override
    public String describeNegated() {
        return "does not have an item which " + verifier.describe();
    }

    @Override
    public Type getType() {
        return Collection.class;
    }

}
