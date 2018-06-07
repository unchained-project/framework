package unchained.contract;

import unchained.contract.AtomicLazy;
import unchained.contract.BaseLazyTest;
import unchained.contract.Lazy;

import java.util.function.Supplier;

public class AtomicLazyTest extends BaseLazyTest {

    @Override
    protected <E> Lazy<E> wrap(final Supplier<E> supplier) {
        return new AtomicLazy<>(supplier);
    }

}
