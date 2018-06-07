package unchained.contract;

import unchained.contract.BaseLazyTest;
import unchained.contract.DefaultLazy;
import unchained.contract.Lazy;

import java.util.function.Supplier;

public class DefaultLazyTest extends BaseLazyTest {

    @Override
    protected <E> Lazy<E> wrap(final Supplier<E> supplier) {
        return new DefaultLazy<>(supplier);
    }

}
