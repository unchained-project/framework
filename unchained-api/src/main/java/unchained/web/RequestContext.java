package unchained.web;

import unchained.ChainContext;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/**
 * TODO: doc
 */
public interface RequestContext extends ChainContext<RequestLifecycle, RequestContext> {

    default Executor executor() {
        return has(Executor.class) ? get(Executor.class) : ForkJoinPool.commonPool();
    }

}
