package unchained.commons.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isA;

/**
 * This class provides a way for working with generic type tokens in Java.
 *
 * @param <T> the type being wrapped
 * @see <a href="http://gafter.blogspot.nl/2006/12/super-type-tokens.html">Neal Gafter's article</a>
 */
public abstract class TypeReference<T> {

    private final Type type;

    public TypeReference() {
        final Class<?> actualSubClass = findActualSubClass(getClass());
        final Type superclass = actualSubClass.getGenericSuperclass();
        assertThat(superclass, "superclass", isA(ParameterizedType.class));
        type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    /**
     * This constructor is used to directly override the {@link #getType()} exposed via this class.
     * Instead of dealing with this constructor directly, use {@link #forType(Type)}.
     * @param type the type that this instance is wrapping.
     */
    private TypeReference(final Type type) {
        this.type = type;
    }

    /**
     * @return the type wrapped by this class.
     */
    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other != null
                && getClass() == other.getClass()
                && Objects.equals(type, ((TypeReference<?>) other).type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "TypeReference<" + type + ">";
    }

    /**
     * Wraps an instance of {@link Type} and coerces it to the expected generic type.
     * @param type the type being wrapped.
     * @param <T> The generic type that is being used for coercion.
     * @return the wrapped type reference.
     */
    public static <T> TypeReference<T> forType(Type type) {
        return new TypeReference<T>(type) {
        };
    }

    /**
     * Will look up the actual class that is the immediate child of this class. This is so that
     * if class {@code MyClass extends TypeReference<T>} where to be used, then type {@code T}
     * would still be looked up properly.
     * @param child the class that is assumed to be the child.
     * @return The class that directly extends this class.
     */
    private static Class<?> findActualSubClass(Class<?> child) {
        Class<?> parent = child.getSuperclass();
        if (Object.class == parent) {
            throw new IllegalStateException("Expected to find TypeReference");
        } else if (TypeReference.class == parent) {
            return child;
        } else {
            return findActualSubClass(parent);
        }
    }

}
