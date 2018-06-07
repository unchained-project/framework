package unchained.utils;

import unchained.annotation.UtilityClass;
import unchained.error.UtilityClassInstantiationException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@UtilityClass
public class FutureUtils {

    private FutureUtils() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static <E> CompletableFuture<E> completableFuture(Future<E> future) {
        if (future instanceof CompletableFuture<?>) {
            return (CompletableFuture<E>) future;
        }
        return CompletableFuture.supplyAsync(() -> {
            final E value;
            try {
                value = future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return value;
        });
    }

    public static Object resolveValue(Object value) {
        if (value instanceof Future<?>) {
            Future<?> future = (Future<?>) value;
            try {
                return resolveValue(future.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return value;
        }
    }

}
