package unchained;

import unchained.commons.utils.MutabilityUtils;
import unchained.error.TooManyBeansException;

import java.util.*;
import java.util.stream.Collectors;

import static unchained.Utils.forceNotNull;
import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isNotNull;

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
        this(forceNotNull(parent, "parent"), parent.lifecycle(), instances);
    }

    /**
     * TODO: doc
     *
     * @param parent
     * @param lifecycle
     * @param instances
     */
    protected SimpleApplicationContext(ApplicationContext parent, ApplicationLifecycle lifecycle, Map<String, Object> instances) {
        super(forceNotNull(parent, "parent"), forceNotNull(lifecycle, "lifecycle"));
        this.instances = MutabilityUtils.immutableCopy(instances);
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
        assertThat(name, "beanName", isNotNull());
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
        assertThat(type, "beanType", isNotNull());
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
        assertThat(name, "beanName", isNotNull());
        assertThat(type, "beanType", isNotNull());
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
        assertThat(type, "beanType", isNotNull());
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
        assertThat(name, "beanName", isNotNull());
        return instances.containsKey(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(Class<?> type) {
        assertThat(type, "beanType", isNotNull());
        return instances.values().stream().anyMatch(type::isInstance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(String name, Class<?> type) {
        assertThat(name, "beanName", isNotNull());
        assertThat(type, "beanType", isNotNull());
        return has(name) && type.isInstance(get(name));
    }

}
