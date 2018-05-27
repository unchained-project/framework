package unchained;

/**
 * Mutable configuration.
 *
 * @param <M>
 */
public interface MutableConfiguration<M extends MutableConfiguration> extends Configuration {

    /**
     * Enables the provided key by setting it to {@code true}.
     *
     * @param key the key to enable.
     * @return the configuration itself to use for chaining method calls.
     */
    default M enable(String key) {
        return set(key, true);
    }

    /**
     * Enables the provided option by setting it to {@code true}.
     *
     * @param option the option to enable.
     * @return the configuration itself to use for chaining method calls.
     */
    default M enable(Option<Boolean> option) {
        return set(option, true);
    }

    /**
     * Disable the provided key by setting it to {@code false}.
     *
     * @param key the key to disable.
     * @return the configuration itself to use for chaining method calls.
     */
    default M disable(String key) {
        return set(key, false);
    }

    /**
     * Disable the provided option by setting it to {@code false}.
     *
     * @param option the option to disable.
     * @return the configuration itself to use for chaining method calls.
     */
    default M disable(Option<Boolean> option) {
        return set(option, false);
    }

    /**
     * Changes the value of the provided key.
     *
     * @param key the key.
     * @param value the value.
     * @return the configuration itself to use for chaining method calls.
     */
    <E> M set(String key, E value);

    /**
     * Sets the value of the provided option to the given value.
     *
     * @param option the option.
     * @param value the new value.
     * @param <E> the type of the value.
     * @return the configuration itself to use for chaining method calls.
     */
    default <E> M set(Option<E> option, E value) {
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
