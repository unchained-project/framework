package unchained;

/**
 * TODO: doc
 *
 * @param <C>
 * @param <I>
 */
public interface Matcher<
    C extends ChainContext<? extends ChainLifecycle, C>,
    I extends Input<? extends ChainLifecycle, C>> {

    /**
     * TODO: doc
     *
     * @param <C>
     * @param <I>
     * @param <M>
     * @param <S>
     */
    interface Compiler<
        C extends ChainContext<? extends ChainLifecycle, C>,
        I extends Input<? extends ChainLifecycle, C>,
        M extends Matcher<C, I>,
        S> {

        /**
         * TODO: doc
         *
         * @param expression
         * @return
         */
        M compile(S expression);

    }

    /**
     * TODO: doc
     *
     * @param input
     * @return
     */
    boolean test(I input);

}
