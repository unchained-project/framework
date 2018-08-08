package unchained.contract;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Registry<K, T extends Registrable<K>> {

    void register(T value);

    T get(K key);

    default T get(K key, T defaultValue) {
        return get(key, s -> defaultValue);
    }

    default T get(K key, Function<K, T> computer) {
        final T result = get(key);
        return result == null ? computer.apply(key) : result;
    }

    boolean has(K key);

    Set<K> keys();

    Set<T> values();

    default Map<K, T> toMap() {
        return keys().stream().collect(Collectors.toMap(Function.identity(), this::get));
    }

    @SuppressWarnings("unchecked")
    static <K, T extends Registrable<K>> Registry<K, T> lookup(Class<T> type) {
        return DefaultRegistry.lookup(type);
    }

}
