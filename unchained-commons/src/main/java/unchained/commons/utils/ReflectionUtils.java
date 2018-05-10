package unchained.commons.utils;

import sun.misc.Unsafe;
import unchained.commons.annotation.UtilityClass;
import unchained.commons.error.UtilityClassInstantiationException;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.*;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.*;

@UtilityClass
public class ReflectionUtils {

    private ReflectionUtils() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static Type resolveCommonAncestor(List<Type> types) {
        return types.stream().reduce(ReflectionUtils::resolveCommonAncestor).orElse(null);
    }

    public static <T, S extends T> Class<?> resolveRawArgument(Class<T> type, Class<S> subType) {
        return resolveRawArgument(resolveGenericType(type, subType), subType);
    }

    public static Class<?> resolveRawArgument(Type genericType, Class<?> subType) {
        Class<?>[] arguments = resolveRawArguments(genericType, subType);
        if (arguments.length != 1) {
            throw new IllegalArgumentException(
                String.format("Expected 1 argument for generic type <%s> but found <%d>.", genericType, arguments.length));
        }
        return arguments[0];
    }

    public static <T, S extends T> Class<?>[] resolveRawArguments(Class<T> type, Class<S> subType) {
        return resolveRawArguments(resolveGenericType(type, subType), subType);
    }

