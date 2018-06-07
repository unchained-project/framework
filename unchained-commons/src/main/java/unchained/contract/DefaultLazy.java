package unchained.contract;

import unchained.contract.Lazy;

import java.util.function.Supplier;

public class DefaultLazy<E> implements Lazy<E> {

    private final Supplier<E> supplier;
    private E value;
    private boolean supplied;

    public DefaultLazy(final Supplier<E> supplier) {
        this.supplier = supplier;
        this.supplied = false;
    }

    @Override
    public E get() {
        if (!supplied) {
            value = supplier.get();
            supplied = true;
        }
        return value;
    }
}
