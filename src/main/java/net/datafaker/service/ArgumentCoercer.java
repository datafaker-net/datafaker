package net.datafaker.service;

import net.datafaker.internal.helper.CopyOnWriteMap;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.SEVERE;

/**
 * Converts the string arguments of an expression like {@code #{number_between '1','10'}} into the parameter
 * types of the method that will be called.
 */
final class ArgumentCoercer {
    // Same logger name as before the extraction, so existing log configuration keeps working.
    private static final Logger LOG = Logger.getLogger(FakeValuesService.class.getName());
    private static final Map<Class<?>, Constructor<?>> CLASS_2_CONSTRUCTOR_CACHE = new CopyOnWriteMap<>(IdentityHashMap::new);

    private ArgumentCoercer() {
    }

    /**
     * Coerce arguments in <em>args</em> into the appropriate types (if possible) for the parameter arguments
     * to <em>accessor</em>.
     *
     * @return array of coerced values if successful, null otherwise
     */
    static Object[] coerceArguments(Method accessor, String[] args) {
        final Object[] coerced = new Object[accessor.getParameterCount()];
        final Class<?>[] parameterTypes = accessor.getParameterTypes();
        for (int i = 0; i < accessor.getParameterCount(); i++) {
            final boolean isVarArg = i == accessor.getParameterCount() - 1 && accessor.isVarArgs();
            final Class<?> toType0 = primitiveToWrapper(parameterTypes[i]);
            final Class<?> toType = isVarArg ? toType0.getComponentType() : toType0;
            try {
                final Object coercedArgument;
                if (toType.isEnum()) {
                    Method method = toType.getMethod("valueOf", String.class);
                    if (isVarArg) {
                        coercedArgument = Array.newInstance(toType, args.length - i);
                        for (int j = i; j < args.length; j++) {
                            String enumArg = args[j].substring(args[j].indexOf(".") + 1);
                            Array.set(coercedArgument, j - i, method.invoke(null, enumArg));
                        }
                    } else {
                        String enumArg = args[i].substring(args[i].indexOf(".") + 1);
                        coercedArgument = method.invoke(null, enumArg);
                    }
                } else {
                    if (isVarArg) {
                        Constructor<?> ctor = CLASS_2_CONSTRUCTOR_CACHE.computeIfAbsent(toType, (__) -> {
                            final Constructor<?>[] constructors = toType.getConstructors();
                            for (Constructor<?> c : constructors) {
                                if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == String.class) {
                                    return getConstructorWithString(toType);
                                }
                            }
                            return null;
                        });
                        if (ctor == null) {
                            return null;
                        }
                        coercedArgument = Array.newInstance(toType, args.length - i);
                        for (int j = i; j < args.length; j++) {
                            Array.set(coercedArgument, j - i, ctor.newInstance(args[j]));
                        }
                    } else if (toType == Character.class) {
                        coercedArgument = args[i] == null ? null : args[i].charAt(0);
                    } else if (Boolean.class == toType) {
                        coercedArgument = Boolean.valueOf(args[i]);
                    } else if (Integer.class == toType) {
                        coercedArgument = Integer.valueOf(args[i]);
                    } else if (Long.class == toType) {
                        coercedArgument = Long.valueOf(args[i]);
                    } else if (Double.class == toType) {
                        coercedArgument = Double.valueOf(args[i]);
                    } else if (Float.class == toType) {
                        coercedArgument = Float.valueOf(args[i]);
                    } else if (Byte.class == toType) {
                        coercedArgument = Byte.valueOf(args[i]);
                    } else if (Short.class == toType) {
                        coercedArgument = Short.valueOf(args[i]);
                    } else if (CharSequence.class.isAssignableFrom(toType)) {
                        coercedArgument = args[i];
                    } else if (BigDecimal.class.isAssignableFrom(toType)) {
                        coercedArgument = new BigDecimal(args[i]);
                    } else if (BigInteger.class.isAssignableFrom(toType)) {
                        coercedArgument = new BigInteger(args[i]);
                    } else {
                        final Constructor<?> ctor = getConstructorWithString(toType);
                        coercedArgument = ctor.newInstance(args[i]);
                    }
                }
                coerced[i] = coercedArgument;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                     InvocationTargetException | NoSuchMethodException | NoSuchMethodRuntimeException e) {
                Throwable cause = unwrap(e);
                Level level = cause instanceof IllegalArgumentException || cause instanceof NoSuchMethodException || cause instanceof NoSuchMethodRuntimeException ? FINE : SEVERE;
                LOG.log(level, "Unable to coerce " + args[i] + " to " + toType.getSimpleName() + " via " + toType.getSimpleName() + "(String) constructor", e);
                return null;
            }
        }
        return coerced;
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new IdentityHashMap<>();

    static {
        PRIMITIVE_WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_WRAPPER_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_WRAPPER_MAP.put(Character.TYPE, Character.class);
        PRIMITIVE_WRAPPER_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_WRAPPER_MAP.put(Integer.TYPE, Integer.class);
        PRIMITIVE_WRAPPER_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_WRAPPER_MAP.put(Double.TYPE, Double.class);
        PRIMITIVE_WRAPPER_MAP.put(Float.TYPE, Float.class);
        PRIMITIVE_WRAPPER_MAP.put(Void.TYPE, Void.class);
    }

    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        if (cls != null && cls.isPrimitive()) {
            return PRIMITIVE_WRAPPER_MAP.get(cls);
        }
        return cls;
    }

    static Throwable unwrap(Throwable e) {
        return e instanceof InvocationTargetException reflection ? unwrap(reflection.getTargetException()) : e;
    }

    private static Constructor<?> getConstructorWithString(Class<?> toType) {
        try {
            return toType.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodRuntimeException(e);
        }
    }

    private static class NoSuchMethodRuntimeException extends RuntimeException {
        public NoSuchMethodRuntimeException(NoSuchMethodException cause) {
            super(cause);
        }
    }
}
