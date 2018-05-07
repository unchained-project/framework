package unchained;

import static unchained.Utils.forceNotNull;

public abstract class AbstractLifecycle implements Lifecycle {

    private final boolean stopped;

    protected AbstractLifecycle() {
        this(false);
    }

    protected AbstractLifecycle(boolean stopped) {
        this.stopped = stopped;
    }

    protected AbstractLifecycle(Lifecycle lifecycle) {
        this(forceNotNull(lifecycle, "lifecycle").stopped());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean stopped() {
        return stopped;
    }

}
