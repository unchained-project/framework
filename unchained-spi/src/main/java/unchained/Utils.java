package unchained;

import unchained.factory.SimpleFactoryMethod;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static unchained.assertion.Assert.assertThat;
import static unchained.assertion.Assertions.isNotNull;
import static unchained.contract.Factory.register;

final class Utils {

    private Utils() {}

    static final Map<String, ? super Object> environment;

    static <O> O forceNotNull(O value, String name) {
        assertThat(value, name, isNotNull());
        return value;
    }

    private static <T, U> void addFactoryMethod(Class<T> type, Function<U, T> builder) {
        register(new SimpleFactoryMethod<>(type, builder));
    }

    private static void addAllFactories() {
        addFactoryMethod(Configuration.class, (arguments) -> new SimpleConfiguration());
        addFactoryMethod(MutableConfiguration.class, (arguments) -> new SimpleMutableConfiguration());
    }

    static {
        environment = Collections.unmodifiableMap(System.getenv());
        addAllFactories();
    }

}
