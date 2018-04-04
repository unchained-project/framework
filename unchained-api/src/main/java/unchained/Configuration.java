package unchained;

import java.util.Set;

/**
 * This interface encapsulates a malleable configuration which contains arbitrary values.
 *
 * @param <C> the configuration type.
 */
public interface Configuration<C extends Configuration<C>> {

    /**
     * This interface represents a configuration option definition, which lets us do things like statically binding
     * the type of an option, and provide idiomatic option value default definitions without imposing upon the
     * implementation of the configuration classes.
     *
     * @param <T> the static type of this option.
     */
    interface Option<T> {

        /**
         * Returns the default value of this option.
         *
         * @return the default value associated with this option.
         */
        default T defaultValue() {
            return null;
        }

        /**
         * Returns the key of this option.
         *
         * @return the key associated with this option.
         */
        String key();

    }

    /**
     * Returns configuration keys.
     *
     * @return the keys for which this configuration has a configuration value.
     */
    Set<String> keys();

    /**
     * Determines if a configuration key has a corresponding value.
     *
     * @param key the key to check.
     * @return {@code true} if the key in question has been configured.
     */
    boolean has(String key);

    /**
     * Determines if a configuration option has a corresponding value.
     *
     * @param option the option to check.
     * @return {@code true} if the key in question has been configured.
     */
    default boolean has(Option<?> option) {
        return has(option.key());
    }

    /**
     * Checks to see if a given option is enabled. Note that if the option is present but is a non-{@code boolean}
     * option, this can cause an error. To check whether or not a configuration value has been provided for a given
     * key, use {@link #has(String)} instead.
     *
     * @param key the key to check for.
     * @return {@code true} if the key is set to {@code true}.
     */
    default boolean enabled(String key) {
        return has(key) && Boolean.TRUE.equals(get(key));
    }

    /**
     * Checks to see if a given option is enabled. Note that if the option is present but is a non-{@code boolean}
     * option, this can cause an error. To check whether or not a configuration value has been provided for a given
     * key, use {@link #has(Option)} instead.
     *
     * @param option the option to check for.
     * @return {@code true} if the key is set to {@code true}.
     */
    default boolean enabled(Option<?> option) {
        return enabled(option.key());
    }

    /**
     * Checks to see if a given option is disabled. Note that if the option is present but is a non-{@code boolean}
     * option, this can cause an error. To check whether or not a configuration value has been provided for a given
     * key, use {@link #has(String)} instead.
     *
     * @param key the key to check for.
     * @return {@code true} if the key is set to {@code false}.
     */
    default boolean disabled(String key) {
        return !enabled(key);
    }

    /**
     * Checks to see if a given option is disabled. Note that if the option is present but is a non-{@code boolean}
     * option, this can cause an error. To check whether or not a configuration value has been provided for a given
     * key, use {@link #has(Option)} instead.
     *
     * @param option the option to check for.
     * @return {@code true} if the key is set to {@code false}.
     */
    default boolean disabled(Option<?> option) {
        return !enabled(option);
    }

    /**
     * Enables the provided key by setting it to {@code true}.
     *
     * @param key the key to enable.
     * @return the configuration itself to use for chaining method calls.
     */
    default C enable(String key) {
        return set(key, true);
    }

    /**
     * Enables the provided option by setting it to {@code true}.
     *
     * @param option the option to enable.
     * @return the configuration itself to use for chaining method calls.
     */
    default C enable(Option<?> option) {
        return set(option, true);
    }

    /**
     * Disable the provided key by setting it to {@code false}.
     *
     * @param key the key to disable.
     * @return the configuration itself to use for chaining method calls.
     */
    default C disable(String key) {
        return set(key, false);
    }

    /**
     * Disable the provided option by setting it to {@code false}.
     *
     * @param option the option to disable.
     * @return the configuration itself to use for chaining method calls.
     */
    default C disable(Option<?> option) {
        return set(option, false);
    }

    /**
     * Returns the value of the given key, or {@code null} if no such key exists.
     *
     * @param key the key.
     * @param <E> the type.
     * @return the value or {@code null} if it was never configured.
     */
    default <E> E get(String key) {
        return get(key, null);
    }

    /**
     * <p>Returns the value of the given option, or the corresponding default value for this option if it was never
     * defined.</p>
     *
     * <p>This method does not provide any sort of deserialization. Type-casting is done via coercion; i.e. if a
     * value is stored using a type, but read using another, incompatible type, an exception will occur.</p>
     *
     * @param option the option.
     * @param <E> the type.
     * @return the value or the default value configured for the option.
     *
     * @see Option#defaultValue()
     */
    default <E> E get(Option<E> option) {
        return get(option.key(), option.defaultValue());
    }

    /**
     * Returns the configured value for this key, or the provided default value if it was never configured.
     *
     * @param key the key.
     * @param defaultValue the default value.
     * @param <E> the type.
     * @return the configured value or the provided default.
     */
    <E> E get(String key, E defaultValue);

    /**
     * Changes the value of the provided key.
     *
     * @param key the key.
     * @param value the value.
     * @return the configuration itself to use for chaining method calls.
     */
    C set(String key, Object value);

    /**
     * Sets the value of the provided option to the given value.
     *
     * @param option the option.
     * @param value the new value.
     * @param <E> the type of the value.
     * @return the configuration itself to use for chaining method calls.
     */
    default <E> C set(Option<E> option, Object value) {
        return set(option.key(), value);
    }

    /**
     * Removes the value of the provided key from the configuration and returns its previous value if it was ever set.
     *
     * @param key the key to unset.
     * @param <E> the type of the option.
     * @return the previously configured value of the configuration option, or {@code null} if it was never set.
     */
    <E> E unset(String key);

    /**
     * <p>Removes the value of the provided option from the configuration and returns its previous value if it was ever
     * set.</p>
     *
     * <pThis method does not provide any sort of deserialization. Type-casting is done via coercion; i.e. if a value
     * is stored using a type, but read using another, incompatible type, an exception will occur.</p>
     *
     * @param option the option to unset.
     * @param <E> the type of the option.
     * @return the previously configured value of the configuration option, or {@code null} if it was never set.
     */
    default <E> E unset(Option<?> option) {
        return unset(option.key());
    }

}
