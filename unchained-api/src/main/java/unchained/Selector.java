package unchained;

import java.util.Map;

/**
 * TODO: doc
 *
 * @param <I>
 * @param <M>
 */
public interface Selector<I extends Input<? extends ChainLifecycle, ? extends ChainContext>, M> {

    /**
     * TODO: doc
     *
     * @param <S>
     */
    interface Compiler<S extends Selector<? extends Input<? extends ChainLifecycle, ? extends ChainContext>, ?>> {

        /**
         * TODO: doc
         *
         * @param expressions
         * @return
         */
        S compile(String... expressions);

    }

    interface Hit<M> {

        /**
         * TODO: doc
         *
         * @return
         */
        boolean matched();

        /**
         * TODO: doc
         *
         * @return
         */
        M value();

    }

    /**
     * TODO: doc
     *
     * @param input
     * @return
     */
    Hit<M> test(I input);

}
