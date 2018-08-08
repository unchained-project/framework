package unchained.web;

import unchained.Middleware;

import java.util.concurrent.CompletableFuture;

/**
 * TODO: doc
 *
 * @param <I>
 * @param <O>
 */
public interface RequestHandler<I extends Request<I>, O extends Response<I,O>>
    extends Middleware<RequestLifecycle, RequestContext, I, O, Void> {

    void handle(I input, O output);

    @Override
    default CompletableFuture<Void> execute(I input, O output) throws Exception {
        return CompletableFuture
            .runAsync(() -> handle(input, output), input.context().executor());
    }
}
