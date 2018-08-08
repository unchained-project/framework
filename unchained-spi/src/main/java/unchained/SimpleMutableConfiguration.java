package unchained;

import java.util.HashMap;

public class SimpleMutableConfiguration extends SimpleConfiguration implements MutableConfiguration {

    @Override
    public <E> MutableConfiguration set(String key, E value) {
        container.put(key, value);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E unset(String key) {
        return (E) container.remove(key);
    }

    @Override
    public Configuration snapshot() {
        return new SimpleConfiguration(new HashMap<>(this.container));
    }

}
