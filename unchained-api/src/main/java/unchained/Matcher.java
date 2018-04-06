package unchained;

/**
 * TODO: doc
 *
 * @param <C>
 * @param <I>
 * @param <E>
 */
public interface Matcher<
    C extends ChainContext<? extends ChainLifecycle, C>,
    I extends Input<? extends ChainLifecycle, C>,
    E> {

    /**
     * TODO: doc
     *
     * @param <C>
     * @param <I>
     * @param <M>
     * @param <S>
     * @param <T>
     */
    interface Compiler<
        C extends ChainContext<? extends ChainLifecycle, C>,
        I extends Input<? extends ChainLifecycle, C>,
        M extends Matcher<C, I, T>,
        S,
        T> {

        /**
         * TODO: doc
         *
         * @param expression
         * @return
         */
        M compile(S expression);

    }

    interface Hit<T> {

        boolean matched();

        T value();

    }

    /**
     * TODO: doc
     *
     * @param input
     * @return
     */
    Hit<E> test(I input);

}
