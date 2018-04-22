package unchained.commons.contract;

import org.testng.annotations.Test;

import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class BaseLazyTest {

    protected abstract <E> Lazy<E> wrap(Supplier<E> supplier);

    @Test
    public void testSupplierIsNotCalledInitially() {
        final CountingSupplier<Integer> supplier = new CountingSupplier<>(300);
        wrap(supplier);
        assertThat(supplier.getCount(), is(0));
    }

    @Test
    public void testSupplierIsCalledOnlyOnce() {
        final CountingSupplier<String> supplier = new CountingSupplier<>("value");
        final Lazy<String> lazy = wrap(supplier);
        for (int i = 0; i < 100; i++) {
            lazy.get();
        }
        assertThat(supplier.getCount(), is(1));
    }

    @Test
    public void testThatItSuppliesTheSameInstance() {
        final Object instance = new Object();
        final CountingSupplier<Object> supplier = new CountingSupplier<>(instance);
        final Lazy<Object> lazy = wrap(supplier);
        assertThat(lazy.get(), is(instance));
    }

    private static class CountingSupplier<E> implements Supplier<E> {

        private final E value;
        private int count;

        private CountingSupplier(final E value) {
            this.value = value;
            count = 0;
        }

        @Override
        public E get() {
            count ++;
            return value;
        }

        int getCount() {
            return count;
        }

    }

}
