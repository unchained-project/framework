package unchained.commons.contract.impl;

import unchained.commons.contract.BaseLazyTest;
import unchained.commons.contract.Lazy;

import java.util.function.Supplier;

public class AtomicLazyTest extends BaseLazyTest {

    @Override
    protected <E> Lazy<E> wrap(final Supplier<E> supplier) {
        return new AtomicLazy<>(supplier);
    }

}
