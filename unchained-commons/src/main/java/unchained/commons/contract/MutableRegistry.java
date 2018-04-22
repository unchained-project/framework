package unchained.commons.contract;

import java.util.Map;

public interface MutableRegistry<R extends Registry<R, I>, I> extends Registry<R, I> {

    void delete(String name);

    void add(String name, I item);

    default void addAll(R registry) {
        fromMap(registry.toMap());
    }

    void fromMap(Map<String, I> map);

}
