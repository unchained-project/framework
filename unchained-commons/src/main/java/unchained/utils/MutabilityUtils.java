package unchained.utils;

import unchained.annotation.UtilityClass;
import unchained.contract.Stateful;
import unchained.error.UtilityClassInstantiationException;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class MutabilityUtils {

    private MutabilityUtils() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static <M extends N, N> N immutableCopy(M original) {
        return immutableCopy(original, false);
    }

    @SuppressWarnings("unchecked")
    public static <M extends N, N> N immutableCopy(M original, boolean lax) {
        if (original == null) {
            return null;
        } else if (original instanceof Stateful<?>) {
            return (N) ((Stateful<?>) original).immutableCopy();
        } else if (original.getClass().getName().startsWith("java.lang.")
                || original.getClass().isPrimitive()) {
            return original;
        } else if (original.getClass().isArray()) {
            return (N) copyArray((Object[]) original);
        } else if (original instanceof List) {
            return (N) copyList((List) original);
        } else if (original instanceof Set) {
            return (N) copySet((Set) original);
        } else if (original instanceof Map) {
            return (N) copyMap((Map) original);
        } else if (lax) {
            return original;
        } else {
            throw new IllegalArgumentException("Could not make the passed object immutable");
        }
    }

    private static <M> M[] copyArray(M[] input) {
        //noinspection unchecked
        final Object copy = Array.newInstance(input.getClass().getComponentType(), input.length);
        for (int i = 0; i < input.length; i++) {
            Array.set(copy, i, immutableCopy(input[i]));
        }
        //noinspection unchecked
        return (M[]) copy;
    }

    private static <M> List<M> copyList(List<M> list) {
        return list.stream()
                   .map(MutabilityUtils::immutableCopy)
                   .collect(Collectors.toList());
    }

    private static <M> Set<M> copySet(Set<M> set) {
        return set.stream()
                  .map(MutabilityUtils::immutableCopy)
                  .collect(Collectors.toSet());
    }

    private static <K, V> Map<K, V> copyMap(Map<K, V> map) {
        final Map<K, V> copy = new HashMap<>(map.size());
        final List<K> originalKeys = new ArrayList<>(map.keySet());
        final List<K> copiedKeys = copyList(originalKeys);
        for (int i = 0; i < originalKeys.size(); i++) {
            K key = originalKeys.get(i);
            final K keyCopy = copiedKeys.get(i);
            copy.put(keyCopy, immutableCopy(map.get(key)));
        }
        return Collections.unmodifiableMap(copy);
    }

}
