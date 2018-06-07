package unchained.contract;

import java.util.function.Supplier;

@FunctionalInterface
public interface Lazy<E> {

    E get();

    static <E> Lazy<E> of(Supplier<E> supplier) {
        return new DefaultLazy<>(supplier);
    }

    static <E> Lazy<E> atomicOf(Supplier<E> supplier) {
        return new AtomicLazy<>(supplier);
    }

}
