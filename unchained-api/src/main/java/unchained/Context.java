package unchained;

import java.util.Collection;
import java.util.Set;

/**
 * This interface encapsulates a context which can be used to carry execution-wide properties
 * and specific information/metadata throughout the life of a an operation.
 *
 * @param <L> the lifecycle type.
 */
public interface Context<L extends Lifecycle> {

    /**
     * Gives access to the lifecycle bound to this context.
     *
     * @return the lifecycle bound to this context.
     */
    L lifecycle();

    /**
     * Returns the object with the provided name.
     *
     * @param name the name.
     * @param <E> the type.
     * @return the object or {@code null} if no such object exists.
     */
    <E> E get(String name);

    /**
     * Returns the object with the given type.
     *
     * @param type the expected type.
     * @param <E> the type.
     * @return the object or {@code null} if no such object exists.
     */
    <E> E get(Class<? super E> type);

    /**
     * Returns an object with the given name and type.
     *
     * @param name the name.
     * @param type the type.
     * @param <E> the type.
     * @return the object or {@code null} if no such object exists.
     */
    <E> E get(String name, Class<E> type);

    /**
     * Returns all objects of the given type.
     *
     * @param type the expected type.
     * @param <E> the type.
     * @return a collection of all objects of the given type.
     */
    <E> Collection<? extends E> getAll(Class<E> type);

    /**
     * Checks to see if an object with the given name can be found in this context.
     *
     * @param name the name to look for.
     * @return {@code true} if the search results in at least one object.
     */
    boolean has(String name);

    /**
     * Checks to see if an object with the given type can be found in this context.
     *
     * @param type the type to look for.
     * @return {@code true} if the search results in at least one object.
     */
    boolean has(Class<?> type);

    /**
     * Checks to see if an object with the given name and type can be found in this context.
     *
     * @param name the name to look for.
     * @param type the type to look for.
     * @return {@code true} if the search results in at least one object.
     */
    boolean has(String name, Class<?> type);

    /**
     * Returns the given property by coercing it to the expected type. Note that no type conversion
     * or deserialization is performed; rather, the object is returned as is in the context and is
     * only coerced to the expected return type.
     *
     * @param name the name of the property.
     * @param <E> the expected type of the property.
     * @return the value of the property or {@code null} if no such property exists.
     * @throws ClassCastException in case the object is being coerced to the wrong type.
     */
    <E> E property(String name);

    /**
     * Checks to see if the named property exists in this context.
     *
     * @param name the name of the property.
     * @return {@code true} in case the property of the given name exists.
     */
    boolean hasProperty(String name);

    /**
     * Returns list of all property names.
     *
     * @return the list of all property names held by this context.
     */
    Set<String> propertyNames();

}
