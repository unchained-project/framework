package unchained.contract;

import unchained.error.FactoryNotFoundException;

public interface Factory<T, U> extends Registrable {

    Class<T> type();

    T create(U arguments);

    default T create() {
        return create(null);
    }

    @Override
    default String registryKey() {
        return type().getCanonicalName();
    }

    static <T, U> void register(Factory<T, U> factory) {
        Registry.lookup(Factory.class).register(factory);
    }

    @SuppressWarnings("unchecked")
    static <T, U> Factory<T, U> forType(Class<T> type) {
        // WARNING: duplicate logic at `type.getCanonicalName()`.
        Factory result = Registry.lookup(Factory.class).get(type.getCanonicalName());
        if (result == null) {
            throw new FactoryNotFoundException(type);
        }
        try {
            return (Factory<T, U>) result;
        } catch (ClassCastException e) {
            throw new FactoryNotFoundException(type, e);
        }
    }

}
