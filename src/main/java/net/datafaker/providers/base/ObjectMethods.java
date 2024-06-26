package net.datafaker.providers.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ObjectMethods {
    private static final Map<Class<?>, Map<String, Method>> METHODS_BY_NAME = new IdentityHashMap<>();
    private static final Map<Class<?>, Map<String, Method>> METHODS_BY_RETURN_TYPE = new IdentityHashMap<>();
    private static final Set<String> IGNORED_METHODS = Set.of("equals", "hashCode", "toString", "Builder", "stream");

    private static synchronized Map<String, Method> scanMethodsByName(Class<?> clazz) {
        return Stream.of(clazz.getMethods())
            .filter(ObjectMethods::isUseful)
            .collect(toMap(Method::getName, method -> method));
    }

    private static synchronized Map<String, Method> scanMethodsByReturnType(Class<?> clazz) {
        return Stream.of(clazz.getMethods())
            .filter(ObjectMethods::isUseful)
            .collect(toMap(method -> method.getReturnType().getSimpleName(), method -> method));
    }

    /**
     * Later we could mark all provider methods with some annotation like "@Provider" instead of this shaky logic
     */
    private static boolean isUseful(Method method) {
        return method.getParameterCount() == 0
            && method.getDeclaringClass() != Object.class
            && method.getReturnType() != void.class
            && !IGNORED_METHODS.contains(method.getName());
    }

    public static Method getMethodByName(Object object, String methodName) {
        return METHODS_BY_NAME.computeIfAbsent(object.getClass(), ObjectMethods::scanMethodsByName).get(methodName);
    }

    private static Method getMethodByReturnType(Object object, String returnTypeSimpleName) {
        return METHODS_BY_RETURN_TYPE.computeIfAbsent(object.getClass(), ObjectMethods::scanMethodsByReturnType).get(returnTypeSimpleName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T executeMethodByReturnType(Object object, String returnTypeSimpleName) {
        try {
            Method method = getMethodByReturnType(object, returnTypeSimpleName);
            if (method == null) return null;
            method.setAccessible(true);
            return (T) method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to call method %s.%s()".formatted(object.getClass().getName(), returnTypeSimpleName), e);
        }
    }
}
