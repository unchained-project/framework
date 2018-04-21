package unchained.commons.contract.impl;

import unchained.commons.contract.MutableRegistry;
import unchained.commons.contract.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isNotNull;

public abstract class AbstractMutableRegistry<R extends Registry<R, I>, I>
        extends AbstractRegistry<R, I>
        implements MutableRegistry<R, I> {

    private final Map<String, I> storage;
    private final R parent;

    public AbstractMutableRegistry() {
        this(null);
    }

    public AbstractMutableRegistry(R parent) {
        this.parent = parent;
        storage = new HashMap<>();
    }

    @Override
    protected Map<String, I> getStorage() {
        return storage;
    }

    @Override
    public Set<I> values() {
        final Set<I> values;
        if (parent != null) {
            values = new HashSet<>(parent.values());
            values.addAll(storage.values());
        } else {
            values = new HashSet<>(storage.values());
        }
        return values;
    }

    @Override
    public I get(final String name, final I defaultValue) {
        if (super.has(name) || parent == null) {
            return super.get(name, defaultValue);
        } else {
            return parent.get(name, defaultValue);
        }
    }

    @Override
    public I get(String name, Function<String, I> computer) {
        if (super.has(name) || parent == null) {
            return super.get(name, computer);
        } else {
            return parent.get(name, computer);
        }
    }

    @Override
    public Set<String> keys() {
        if (parent == null) {
            return super.keys();
        }
        final Set<String> keys = new HashSet<>(parent.keys());
        keys.addAll(super.keys());
        return keys;
    }

    @Override
    public boolean has(String key) {
        return super.has(key) || parent != null && parent.has(key);
    }

    @Override
    public I get(String name) {
        if (parent == null || super.has(name)) {
            return super.get(name);
        } else {
            return parent.get(name);
        }
    }

    @Override
    public synchronized void delete(String name) {
        assertThat(name, "name", isNotNull());
        if (has(name)) {
            storage.remove(name);
        }
    }

    @Override
    public synchronized void add(String name, I item) {
        assertThat(name, "name", isNotNull());
        assertThat(item, "item", isNotNull());
        storage.put(name, item);
    }

    @Override
    public synchronized void fromMap(Map<String, I> map) {
        map.forEach(this::add);
    }

    @Override
    public Map<String, I> toMap() {
        if (parent == null) {
            return super.toMap();
        }
        final Map<String, I> result = new HashMap<>(parent.toMap());
        result.putAll(super.toMap());
        return result;
    }

}
