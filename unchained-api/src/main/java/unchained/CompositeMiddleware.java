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
 */
public interface CompositeMiddleware<
    W extends CompositeMiddleware<W, L, C, I, O, M, Q>,
    L extends ChainLifecycle,
    C extends ChainContext<L, C>,
    I extends Input<L, C>,
    O extends Output<L, C, I>,
    M extends Matcher<C, I>,
    Q> extends Middleware<L, C, I, O, Q> {

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
