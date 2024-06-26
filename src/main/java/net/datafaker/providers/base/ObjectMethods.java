package net.datafaker.providers.base;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ObjectMethods {
    private static final Map<Class<?>, Map<String, Method>> METHODS = new IdentityHashMap<>();

    private static synchronized Map<String, Method> scan(Class<?> clazz) {
        Method[] methods = clazz.getMethods();

        return Stream.of(methods)
            .filter(ObjectMethods::returnsProvider)
            .collect(toMap(Method::getName, method -> method));
    }

    private static boolean returnsProvider(Method method) {
        return method.getParameterCount() == 0
            && method.getDeclaringClass() != Object.class
            && method.getDeclaringClass() != AbstractProvider.class
            && method.getReturnType() != void.class;
    }

    static Method getMethod(Object object, String methodName) {
        return METHODS.computeIfAbsent(object.getClass(), ObjectMethods::scan).get(methodName);
    }
}
