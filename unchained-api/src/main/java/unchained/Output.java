package unchained;

/**
 * This interface represents an output which is used at the end of a chain to communicate with the client.
 *
 * @param <L> the lifecycle type.
 * @param <C> the context type.
 * @param <I> the input type.
 */
public interface Output<
    L extends ChainLifecycle,
    C extends ChainContext<C, L>,
    I extends Input<L, C>> {

    /**
     * Gives access to the input associated with this output.
     *
     * @return the input associated with this output.
     */
    I input();

    /**
     * Indicates if the output is ready to be returned.
     *
     * @return {@code true} in case the output is ready to be communicated back to the client.
     */
    boolean ready();

}
