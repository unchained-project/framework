package unchained;

import java.util.Map;
import java.util.Set;

import static unchained.Utils.forceNotNull;
import static unchained.commons.assertion.Assert.assertThat;
import static unchained.commons.assertion.Assertions.isNotNull;

/**
 * TODO: doc
 *
 * @param <L>
 */
public abstract class AbstractContext<L extends Lifecycle> implements Context<L> {

    private final L lifecycle;

    protected AbstractContext(L lifecycle) {
        this.lifecycle = forceNotNull(lifecycle, "lifecycle");
    }

    /**
     * TODO: doc
     *
     * @return
     */
    protected abstract Map<String, Object> properties();

    /**
     * ${@inheritDoc}
     */
    @Override
    public L lifecycle() {
        return lifecycle;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E property(String name) {
        assertThat(name, "propertyName", isNotNull());
        return (E) properties().get(name);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public boolean hasProperty(String name) {
        assertThat(name, "propertyName", isNotNull());
        return properties().containsKey(name);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public Set<String> propertyNames() {
        return properties().keySet();
    }

}
