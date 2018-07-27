package unchained;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleConfiguration implements Configuration {

    protected final Map<String, Object> container;

    protected SimpleConfiguration(Map<String, Object> container) {
        this.container = container;
    }

    public SimpleConfiguration() {
        this(new HashMap<>());
    }

    @Override
    public Set<String> keys() {
        return container.keySet();
    }

    @Override
    public boolean has(String key) {
        return container.containsKey(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E get(String key) {
        return (E) container.get(key);
    }

}
