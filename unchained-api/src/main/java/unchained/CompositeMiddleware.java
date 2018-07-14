package unchained;

/**
 * TODO: doc
 *
 * @param <L>
 * @param <C>
 * @param <I>
 * @param <O>
 * @param <S>
 * @param <W>
 * @param <K>
 */
public interface CompositeMiddleware<L extends ChainLifecycle, C extends ChainContext<L, C>, I extends Input<L, C>,
    O extends Output<L, C, I>, S extends Selector<I, ?>, W extends CompositeMiddleware<L, C, I, O, S, W, K>, K>
        extends Middleware<L, C, I, O, K> {

    /**
     * TODO: doc
     *
     * @param first
     * @param rest
     * @param <N>
     * @return
     */
    <N extends Middleware<L, C, I, O, ?>> W use(N first, N... rest);

    /**
     * TODO: doc
     *
     * @param selector
     * @param first
     * @param rest
     * @param <N>
     * @return
     */
    <N extends Middleware<L, C, I, O, ?>> W use(S selector, N first, N... rest);

}
