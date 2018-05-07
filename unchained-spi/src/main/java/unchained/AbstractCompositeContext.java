package unchained;

import unchained.error.AmbiguousBeanSelectionException;

import java.util.*;
import java.util.stream.Collectors;

import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isNotNull;

/**
 * TODO: doc
 */
public abstract class AbstractCompositeContext<L extends Lifecycle, C extends Context<L>, M extends AbstractCompositeContext<L, C, M>>
    extends AbstractNestableContext<L> implements Context<L> {

    private final List<C> contexts;

    protected AbstractCompositeContext(L lifecycle) {
        this(null, lifecycle);
    }

    protected AbstractCompositeContext(Context<? extends Lifecycle> parent, L lifecycle) {
        super(parent, lifecycle);
        this.contexts = new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    private <E> Set<E> allOf(String name) {
        assertThat(name, "beanName", isNotNull());
        return contexts.stream()
            .filter(context -> context.has(name))
            .map(context -> (E) context.get(name))
            .collect(Collectors.toSet());
    }

    private <E> Set<E> allOf(Class<E> type) {
        assertThat(type, "beanType", isNotNull());
        return contexts.stream()
            .filter(context -> context.has(type))
            .map(context -> context.get(type))
            .collect(Collectors.toSet());
    }

    private <E> Set<E> allOf(String name, Class<E> type) {
        assertThat(name, "beanName", isNotNull());
        assertThat(type, "beanType", isNotNull());
        return contexts.stream()
            .filter(context -> context.has(name, type))
            .map(context -> context.get(name, type))
            .collect(Collectors.toSet());
    }

    private boolean addContext(C context) {
        assertThat(context, "applicationContext", isNotNull());
        return !contexts.contains(context) && contexts.add(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(String name) {
        final Set<E> beans = allOf(name);
        if (beans.isEmpty()) {
            return super.get(name);
        }
        if (beans.size() == 1) {
            return beans.iterator().next();
        }
        throw new AmbiguousBeanSelectionException(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(Class<E> type) {
        final Set<E> beans = allOf(type);
        if (beans.isEmpty()) {
            return super.get(type);
        }
        if (beans.size() == 1) {
            return beans.iterator().next();
        }
        throw new AmbiguousBeanSelectionException(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E get(String name, Class<E> type) {
        final Set<E> beans = allOf(name, type);
        if (beans.isEmpty()) {
            return super.get(name, type);
        }
        if (beans.size() == 1) {
            return beans.iterator().next();
        }
        throw new AmbiguousBeanSelectionException(name, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> Collection<E> getAll(Class<E> type) {
        assertThat(type, "beanType", isNotNull());
        final Set<E> allBeans = new HashSet<>();
        allBeans.addAll(contexts.stream()
            .map(context -> context.getAll(type))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet()));
        allBeans.addAll(super.getAll(type));
        return allBeans;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(String name) {
        assertThat(name, "beanName", isNotNull());
        return contexts.stream()
            .filter(context -> context.has(name))
            .map(context -> true)
            .findFirst()
            .orElse(super.has(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(Class<?> type) {
        assertThat(type, "beanType", isNotNull());
        return contexts.stream()
            .filter(context -> context.has(type))
            .map(context -> true)
            .findFirst()
            .orElse(super.has(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(String name, Class<?> type) {
        assertThat(name, "beanName", isNotNull());
        assertThat(type, "beanType", isNotNull());
        return contexts.stream()
            .filter(context -> context.has(name, type))
            .map(context -> true)
            .findFirst()
            .orElse(super.has(name, type));
    }

    /**
     * TODO: doc
     *
     * @param first
     * @param rest
     * @return
     */
    @SuppressWarnings("unchecked")
    public M add(C first, C... rest) {
        addContext(first);
        for (C context : rest) {
            addContext(context);
        }
        return (M) this;
    }

}
