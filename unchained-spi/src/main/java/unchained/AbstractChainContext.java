package unchained;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static unchained.Utils.forceNotNull;
import static unchained.assertion.Assert.assertThat;
import static unchained.assertion.Assertions.isNotNull;

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
        this(parent, forceNotNull(parent, "parent").lifecycle(), null);
    }

    protected AbstractChainContext(ApplicationContext applicationContext, L lifecycle) {
        this(forceNotNull(applicationContext, "applicationContext"), forceNotNull(lifecycle, "lifecycle"),
            null);
    }

    protected AbstractChainContext(Context<? extends Lifecycle> parent, L lifecycle, Map<String, Object> properties) {
        super(forceNotNull(parent, "parent"), forceNotNull(lifecycle, "lifecycle"));
        this.properties = properties == null ? new ConcurrentHashMap<>() : properties;
    }

    @Override
    protected Map<String, ? super Object> properties() {
        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public C property(String name, Object value) {
        assertThat(name, "propertyName", isNotNull());
        if (value == null) {
            properties().remove(name);
        } else {
            properties().put(name, value);
        }
        return (C) this;
    }


}

