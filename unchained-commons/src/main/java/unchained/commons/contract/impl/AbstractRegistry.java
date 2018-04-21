package unchained.commons.contract.impl;

import unchained.commons.contract.Registry;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isNotNull;

public abstract class AbstractRegistry<R extends Registry<R, I>, I> implements Registry<R, I> {

    protected abstract Map<String, I> getStorage();

    private I read(String name) {
        assertThat(name, "name", isNotNull());
        return getStorage().get(name);
    }

    @Override
    public I get(String name, I defaultValue) {
        assertThat(name, "name", isNotNull());
        if (!has(name)) {
            return defaultValue;
        }
        return read(name);
    }

    @Override
    public I get(String name, Function<String, I> computer) {
        assertThat(name, "name", isNotNull());
        if (!has(name)) {
            return computer.apply(name);
        }
        return read(name);
    }

    @Override
    public Set<String> keys() {
        return getStorage().keySet();
    }

    @Override
    public boolean has(String key) {
        assertThat(key, "key", isNotNull());
        return getStorage().containsKey(key);
    }

    @Override
    public String toString() {
        return getStorage().toString();
    }

}