    public static Class<?>[] resolveRawArguments(Type genericType, Class<?> subType) {
        Class<?>[] result = new Class<?>[0];
        Class<?> functionalInterface = null;
        if (subType.isSynthetic()) {
            Class<?> fi = genericType instanceof ParameterizedType
                && ((ParameterizedType) genericType).getRawType() instanceof Class
                ? (Class<?>) ((ParameterizedType) genericType).getRawType()
                : genericType instanceof Class ? (Class<?>) genericType : null;
            if (fi != null && fi.isInterface()) {
                functionalInterface = fi;
            }
        }
        if (genericType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericType;
            Type[] arguments = paramType.getActualTypeArguments();
            result = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                result[i] = resolveRawClass(arguments[i], subType, functionalInterface);
            }
        } else if (genericType instanceof TypeVariable) {
            result = new Class[1];
            result[0] = resolveRawClass(genericType, subType, functionalInterface);
        } else if (genericType instanceof Class) {
            TypeVariable<?>[] typeParams = ((Class<?>) genericType).getTypeParameters();
            result = new Class[typeParams.length];
            for (int i = 0; i < typeParams.length; i++) {
                result[i] = resolveRawClass(typeParams[i], subType, functionalInterface);
            }
        }
        return result;
    }

    public static Type resolveGenericType(Class<?> type, Type subType) {
        Class<?> rawType;
        rawType = subType instanceof ParameterizedType
            ? (Class<?>) ((ParameterizedType) subType).getRawType()
            : (Class<?>) subType;
        if (type.equals(rawType)) {
            return subType;
        }
        Type result;
        if (type.isInterface()) {
            for (Type superInterface : rawType.getGenericInterfaces()) {
                if (superInterface != null
                    && !superInterface.equals(Object.class)
                    && (result = resolveGenericType(type, superInterface)) != null) {
                    return result;
                }
            }
        }
        Type superClass = rawType.getGenericSuperclass();
        return superClass != null
            && !superClass.equals(Object.class)
            && (result = resolveGenericType(type, superClass)) != null
            ? result : null;
    }

    public static Class<?> resolveRawClass(Type genericType, Class<?> subType) {
        return resolveRawClass(genericType, subType, null);
    }

    private static Class<?> resolveRawClass(Type genericType, Class<?> subType, Class<?> functionalInterface) {
        if (genericType instanceof Class) {
            return (Class<?>) genericType;
        } else if (genericType instanceof ParameterizedType) {
            return resolveRawClass(((ParameterizedType) genericType).getRawType(), subType, functionalInterface);
        } else if (genericType instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) genericType;
            Class<?> component = resolveRawClass(arrayType.getGenericComponentType(), subType, functionalInterface);
            return Array.newInstance(component, 0).getClass();
        } else if (genericType instanceof TypeVariable) {
            TypeVariable<?> variable = (TypeVariable<?>) genericType;
            genericType = getTypeVariableMap(subType, functionalInterface).get(variable);
            genericType = genericType == null
                ? resolveBound(variable)
                : resolveRawClass(genericType, subType, functionalInterface);
        }
        return genericType instanceof Class ? (Class<?>) genericType : Unknown.class;
    }

    private static Map<TypeVariable<?>, Type> getTypeVariableMap(final Class<?> targetType, Class<?> functionalInterface) {
        Reference<Map<TypeVariable<?>, Type>> ref = TYPE_VARIABLE_CACHE.get(targetType);
        Map<TypeVariable<?>, Type> map = ref != null ? ref.get() : null;
        if (map == null) {
            map = new HashMap<>();
            if (functionalInterface != null) {
                populateLambdaArgs(functionalInterface, targetType, map);
            }
            populateSuperTypeArgs(targetType.getGenericInterfaces(), map, functionalInterface != null);
            Type genericType = targetType.getGenericSuperclass();
            Class<?> type = targetType.getSuperclass();
            while (type != null && !Object.class.equals(type)) {
                if (genericType instanceof ParameterizedType) {
                    populateTypeArgs((ParameterizedType) genericType, map, false);
                }
                populateSuperTypeArgs(type.getGenericInterfaces(), map, false);
                genericType = type.getGenericSuperclass();
                type = type.getSuperclass();
            }
            type = targetType;
            while (type.isMemberClass()) {
                genericType = type.getGenericSuperclass();
                if (genericType instanceof ParameterizedType) {
                    populateTypeArgs((ParameterizedType) genericType, map, functionalInterface != null);
                }
                type = type.getEnclosingClass();
            }
            if (CACHE_ENABLED) {
                TYPE_VARIABLE_CACHE.put(targetType, new WeakReference<>(map));
            }
        }
        return map;
    }

    private static void populateSuperTypeArgs(final Type[] types, final Map<TypeVariable<?>, Type> map, boolean depthFirst) {
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (!depthFirst) {
                    populateTypeArgs(parameterizedType, map, depthFirst);
                }
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class) {
                    populateSuperTypeArgs(((Class<?>) rawType).getGenericInterfaces(), map, depthFirst);
                }
                if (depthFirst) {
                    populateTypeArgs(parameterizedType, map, depthFirst);
                }
            } else if (type instanceof Class) {
                populateSuperTypeArgs(((Class<?>) type).getGenericInterfaces(), map, depthFirst);
            }
        }
    }

    private static void populateTypeArgs(ParameterizedType type, Map<TypeVariable<?>, Type> map, boolean depthFirst) {
        if (type.getRawType() instanceof Class) {
            TypeVariable<?>[] typeVariables = ((Class<?>) type.getRawType()).getTypeParameters();
            Type[] typeArguments = type.getActualTypeArguments();
            if (type.getOwnerType() != null) {
                Type owner = type.getOwnerType();
                if (owner instanceof ParameterizedType) {
                    populateTypeArgs((ParameterizedType) owner, map, depthFirst);
                }
            }
            for (int i = 0; i < typeArguments.length; i++) {
                TypeVariable<?> variable = typeVariables[i];
                Type typeArgument = typeArguments[i];
                if (typeArgument instanceof Class) {
                    map.put(variable, typeArgument);
                } else if (typeArgument instanceof GenericArrayType) {
                    map.put(variable, typeArgument);
                } else if (typeArgument instanceof ParameterizedType) {
                    map.put(variable, typeArgument);
                } else if (typeArgument instanceof TypeVariable) {
                    TypeVariable<?> typeVariableArgument = (TypeVariable<?>) typeArgument;
                    if (depthFirst) {
                        Type existingType = map.get(variable);
                        if (existingType != null) {
                            map.put(typeVariableArgument, existingType);
                            continue;
                        }
                    }
                    Type resolvedType = map.get(typeVariableArgument);
                    if (resolvedType == null) {
                        resolvedType = resolveBound(typeVariableArgument);
                    }
                    map.put(variable, resolvedType);
                }
            }
        }
    }

    private static Type resolveBound(TypeVariable<?> typeVariable) {
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length == 0) {
            return Unknown.class;
        }
        Type bound = bounds[0];
        if (bound instanceof TypeVariable) {
            bound = resolveBound((TypeVariable<?>) bound);
        }
        return bound == Object.class ? Unknown.class : bound;
    }

    private static void populateLambdaArgs(Class<?> functionalInterface, final Class<?> lambdaType, Map<TypeVariable<?>, Type> map) {
        for (Method m : functionalInterface.getMethods()) {
            if (!isDefaultMethod(m) && !Modifier.isStatic(m.getModifiers()) && !m.isBridge()) {
                Method objectMethod = OBJECT_METHODS.get(m.getName());
                if (objectMethod != null && Arrays.equals(m.getTypeParameters(), objectMethod.getTypeParameters())) {
                    continue;
                }
                Type returnTypeVar = m.getGenericReturnType();
                Type[] paramTypeVars = m.getGenericParameterTypes();
                Member member = getMemberRef(lambdaType);
                if (member == null) {
                    return;
                }
                if (returnTypeVar instanceof TypeVariable) {
                    Class<?> returnType = member instanceof Method
                        ? ((Method) member).getReturnType()
                        : ((Constructor<?>) member).getDeclaringClass();
                    returnType = wrapPrimitives(returnType);
                    if (!returnType.equals(Void.class)) {
                        map.put((TypeVariable<?>) returnTypeVar, returnType);
                    }
                }
                Class<?>[] arguments = member instanceof Method
                    ? ((Method) member).getParameterTypes()
                    : ((Constructor<?>) member).getParameterTypes();
                int paramOffset = 0;
                if (paramTypeVars.length > 0
                && paramTypeVars[0] instanceof TypeVariable
                && paramTypeVars.length == arguments.length + 1) {
                    Class<?> instanceType = member.getDeclaringClass();
                    map.put((TypeVariable<?>) paramTypeVars[0], instanceType);
                    paramOffset = 1;
                }
                int argOffset = 0;
                if (paramTypeVars.length < arguments.length) {
                    argOffset = arguments.length - paramTypeVars.length;
                }
                for (int i = 0; i + argOffset < arguments.length; i++) {
                    if (paramTypeVars[i] instanceof TypeVariable) {
                        map.put((TypeVariable<?>) paramTypeVars[i + paramOffset], wrapPrimitives(arguments[i + argOffset]));
                    }
                }
                return;
            }
        }
    }

    private static boolean isDefaultMethod(Method method) {
        return JAVA_VERSION >= 1.8 && method.isDefault();
    }

    private static Class<?> wrapPrimitives(Class<?> clazz) {
        return clazz.isPrimitive() ? PRIMITIVE_WRAPPERS.get(clazz) : clazz;
    }

    private static Member getMemberRef(Class<?> type) {
        Object constantPool;
        try {
            constantPool = GET_CONSTANT_POOL.invoke(type);
        } catch (Exception ignore) {
            return null;
        }
        Member result = null;
        for (int i = getConstantPoolSize(constantPool) - 1; i >= 0; i--) {
            Member member = getConstantPoolMethodAt(constantPool, i);
            if (member == null
            || (member instanceof Constructor
                && member.getDeclaringClass().getName().equals("java.lang.invoke.SerializedLambda"))
            || member.getDeclaringClass().isAssignableFrom(type)) {
                continue;
            }
            result = member;
            if (!(member instanceof Method) || !isAutoBoxingMethod((Method) member)) {
                break;
            }
        }
        return result;
    }

    private static boolean isAutoBoxingMethod(Method method) {
        Class<?>[] parameters = method.getParameterTypes();
        return method.getName().equals("valueOf")
            && parameters.length == 1
            && parameters[0].isPrimitive()
            && wrapPrimitives(parameters[0]).equals(method.getDeclaringClass());
    }

    private static int getConstantPoolSize(Object constantPool) {
        try {
            return (Integer) GET_CONSTANT_POOL_SIZE.invoke(constantPool);
        } catch (Exception ignore) {
            return 0;
        }
    }

    private static Member getConstantPoolMethodAt(Object constantPool, int i) {
        try {
            return (Member) GET_CONSTANT_POOL_METHOD_AT.invoke(constantPool, i);
        } catch (Exception ignore) {
            return null;
        }
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

    private static final class Unknown {
        private Unknown() {}
    }

    private static final Double JAVA_VERSION;
    private static final Map<Class<?>, Reference<Map<TypeVariable<?>, Type>>> TYPE_VARIABLE_CACHE = Collections
        .synchronizedMap(new WeakHashMap<Class<?>, Reference<Map<TypeVariable<?>, Type>>>());
    private static volatile boolean CACHE_ENABLED = true;
    private static final Map<String, Method> OBJECT_METHODS = new HashMap<>();
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPERS;
    private static Method GET_CONSTANT_POOL;
    private static Method GET_CONSTANT_POOL_SIZE;
    private static Method GET_CONSTANT_POOL_METHOD_AT;

    static {
        JAVA_VERSION = Double.parseDouble(System.getProperty("java.specification.version", "0"));
        Map<Class<?>, Class<?>> types = new HashMap<>();
        types.put(boolean.class, Boolean.class);
        types.put(byte.class, Byte.class);
        types.put(char.class, Character.class);
        types.put(double.class, Double.class);
        types.put(float.class, Float.class);
        types.put(int.class, Integer.class);
        types.put(long.class, Long.class);
        types.put(short.class, Short.class);
        types.put(void.class, Void.class);
        PRIMITIVE_WRAPPERS = Collections.unmodifiableMap(types);
        try {
            for (Method method : Object.class.getDeclaredMethods()) {
                OBJECT_METHODS.put(method.getName(), method);
            }
            Unsafe unsafe = AccessController.doPrivileged((PrivilegedExceptionAction<Unsafe>) () -> {
                final Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);

                return (Unsafe) f.get(null);
            });
            GET_CONSTANT_POOL = Class.class.getDeclaredMethod("getConstantPool");
            String constantPoolName = JAVA_VERSION < 9 ? "sun.reflect.ConstantPool" : "jdk.internal.reflect.ConstantPool";
            Class<?> constantPoolClass = Class.forName(constantPoolName);
            GET_CONSTANT_POOL_SIZE = constantPoolClass.getDeclaredMethod("getSize");
            GET_CONSTANT_POOL_METHOD_AT = constantPoolClass.getDeclaredMethod("getMethodAt", int.class);
            Field overrideField = AccessibleObject.class.getDeclaredField("override");
            long overrideFieldOffset = unsafe.objectFieldOffset(overrideField);
            unsafe.putBoolean(GET_CONSTANT_POOL, overrideFieldOffset, true);
            unsafe.putBoolean(GET_CONSTANT_POOL_SIZE, overrideFieldOffset, true);
            unsafe.putBoolean(GET_CONSTANT_POOL_METHOD_AT, overrideFieldOffset, true);
            Object constantPool = GET_CONSTANT_POOL.invoke(Object.class);
            GET_CONSTANT_POOL_SIZE.invoke(constantPool);
        } catch (Exception ignore) {}
    }

}
