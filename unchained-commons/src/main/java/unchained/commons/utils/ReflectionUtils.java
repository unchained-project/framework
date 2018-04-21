package unchained.commons.utils;

import unchained.commons.annotation.UtilityClass;
import unchained.commons.error.UtilityClassInstantiationException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.*;

@UtilityClass
public class ReflectionUtils {

    private ReflectionUtils() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static Type resolveCommonAncestor(List<Type> types) {
        return types.stream().reduce(ReflectionUtils::resolveCommonAncestor).orElse(null);
    }

    private static Type resolveCommonAncestor(Type first, Type second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        }
        final List<TypeInfo> ancestry = readAncestry(first);
        final List<TypeInfo> other = readAncestry(second);
        for (TypeInfo info : ancestry) {
            if (other.contains(info)) {
                return info.superType;
            }
        }
        return Object.class;
    }

    private static List<TypeInfo> readAncestry(Type type) {
        final Class<?> resolvedClass = resolveClass(type);
        final Set<TypeInfo> info = new HashSet<>();
        readAncestry(info, 0, resolvedClass);
        final List<TypeInfo> result = new ArrayList<>(info);
        result.sort(TypeInfo::compareTo);
        return result;
    }

    private static void readAncestry(Set<TypeInfo> info, int distance, Class<?> type) {
        if (type == null || Object.class.equals(type)) {
            return;
        }
        final TypeInfo typeInfo = new TypeInfo(type, distance);
        if (info.contains(typeInfo)) {
            return;
        }
        info.add(typeInfo);
        readAncestry(info, distance + 1, type.getSuperclass());
        Arrays.stream(type.getInterfaces()).forEach(superInterface -> readAncestry(info, distance + 1, superInterface));
    }

    private static Class<?> resolveClass(Type type) {
        if (type instanceof Class<?>) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return resolveClass(parameterizedType.getRawType());
        } else if (type instanceof TypeVariable<?>) {
            throw new UnsupportedOperationException();
        } else if (type instanceof WildcardType) {
            final WildcardType wildcardType = (WildcardType) type;
            if (wildcardType.getLowerBounds() == null || wildcardType.getLowerBounds().length == 0) {
                return Object.class;
            } else {
                return resolveClass(wildcardType.getLowerBounds()[0]);
            }
        }
        throw new UnsupportedOperationException();
    }

    private static class TypeInfo implements Comparable<TypeInfo> {

        private final Class<?> superType;
        private final int distance;

        private TypeInfo(final Class<?> superType, final int distance) {
            this.superType = superType;
            this.distance = distance;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final TypeInfo typeInfo = (TypeInfo) o;
            return Objects.equals(superType, typeInfo.superType);
        }

        @Override
        public int hashCode() {

            return Objects.hash(superType);
        }

        @Override
        public int compareTo(final TypeInfo o) {
            final int distanceComparison = Integer.compare(distance, o.distance);
            if (distanceComparison != 0) {
                return distanceComparison;
            }
            if (superType.isInterface() && o.superType.isInterface()) {
                return 0;
            } else if (superType.isInterface()) {
                return 1;
            } else if (o.superType.isInterface()) {
                return -1;
            }
            return 0;
        }

    }

}
