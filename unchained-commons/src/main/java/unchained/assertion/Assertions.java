package unchained.assertion;

import unchained.annotation.FunctionalClass;
import unchained.assertion.verifiers.*;
import unchained.error.FunctionalClassInstantiationException;

import java.util.Collection;

/**
 * This class provides several static methods (functions) that can be used to fluently set up assertions.
 * @see Assert
 */
@FunctionalClass
public class Assertions {

    private Assertions() {
        throw new FunctionalClassInstantiationException(getClass());
    }

    /**
     * Asserts that the given value is not {@code null}.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O> AssertionVerifier<O> isNotNull() {
        return new NotNullAssertionVerifier<>();
    }

    /**
     * Asserts that the given value is {@code null}.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O> AssertionVerifier<O> isNull() {
        //noinspection unchecked
        return (AssertionVerifier<O>) isNotNull().negate();
    }

    /**
     * Asserts that the target is equal to the provided value
     * @param value the value to check against.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O> AssertionVerifier<O> is(O value) {
        return new SameValueAssertionVerifier<>(value);
    }

    /**
     * Asserts that the target is an instance of the provided type.
     * @param type the type being checked.
     * @param <Q> the super-type of the target.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O, Q extends O> AssertionVerifier<O> isA(Class<Q> type) {
        return new InstanceOfAssertionVerifier<>(type);
    }

    /**
     * Asserts that the target and the value are referentially the same objects.
     * @param instance the instance which will be checked against the target.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O> AssertionVerifier<O> isSameAs(O instance) {
        return new SameInstanceAssertionVerifier<>(instance);
    }

    /**
     * Asserts that the provided target is an array. This assertion does not hold for {@code null} values.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O> AssertionVerifier<O> isArray() {
        return new IsArrayAssertionVerifier<>();
    }

    /**
     * Asserts that the target is an empty collection.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O extends Collection> AssertionVerifier<O> isEmpty() {
        return new EmptyCollectionAssertionVerifier<>();
    }

    /**
     * Asserts that the provided collection has the given size.
     * @param size the expected size.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O extends Collection> AssertionVerifier<O> hasSize(int size) {
        return new CollectionSizeAssertionVerifier<>(size);
    }

    /**
     * Asserts that the provided target is an empty string.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O extends CharSequence> AssertionVerifier<O> isEmptyString() {
        return new EmptyCharSequenceAssertionVerifier<>();
    }

    /**
     * Asserts that the provided target matches the given pattern. This assertion invokes the
     * {@link String#matches(String) matches(...) method of the String class}.
     *
     * @param pattern the pattern to be checked.
     * @return The assertion verifier
     */
    public static AssertionVerifier<String> matches(String pattern) {
        return new StringPatternAssertionVerifier(pattern);
    }

    /**
     * Checks to see if the provided pivot appears before the target value in a total ordering.
     * @param pivot the pivot
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O extends Comparable<O>> AssertionVerifier<O> isGreaterThan(O pivot) {
        return new GreaterThanAssertionVerifier<>(pivot);
    }

    /**
     * Checks to see if the provided pivot appears after the target value in a total ordering.
     * @param pivot the pivot
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O extends Comparable<O>> AssertionVerifier<O> isLessThan(O pivot) {
        return new LessThanAssertionVerifier<>(pivot);
    }

    /**
     * Asserts that the target is a sub-type of the provided superType.
     * @param superType the super type.
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O extends Class<?>> AssertionVerifier<O> isAssignableTo(Class<?> superType) {
        return new SuperTypeAssertionVerifier<>(superType);
    }

    /**
     * Asserts that the provided target is a super-type of the provided target.
     * @param subType the sub-type
     * @param <O> the type of the value
     * @return The assertion verifier
     */
    public static <O extends Class<?>> AssertionVerifier<O> isAssignableFrom(Class<?> subType) {
        return new SubTypeAssertionVerifier<>(subType);
    }

    /**
     * Asserts that the provided collection has an item matching the provided verifier.
     * @param verifier the verifier that will be used for matching the items
     * @param <O>      the type of the collection
     * @param <I>      the type of the item
     * @return The assertion verifier
     */
    public static <O extends Collection<I>, I> AssertionVerifier<O> containsItem(AssertionVerifier<I> verifier) {
        return new ContainsItemMatchingAssertionVerifier<>(verifier);
    }

    /**
     * Asserts that the provided collection contains all the expected items in the same order.
     * @param items the items to look for
     * @param <O>   the type of the collection
     * @param <I>   the type of the items
     * @return The assertion verifier
     */
    @SafeVarargs
    public static <O extends Collection<I>, I> AssertionVerifier<O> containsItems(I... items) {
        return new ContainsItemsOrderedAssertionVerifier<>(items);
    }

    /**
     * Asserts that the provided collection contains all the expected items in any order.
     * @param items the items to look for
     * @param <O>   the type of the collection
     * @param <I>   the type of the items
     * @return The assertion verifier
     */
    @SafeVarargs
    public static <O extends Collection<I>, I> AssertionVerifier<O> containsItemsInAnyOrder(I... items) {
        return new ContainsItemsUnorderedAssertionVerifier<>(items);
    }

}
