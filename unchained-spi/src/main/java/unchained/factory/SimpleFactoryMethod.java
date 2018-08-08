package unchained.factory;

import unchained.contract.Factory;

import java.util.function.Function;

public class SimpleFactoryMethod<T, U> implements Factory<T, U> {

    private final Class<T> type;
    private final Function<U, T> builder;

    public SimpleFactoryMethod(Class<T> type, Function<U, T> builder) {
        this.type = type;
        this.builder = builder;
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public T create(U arguments) {
        return builder.apply(arguments);
    }

}
