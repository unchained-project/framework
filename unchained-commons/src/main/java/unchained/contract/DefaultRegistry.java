package unchained.contract;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class DefaultRegistry<K, T extends Registrable<K>> implements Registry<K, T> {

    private final Map<K, T> entries = new ConcurrentHashMap<>();

    private DefaultRegistry() {}

    @Override
    public T get(K key) {
        return entries.get(key);
    }

    @Override
    public boolean has(K key) {
        return entries.containsKey(key);
    }

    @Override
    public void register(T value) {
        entries.put(value.registryKey(), value);
    }

    @Override
    public Set<K> keys() {
        return Collections.unmodifiableSet(entries.keySet());
    }

    @Override
    public Set<T> values() {
        return Collections.unmodifiableSet(new HashSet<>(entries.values()));
    }

    private static final Map<Class<?>, Registry<?, ? extends Registrable<?>>> ALL = new HashMap<>();
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    @SuppressWarnings("unchecked")
    static <K, T extends Registrable<K>> Registry<K, T> lookup(Class<T> type) {
        LOCK.readLock().lock();
        try {
            Registry<K, T> result = (Registry<K, T>) ALL.get(type);
            if (result == null) {
                LOCK.readLock().unlock();
                LOCK.writeLock().lock();
                try {
                    result = new DefaultRegistry<>();
                    ALL.put(type, result);
                    LOCK.readLock().lock();
                } finally {
                    LOCK.writeLock().unlock();
                }
            }
            return result;
        } finally {
            LOCK.readLock().unlock();
        }
    }

}
