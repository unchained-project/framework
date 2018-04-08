package unchained;

/**
 * TODO: doc
 *
 * @param <W>
 * @param <L>
 * @param <C>
 * @param <I>
 * @param <O>
 * @param <M>
 * @param <Q>
 * @param <H>
 */
public interface CompositeMiddleware<
    W extends CompositeMiddleware<W, L, C, I, O, M, Q, H>,
    L extends ChainLifecycle,
    C extends ChainContext<C, L>,
    I extends Input<L, C>,
    O extends Output<L, C, I>,
    M extends Matcher<C, I, H>,
    Q,
    H> extends Middleware<L, C, I, O, Q> {

    /**
     * TODO: doc
     *
     * @param matcher
     * @param first
     * @param rest
     * @param <N>
     * @return
     */
    @SuppressWarnings("unchecked")
    <N extends Middleware<L, C, I, O, ?>> W use(M matcher, N first, N... rest);

}
