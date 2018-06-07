package unchained;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static unchained.assertion.Assert.assertThat;
import static unchained.assertion.Assertions.isNotNull;

final class Utils {

    private Utils() {}

    static <O> O forceNotNull(O value, String name) {
        assertThat(value, name, isNotNull());
        return value;
    }

    static final Map<String, ? super Object> environment;

    static {
        final Map<String, ? super Object> replica = new HashMap<>(System.getenv().size());
        replica.putAll(System.getenv());
        environment = Collections.unmodifiableMap(replica);
    }

}
