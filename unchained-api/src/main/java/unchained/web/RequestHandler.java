package unchained.web;

import unchained.Middleware;

/**
 * TODO: doc
 *
 * @param <S>
 * @param <T>
 * @param <I>
 * @param <O>
 */
public interface RequestHandler<
    S, T,
    I extends Request<S>,
    O extends Response<S, T, I>> extends Middleware<RequestLifecycle, RequestContext, I, O, Void> {

}
