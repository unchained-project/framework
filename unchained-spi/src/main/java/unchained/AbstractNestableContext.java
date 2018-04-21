package unchained;

import unchained.error.NoSuchBeanException;

import java.util.*;

/**
 * TODO: doc
 *
 * @param <L>
 */
public abstract class AbstractNestableContext<L extends Lifecycle> extends AbstractContext<L> {

    private final Context<? extends Lifecycle> parent;
    private HashMap<String, Object> properties;

    protected AbstractNestableContext(L lifecycle) {
        // TODO: assertion
        this(null, lifecycle);
        properties = new HashMap<>();
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
        throw new NoSuchBeanException(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(Class<E> type) {
        if (parent != null) {
            return parent.get(type);
        }
        throw new NoSuchBeanException(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(String name, Class<E> type) {
        if (parent != null) {
            return parent.get(name, type);
        }
        throw new NoSuchBeanException(name, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> Collection<E> getAll(Class<E> type) {
        if (parent != null) {
            return parent.getAll(type);
        }
        return Collections.emptySet();
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
            return allNames;
        }
        return super.propertyNames();
    }

    @Override
    protected Map<String, Object> properties() {
        return properties;
    }

}
