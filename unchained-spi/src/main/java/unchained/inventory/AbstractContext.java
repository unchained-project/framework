package unchained.inventory;

import unchained.Context;
import unchained.Lifecycle;

import java.util.Map;
import java.util.Set;

/**
 * TODO: doc
 *
 * @param <L>
 */
public abstract class AbstractContext<L extends Lifecycle> implements Context<L> {

    private final L lifecycle;

    protected AbstractContext(L lifecycle) {
        // TODO: assertion
        this.lifecycle = lifecycle;
    }

    /**
     * TODO: doc
     *
     * @return
     */
    protected abstract Map<String, Object> properties();

    /**
     * ${@inheritDoc}
     */
    @Override
    public L lifecycle() {
        return lifecycle;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E property(String name) {
        // TODO: assertion
        return (E) properties().get(name);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public boolean hasProperty(String name) {
        // TODO: assertion
        return properties().containsKey(name);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public Set<String> propertyNames() {
        return properties().keySet();
    }

}
