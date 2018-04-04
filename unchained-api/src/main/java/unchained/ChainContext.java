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
    L extends ChainLifecycle,
    C extends ChainContext<L, C>> extends Context<L> {

    /**
     * Sets the property to the given value.
     *
     * @param property the property name.
     * @param value the value.
     * @return the context itself to use for chaining method calls.
     */
    C property(String property, Object value);

}
