package unchained;

import java.util.concurrent.atomic.AtomicBoolean;

import static unchained.Utils.forceNotNull;

/**
 * TODO: doc
 */
public abstract class AbstractChainLifecycle extends AbstractLifecycle implements ChainLifecycle {

    private final AtomicBoolean stopped;

    protected AbstractChainLifecycle() {
        this(false);
    }

    protected AbstractChainLifecycle(Lifecycle lifecycle) {
        this(forceNotNull(lifecycle, "lifecycle").stopped());
    }

    protected AbstractChainLifecycle(boolean stopped) {
        super(stopped);
        this.stopped = new AtomicBoolean(stopped);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        stopped.set(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean stopped() {
        return stopped.get();
    }

}
