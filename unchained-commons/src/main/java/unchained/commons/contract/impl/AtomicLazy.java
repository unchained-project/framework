package unchained.commons.contract.impl;

import unchained.commons.contract.Lazy;

import java.util.function.Supplier;

public class AtomicLazy<E> implements Lazy<E> {

    private final Supplier<E> supplier;
    private E value;
    private boolean supplied;

    public AtomicLazy(final Supplier<E> supplier) {
        this.supplier = supplier;
        this.supplied = false;
    }

    @Override
    public E get() {
        synchronized (this) {
            if (!supplied) {
                value = supplier.get();
                supplied = true;
            }
        }
        return value;
    }

}
