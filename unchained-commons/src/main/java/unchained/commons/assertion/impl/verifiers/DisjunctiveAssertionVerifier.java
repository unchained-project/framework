package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;
import unchained.commons.contract.Lazy;
import unchained.commons.utils.ReflectionUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DisjunctiveAssertionVerifier<O> implements AssertionVerifier<O> {

    private final List<AssertionVerifier<? super O>> verifiers;
    private final Lazy<Type> type;

    public DisjunctiveAssertionVerifier(AssertionVerifier<? extends O> first, AssertionVerifier<? extends O> second) {
        this(Arrays.asList(first, second));
    }

    public DisjunctiveAssertionVerifier(List<AssertionVerifier<? extends O>> verifiers) {
        //noinspection unchecked
        this.verifiers = verifiers.stream()
                                  .map(verifier -> verifier instanceof DisjunctiveAssertionVerifier
                                          ? ((DisjunctiveAssertionVerifier) verifier).verifiers
                                          : Collections.singletonList(verifier)
                                      )
                                  .map(collection -> (List<AssertionVerifier<O>>) collection)
                                  .flatMap(Collection::stream)
                                  .collect(toList());
        type = Lazy.of(() -> ReflectionUtils.resolveCommonAncestor(verifiers.stream()
                                                                            .map(AssertionVerifier::getType)
                                                                            .collect(toList())));
    }

    @Override
    public boolean test(O value) {
        for (AssertionVerifier<? super O> verifier : verifiers) {
            if (verifier.test(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String describe() {
        return verifiers.stream()
                        .map(AssertionVerifier::describe)
                        .reduce((a, b) -> a + " or " + b)
                        .orElse("");
    }

    @Override
    public String describeNegated() {
        return new ConjunctiveAssertionVerifier<>(verifiers.stream()
                                                           .map(AssertionVerifier::negate)
                                                           .collect(toList()))
                .describe();
    }

    @Override
    public Type getType() {
        return type.get();
    }

}
