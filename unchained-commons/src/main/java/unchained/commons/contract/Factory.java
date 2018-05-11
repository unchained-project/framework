package unchained.commons.contract;

import unchained.commons.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    interface Provider {

        Collection<Factory<?>> all();

        @SuppressWarnings("unchecked")
        default <T> Collection<Factory<? extends T>> forType(Class<T> type) {
            return all()
                .stream()
                .filter(factory -> type.isAssignableFrom(factory.type()))
                .map(factory -> (Factory<? extends T>) factory)
                .collect(Collectors.toList());
        }

        Iterable<Provider> ALL = ServiceLoader.load(Provider.class);

    }

    static <T> Collection<Factory<? extends T>> forType(Class<T> type) {
        final Collection<Factory<? extends T>> factories = new ArrayList<>();
        for (Provider provider : Provider.ALL) {
            factories.addAll(provider.forType(type));
        }
        return factories;
    }

    static <T> Collection<Factory<T>> sort(Class<T> type, Collection<Factory<T>> factories) {
        return sort(type, factories, (factory1, factory2) -> {
            Class<? extends T> type1 = factory1.type();
            Class<? extends T> type2 = factory2.type();
            return type1 == type2 ? 0 : type1.isAssignableFrom(type2) ? 1 : -1;
        });
    }

    static <T> Collection<Factory<T>> sort(Class<T> type, Collection<Factory<T>> factories, Comparator<Factory<? extends T>> comparator) {
        return factories
            .stream()
            .filter(factory -> type.isAssignableFrom(factory.type()))
            .sorted(comparator)
            .collect(Collectors.toList());
    }

}
