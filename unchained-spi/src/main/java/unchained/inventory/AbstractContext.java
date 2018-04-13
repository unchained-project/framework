package unchained.inventory;

import unchained.Context;
import unchained.Lifecycle;
import unchained.error.NoSuchBeanException;

import java.util.Collection;
import java.util.Collections;
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
    protected Map<String, Object> properties() {
        return Collections.emptyMap();
    }

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
    public <E> E get(String name) {
        // TODO: assertion
        throw new NoSuchBeanException(name);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public <E> E get(Class<E> type) {
        // TODO: assertion
        throw new NoSuchBeanException(type);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public <E> E get(String name, Class<E> type) {
        // TODO: assertion
        throw new NoSuchBeanException(type);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public <E> Collection<E> getAll(Class<E> type) {
        // TODO: assertion
        return Collections.emptySet();
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public boolean has(String name) {
        // TODO: assertion
        return false;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public boolean has(Class<?> type) {
        // TODO: assertion
        return false;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public boolean has(String name, Class<?> type) {
        // TODO: assertion
        return false;
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
