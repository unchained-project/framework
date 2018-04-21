package unchained.commons.contract.impl;

import unchained.commons.contract.Registry;
import unchained.commons.utils.MutabilityUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isNotNull;

public abstract class AbstractImmutableRegistry<R extends Registry<R, I>, I>
        extends AbstractRegistry<R, I> {

    private final Map<String, I> map;
    private final Set<I> values;

    protected AbstractImmutableRegistry(Map<String, I> map) {
        assertThat(map, "map", isNotNull());
        this.map = MutabilityUtils.immutableCopy(map);
        this.values = Collections.unmodifiableSet(new HashSet<>(map.values()));
    }

    @Override
    protected Map<String, I> getStorage() {
        return map;
    }

    @Override
    public Set<I> values() {
        return values;
    }

}
