package net.datafaker.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

public class FakeResolver<T> {

    private final Class<T> clazz;

    public FakeResolver(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T generateFromDefaultSchema() {
        checkFakeAnnotation(clazz);

        FakeForSchema fakeForSchemaAnnotation = clazz.getAnnotation(FakeForSchema.class);
        Schema<Object, ?> useSchema = getSchema(fakeForSchemaAnnotation.value());

        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        return (T) jTransformer.apply(clazz, useSchema);
    }

    public T generateFromCustomSchema(Schema<Object, ?> schema) {
        if (Objects.isNull(schema)) {
            return generateFromDefaultSchema();
        }

        JavaObjectTransformer jTransformer = new JavaObjectTransformer();
        return (T) jTransformer.apply(clazz, schema);
    }

    private Schema<Object, ?> getSchema(String[] pathToSchema) {
        if (pathToSchema.length != 0) {
            try {
                final int sharpIndex = pathToSchema[0].indexOf('#');
                final Class<?> classToCall;
                final String methodName;
                if (sharpIndex >= 0) {
                    classToCall = Class.forName(pathToSchema[0].substring(0, sharpIndex));
                    methodName = pathToSchema[0].substring(sharpIndex + 1);
                } else {
                    classToCall = this.clazz.getEnclosingClass();
                    methodName = pathToSchema[0];
                }
                Method myStaticMethod = classToCall.getMethod(methodName);
                myStaticMethod.setAccessible(true);
                return (Schema<Object, ?>) myStaticMethod.invoke(null);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    private void checkFakeAnnotation(Class<T> clazz) {
        Objects.requireNonNull(clazz, "The class is null.");

        if (!clazz.isAnnotationPresent(FakeForSchema.class)) {
            throw new RuntimeException("The class " + clazz.getSimpleName() + " is not annotated with Fake");
        }
    }
}
