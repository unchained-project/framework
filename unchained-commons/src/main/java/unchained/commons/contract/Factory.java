package unchained.commons.contract;

import unchained.commons.utils.ReflectionUtils;

import java.util.function.Function;

@FunctionalInterface
public interface Factory<T> extends Function<Object[], T> {

    T create(Object... arguments);

    @Override
    default T apply(Object[] arguments) {
        return create(arguments);
    }

    @SuppressWarnings("unchecked")
    default Class<T> type() {
        return (Class<T>) ReflectionUtils.resolveRawArgument(Factory.class, this.getClass());
    }

}
