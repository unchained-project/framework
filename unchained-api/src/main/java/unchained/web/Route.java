package unchained.web;

import unchained.CompositeMiddleware;
import unchained.Selector;

/**
 * TODO: doc
 *
 * @param <I>
 * @param <O>
 * @param <S>
 * @param <M>
 * @param <R>
 */
public interface Route<I extends Request<I>, O extends Response<I, O>, S extends Selector<I, M>, M,
    R extends Route<I, O, S, M, R>> extends CompositeMiddleware<RequestLifecycle, RequestContext, I, O, S, R, Void> {

    /**
     * TODO: doc
     *
     * @return
     */
    M domain();

}
