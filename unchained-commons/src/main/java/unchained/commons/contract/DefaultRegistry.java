package unchained.commons.contract;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class DefaultRegistry<T extends Registrable> implements Registry<T> {

    private final Map<String, T> entries = new ConcurrentHashMap<>();

    DefaultRegistry() {}

    @Override
    public T get(String key) {
        return entries.get(key);
    }

    @Override
    public boolean has(String key) {
        return entries.containsKey(key);
    }

    @Override
    public void register(T value) {
        entries.put(value.registryKey(), value);
    }

    @Override
    public Set<String> keys() {
        return Collections.unmodifiableSet(entries.keySet());
    }

    @Override
    public Set<T> values() {
        final Set<T> result = new HashSet<>();
        result.addAll(entries.values());
        return Collections.unmodifiableSet(result);
    }

    static final Map<Class<?>, Registry<? extends Registrable>> ALL = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    static <T extends Registrable> Registry<T> lookup(Class<T> type) {
        Registry<T> result = (Registry<T>) ALL.get(type);
        if (result == null) {
            result = new DefaultRegistry<>();
            ALL.put(type, result);
        }
        return result;
    }

}
