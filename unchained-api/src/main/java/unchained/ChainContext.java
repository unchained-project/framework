package unchained;

/**
 * This interface encapsulates a context which can be used to carry execution-wide properties and chain-specific
 * information/metadata throughout the life of a chain of an input/output command as it is being handled by
 * middleware.
 *
 * @param <L> the lifecycle type.
 * @param <C> the context type.
 */
public interface ChainContext<
    C extends ChainContext<C, L>,
    L extends ChainLifecycle
    > extends Context<L> {

    /**
     * Sets the property to the given value.
     *
     * @param name the property name.
     * @param value the value.
     * @return the context itself to use for chaining method calls.
     */
    C property(String name, Object value);

}
