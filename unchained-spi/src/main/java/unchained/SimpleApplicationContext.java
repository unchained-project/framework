package unchained;

import unchained.error.TooManyBeansException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO: doc
 */
public class SimpleApplicationContext extends AbstractNestableContext<ApplicationLifecycle>
    implements ApplicationContext {

    private final Map<String, Object> instances;

    /**
     * TODO: doc
     *
     * @param lifecycle
     * @param instances
     */
    public SimpleApplicationContext(ApplicationLifecycle lifecycle, Map<String, Object> instances) {
        this(null, lifecycle, instances);
    }

    /**
     * TODO: doc
     *
     * @param parent
     * @param instances
     */
    public SimpleApplicationContext(ApplicationContext parent, Map<String, Object> instances) {
        // TODO: assertion(parent)
        this(parent, parent.lifecycle(), instances);
    }

    /**
     * TODO: doc
     *
     * @param parent
     * @param lifecycle
     * @param instances
     */
    protected SimpleApplicationContext(ApplicationContext parent, ApplicationLifecycle lifecycle, Map<String, Object> instances) {
        // TODO: assertion(lifecycle, instances)
        super(parent, lifecycle);
        // TODO: use `MutabilityUtils.immutableCopy` instead
        this.instances = Collections.unmodifiableMap(instances);
    }

    private <E> Collection<E> findInstances(Class<E> type) {
        return instances.values().stream()
            .filter(type::isInstance)
            .map(type::cast)
            .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E get(String name) {
        if (has(name)) {
            return (E) instances.get(name);
        }
        return super.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(Class<E> type) {
        // TODO: assertion
        final Collection<E> all = findInstances(type);
        if (all.isEmpty()) {
            return super.get(type);
        }
        if (all.size() == 1) {
            return all.iterator().next();
        }
        throw new TooManyBeansException(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(String name, Class<E> type) {
        // TODO: assertion(type)
        final Object bean = get(name);
        if (type.isInstance(bean)) {
            return type.cast(bean);
        }
        return super.get(name, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> Collection<E> getAll(Class<E> type) {
        // TODO: assertion
        final Set<E> allBeans = new HashSet<>();
        allBeans.addAll(findInstances(type));
        allBeans.addAll(super.getAll(type));
        return allBeans;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(String name) {
        // TODO: assertion
        return instances.containsKey(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(Class<?> type) {
        // TODO: assertion
        return instances.values().stream().anyMatch(type::isInstance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(String name, Class<?> type) {
        // TODO: assertion(type)
        return has(name) && type.isInstance(get(name));
    }

}
