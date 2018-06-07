package unchained.contract;

import unchained.error.FactoryNotFoundException;

public interface Factory<T, A extends Factory.Arguments> extends Registrable {

    Class<T> type();

    T create(A configuration);

    interface Arguments {}

    class NoArgument implements Arguments {

        public static final NoArgument INSTANCE = new NoArgument();

    }

    @Override
    default String registryKey() {
        return type().getCanonicalName();
    }

    static <T, A extends Arguments> void register(Factory<T, A> factory) {
        Registry.lookup(Factory.class).register(factory);
    }

    @SuppressWarnings("unchecked")
    static <T, A extends Arguments> Factory<T, A> forType(Class<T> type) {
        // WARNING: duplicate logic at `type.getCanonicalName()`.
        Factory result = Registry.lookup(Factory.class).get(type.getCanonicalName());
        if (result == null) {
            throw new FactoryNotFoundException(type);
        }
        try {
            return (Factory<T, A>) result;
        } catch (ClassCastException e) {
            throw new FactoryNotFoundException(type, e);
        }
    }

}
