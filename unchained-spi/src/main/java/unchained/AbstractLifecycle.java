package unchained;

import unchained.Lifecycle;

public abstract class AbstractLifecycle implements Lifecycle {

    private final boolean stopped;

    protected AbstractLifecycle() {
        this(false);
    }

    protected AbstractLifecycle(boolean stopped) {
        this.stopped = stopped;
    }

    protected AbstractLifecycle(Lifecycle lifecycle) {
        // TODO: assertion
        this(lifecycle.stopped());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean stopped() {
        return stopped;
    }

}
