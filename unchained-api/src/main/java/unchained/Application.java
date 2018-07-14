package unchained;

/**
 * TODO: doc
 *
 * @param <L>
 * @param <C>
 * @param <I>
 * @param <O>
 * @param <S>
 * @param <A>
 */
public interface Application<L extends ChainLifecycle, C extends ChainContext<L, C>, I extends Input<L, C>,
    O extends Output<L, C, I>, S extends Selector<I, ?>, A extends Application<L, C, I, O, S, A>>
        extends CompositeMiddleware<L, C, I, O, S, A, Void> {

    /**
     * TODO: doc
     * @return
     */
    ApplicationContext context();

}
