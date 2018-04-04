package unchained;

import java.util.concurrent.CompletableFuture;

/**
 * This interface defines the cornerstone of how a chain is handled. A chain of actions is ultimately a root middleware
 * which will call to other middleware, each of which might follow suite, and so on, which essentially forms a
 * multi-level chain of execution. This encourages writing reusable middleware that can be hooked at the appropriate
 * juncture to provide essential functionality.
 *
 * @param <L>  the type of lifecycle bound to this middleware's chain.
 * @param <C> the type of context bound to this middleware's input.
 * @param <I> the type of input this middleware works with.
 * @param <O> the type of output this middleware can deal with.
 * @param <Q> the type of output for this middleware.
 */
public interface Middleware<
    L extends ChainLifecycle,
    C extends ChainContext<L, C>,
    I extends Input<L, C>,
    O extends Output<L, C, I>,
    Q> {

    /**
     * Handles the incoming input and works on the input and output. This is a very generic method which will be called
     * by the framework whenever a chain is in progress. Note that this method is asynchronous in nature.
     *
     * @param input the input.
     * @param output the output.
     * @return a future whose completion represents the completion of the middleware itself.
     * @throws Exception in case the underlying logic causes an error.
     */
    CompletableFuture<Q> execute(I input, O output) throws Exception;

}
