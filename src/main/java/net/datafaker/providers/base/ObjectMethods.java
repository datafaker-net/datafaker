package net.datafaker.providers.base;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectMethods {
    private static final Map<Class<?>, Map<String, Method>> METHODS = new IdentityHashMap<>();

    static void scan(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        METHODS.putIfAbsent(clazz, new ConcurrentHashMap<>(methods.length));
        for (Method method: methods) {
            if (method.getParameterCount() > 0
                || method.getDeclaringClass() == Object.class
                || method.getDeclaringClass() == AbstractProvider.class
                || method.getReturnType() == void.class
            ) {
                continue;
            }

            String methodName = method.getName();

            Map<String, Method> methodMap = METHODS.get(clazz);
            if (methodMap == null) {
                synchronized (ObjectMethods.class) {
                    methodMap = METHODS.get(clazz);
                    if (methodMap == null) {
                        METHODS.put(clazz, new ConcurrentHashMap<>(methods.length));
                        methodMap = METHODS.get(clazz);
                    }
                }
            }
            methodMap.put(methodName, method);
        }
    }

    static Method getMethod(Object object, String methodName) {
        Map<String, Method> map = METHODS.get(object.getClass());
        return map == null ? null : map.get(methodName);
    }
}
