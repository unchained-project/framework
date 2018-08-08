package unchained.contract;

import unchained.error.FactoryNotFoundException;

public interface Factory<T, U> extends Registrable<Class<?>> {

    Class<T> type();

    T create(U arguments);

    default T create() {
        return create(null);
    }

    @Override
    default Class<?> registryKey() {
        return type();
    }

    @SuppressWarnings("unchecked")
    static <T, U> void register(Factory<T, U> factory) {
        Registry.lookup(Factory.class).register(factory);
    }

    @SuppressWarnings("unchecked")
    static <T, U> Factory<T, U> forType(Class<T> type) {
        /*

          NOTE:

          This is an oversimplified logic to implement factory lookup.
          A more advanced implementation must be able to find the best
          factory among a set of candidate factories.

        */
        Factory result = (Factory<T, U>) Registry.lookup(Factory.class).get(type);

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
