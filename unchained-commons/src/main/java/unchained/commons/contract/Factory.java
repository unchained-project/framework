package unchained.commons.contract;

public interface Factory<T, A extends Factory.Arguments> extends Registrable {

    Class<T> type();

    T create(A configuration);

    interface Arguments {}

    @Override
    default String registryKey() {
        return type().getCanonicalName();
    }

    static <T, A extends Arguments> void register(Factory<T, A> factory) {
        Registry.lookup(Factory.class).register(factory);
    }

    static <T, A extends Arguments> Factory<T, A> forType(Class<T> type) {
        return Registry.lookup(Factory.class).get(type.getCanonicalName());
        // WARNING: duplicate logic at `type.getCanonicalName()`.
    }

}
