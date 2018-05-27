package unchained.commons.assertion.impl.verifiers;

import unchained.commons.assertion.AssertionVerifier;
import unchained.commons.contract.Lazy;
import unchained.commons.utils.ReflectionUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ConjunctiveAssertionVerifier<O> implements AssertionVerifier<O> {

    private final Lazy<Type> type;
    private final List<AssertionVerifier<? super O>> verifiers;

    public ConjunctiveAssertionVerifier(AssertionVerifier<? extends O> first, AssertionVerifier<? extends O> second) {
        this(Arrays.asList(first, second));
    }

    public ConjunctiveAssertionVerifier(List<AssertionVerifier<? extends O>> verifiers) {
        //noinspection unchecked
        this.verifiers = verifiers.stream()
                                  .map(verifier -> verifier instanceof ConjunctiveAssertionVerifier
                                          ? ((ConjunctiveAssertionVerifier) verifier).verifiers
                                          : Collections.singletonList(verifier)
                                      )
                                  .map(collection -> (List<AssertionVerifier<O>>) collection)
                                  .flatMap(Collection::stream)
                                  .collect(Collectors.toList());
        type = Lazy.of(() -> ReflectionUtils.resolveCommonAncestor(verifiers.stream()
                                                                            .map(AssertionVerifier::getType)
                                                                            .collect(toList())));
    }

    private AssertionVerifier<? super O> findFailingVerifier(O value) {
        for (AssertionVerifier<? super O> verifier : verifiers) {
            if (!verifier.test(value)) {
                return verifier;
            }
        }
        return null;
    }

    @Override
    public boolean test(O value) {
        return findFailingVerifier(value) == null;
    }

    @Override
    public String describe() {
        return verifiers.stream()
                        .map(AssertionVerifier::describe)
                        .reduce((a, b) -> a + " and " + b)
                        .orElse("");
    }

    @Override
    public String describeNegated() {
        return new DisjunctiveAssertionVerifier<>(verifiers.stream()
                                                           .map(AssertionVerifier::negate)
                                                           .collect(toList()))
                .describe();
    }

    @Override
    public Type getType() {
        return type.get();
    }

}
