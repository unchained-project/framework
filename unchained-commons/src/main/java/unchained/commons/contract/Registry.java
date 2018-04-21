package unchained.commons.contract;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Registry<R extends Registry<R, I>, I> extends Stateful<R> {

    default I get(String name) {
        return get(name, (I) null);
    }

    I get(String name, I defaultValue);

    I get(String name, Function<String, I> computer);

    Set<String> keys();

    Set<I> values();

    boolean has(String key);

    default Map<String, I> toMap() {
        return keys().stream().collect(Collectors.toMap(Function.identity(), this::get));
    }

}
