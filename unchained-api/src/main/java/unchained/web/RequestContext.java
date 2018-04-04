package unchained.web;

import unchained.ChainContext;

import java.util.concurrent.Executor;

/**
 * TODO: doc
 */
public interface RequestContext extends ChainContext<RequestLifecycle, RequestContext> {

    /**
     * Convenient method to get access to the {@link Executor} bound to this context.
     *
     * @return the {@link Executor} bound to this context.
     */
    default Executor executor() {
        return get(Executor.class);
    }

}
