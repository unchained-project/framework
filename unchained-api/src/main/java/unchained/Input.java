package unchained;

/**
 * This class represents an actionable input which is used to start off a chain.
 *
 * @param <L> the lifecycle type.
 * @param <C> the context type.
 */
public interface Input<
    L extends ChainLifecycle,
    C extends ChainContext<C, L>> {

    /**
     * Gives access to the context associated with this input.
     *
     * @return the context associated with this input.
     */
    C context();

    /**
     * Indicates if the input is ready to be consumed.
     *
     * @return {@code true} in case the input is ready to be consumed.
     */
    boolean ready();

}
