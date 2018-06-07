package unchained.contract;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Registry<T extends Registrable> {

    void register(T value);

    T get(String key);

    default T get(String key, T defaultValue) {
        return get(key, s -> defaultValue);
    }

    default T get(String key, Function<String, T> computer) {
        final T result = get(key);
        return result == null ? computer.apply(key) : result;
    }

    boolean has(String key);

    Set<String> keys();

    Set<T> values();

    default Map<String, T> toMap() {
        return keys().stream().collect(Collectors.toMap(Function.identity(), this::get));
    }

    static <T extends Registrable> Registry<T> lookup(Class<T> type) {
        return DefaultRegistry.lookup(type);
    }

}
