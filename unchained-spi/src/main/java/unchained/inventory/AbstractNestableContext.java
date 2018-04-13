package unchained.inventory;

import unchained.Context;
import unchained.Lifecycle;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: doc
 *
 * @param <L>
 */
public abstract class AbstractNestableContext<L extends Lifecycle> extends AbstractContext<L> {

    private final Context<? extends Lifecycle> parent;

    protected AbstractNestableContext(L lifecycle) {
        // TODO: assertion
        this(null, lifecycle);
    }

    protected AbstractNestableContext(Context<? extends Lifecycle> parent, L lifecycle) {
        super(lifecycle);
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(String name) {
        if (parent != null) {
            return parent.get(name);
        }
        return super.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(Class<E> type) {
        if (parent != null) {
            return parent.get(type);
        }
        return super.get(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(String name, Class<E> type) {
        if (parent != null) {
            return parent.get(name, type);
        }
        return super.get(name, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> Collection<E> getAll(Class<E> type) {
        if (parent != null) {
            final Set<E> allBeans = new HashSet<>();
            allBeans.addAll(parent.getAll(type));
            allBeans.addAll(super.getAll(type));
            return allBeans;
        }
        return super.getAll(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E property(String name) {
        if (super.hasProperty(name)) {
            return super.property(name);
        }
        if (parent != null) {
            return parent.property(name);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasProperty(String name) {
        return super.hasProperty(name) || (parent != null && parent.hasProperty(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> propertyNames() {
        if (parent != null) {
            final Set<String> allNames = new HashSet<>();
            allNames.addAll(parent.propertyNames());
            allNames.addAll(super.propertyNames());
        }
        return super.propertyNames();
    }

}
