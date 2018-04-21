package unchained;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO: doc
 *
 * @param <C>
 * @param <L>
 */
public abstract class AbstractChainContext<C extends ChainContext<C, L>, L extends ChainLifecycle>
    extends AbstractNestableContext<L> implements ChainContext<C, L> {

    private final Map<String, Object> properties;

    protected AbstractChainContext(C parent) {
        // TODO: assertion
        this(parent, parent.lifecycle(), null);
    }

    protected AbstractChainContext(ApplicationContext applicationContext, L lifecycle) {
        // TODO: assertion
        this(applicationContext, lifecycle, null);
    }

    protected AbstractChainContext(Context<? extends Lifecycle> parent, L lifecycle, Map<String, Object> properties) {
        // TODO: assertion(parent, lifecycle)
        super(parent, lifecycle);
        this.properties = properties == null ? new ConcurrentHashMap<>() : properties;
    }

    @Override
    protected Map<String, Object> properties() {
        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public C property(String name, Object value) {
        // TODO: assertion(name)
        if (value == null) {
            properties().remove(name);
        } else {
            properties().put(name, value);
        }
        return (C) this;
    }

}

