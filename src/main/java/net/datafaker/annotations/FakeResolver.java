package net.datafaker.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

import net.datafaker.internal.helper.COWMap;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

public class FakeResolver<T> {

    private static final JavaObjectTransformer JAVA_OBJECT_TRANSFORMER = new JavaObjectTransformer();
    private static final Map<Class<?>, FakeResolver<?>> CLASS_2_FAKE_RESOLVER = new COWMap<>(IdentityHashMap::new);

    private static final Map<Class<?>, Schema<Object, ?>> DEFAULT_SCHEMA_CACHE = new COWMap<>(IdentityHashMap::new);

    private final Class<T> clazz;

    private FakeResolver(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> FakeResolver<T> of(Class<T> clazz) {
        var fakeFactory = CLASS_2_FAKE_RESOLVER.get(clazz);
        if (fakeFactory == null) {
            fakeFactory = new FakeResolver<>(clazz);
            CLASS_2_FAKE_RESOLVER.put(clazz, fakeFactory);
        }
        return (FakeResolver<T>) fakeFactory;
    }

    public T generate(Schema<Object, ?> schema) {
        if (Objects.isNull(schema)) {
            return generateFromDefaultSchema();
        }

        return (T) JAVA_OBJECT_TRANSFORMER.apply(clazz, schema);
    }

    private T generateFromDefaultSchema() {
        Schema<Object, ?> useSchema = DEFAULT_SCHEMA_CACHE.get(clazz);
        if (useSchema == null) {
            checkFakeAnnotation(clazz);

            FakeForSchema fakeForSchemaAnnotation = clazz.getAnnotation(FakeForSchema.class);
            useSchema = getSchema(fakeForSchemaAnnotation.value());
            DEFAULT_SCHEMA_CACHE.put(clazz, useSchema);
        }

        return (T) JAVA_OBJECT_TRANSFORMER.apply(clazz, useSchema);
    }

    private Schema<Object, T> getSchema(String pathToSchema) {
        if (pathToSchema != null) {
            try {
                final int sharpIndex = pathToSchema.indexOf('#');
                final Class<?> classToCall;
                final String methodName;
                if (sharpIndex >= 0) {
                    classToCall = Class.forName(pathToSchema.substring(0, sharpIndex));
                    methodName = pathToSchema.substring(sharpIndex + 1);
                } else {
                    classToCall = this.clazz.getEnclosingClass();
                    methodName = pathToSchema;
                }
                Method myStaticMethod = classToCall.getMethod(methodName);
                myStaticMethod.setAccessible(true);
                return (Schema<Object, T>) myStaticMethod.invoke(null);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("The path to the schema is empty.");
        }
    }

    private void checkFakeAnnotation(Class<T> clazz) {
        Objects.requireNonNull(clazz, "The class is null.");

        if (!clazz.isAnnotationPresent(FakeForSchema.class)) {
            throw new RuntimeException("The class %s is not annotated with Fake".formatted(clazz.getSimpleName()));
        }
    }
}
