package unchained.contract;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class DefaultRegistry<T extends Registrable> implements Registry<T> {

    private final Map<String, T> entries = new ConcurrentHashMap<>();

    private DefaultRegistry() {}

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
        return Collections.unmodifiableSet(new HashSet<>(entries.values()));
    }

    private static final Map<Class<?>, Registry<? extends Registrable>> ALL = new HashMap<>();
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    @SuppressWarnings("unchecked")
    static <T extends Registrable> Registry<T> lookup(Class<T> type) {
        LOCK.readLock().lock();
        try {
            Registry<T> result = (Registry<T>) ALL.get(type);
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
