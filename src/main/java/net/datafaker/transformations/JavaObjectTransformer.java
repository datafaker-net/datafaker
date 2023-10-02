package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaObjectTransformer implements Transformer<Object, Object> {
    private static final Map<Schema<Object, ?>, Consumer<Object>> SCHEMA2CONSUMER = new IdentityHashMap<>();
    private static final Map<Class<?>, Constructor<?>> CLASS2CONSTRUCTOR = new IdentityHashMap<>();

    @Override
    public Object apply(Object input, Schema<Object, ?> schema) {
        Class clazz;
        Object result = null;
        if (input instanceof Class) {
            clazz = (Class) input;
        } else {
            clazz = input.getClass();
            result = input;
        }

        if (clazz.isRecord()) {
            Constructor<?> recordConstructor = CLASS2CONSTRUCTOR.get(clazz);
            if (recordConstructor == null) {
                Class<?>[] componentTypes = Arrays.stream(clazz.getRecordComponents())
                    .map(RecordComponent::getType)
                    .toArray(Class<?>[]::new);

                try {
                    recordConstructor = clazz.getDeclaredConstructor(componentTypes);
                    CLASS2CONSTRUCTOR.put(clazz, recordConstructor);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            result = getObject(schema, result, recordConstructor);
        } else if (!hasParameterlessPublicConstructor(clazz)) {
            Constructor<?> primaryConstructor = CLASS2CONSTRUCTOR.get(clazz);
            if (primaryConstructor == null) {
                primaryConstructor = clazz.getDeclaredConstructors()[0];
                CLASS2CONSTRUCTOR.put(clazz, primaryConstructor);
            }

            result = getObject(schema, result, primaryConstructor);
        } else {
            if (result == null) {
                try {
                    Constructor<?> primaryConstructor = CLASS2CONSTRUCTOR.get(clazz);
                    if (primaryConstructor == null) {
                        primaryConstructor = clazz.getDeclaredConstructors()[0];
                        CLASS2CONSTRUCTOR.put(clazz, primaryConstructor);
                    }
                    result = primaryConstructor.newInstance();
                } catch (InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            Consumer<Object> consumer = SCHEMA2CONSUMER.get(schema);
            if (consumer == null) {
                final Field<Object, ?>[] fields = schema.getFields();
                final Map<String, java.lang.reflect.Field> name2ClassField = Stream.of(clazz.getDeclaredFields()).collect(
                    Collectors.toMap(java.lang.reflect.Field::getName, Function.identity()));
                final java.lang.reflect.Field[] rFields = new java.lang.reflect.Field[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    rFields[i] = name2ClassField.get(fields[i].getName());
                    rFields[i].setAccessible(true);
                }
                consumer = classObject -> {
                    try {
                        for (int i = 0; i < fields.length; i++) {
                            rFields[i].set(classObject, fields[i].transform(classObject));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                };
                SCHEMA2CONSUMER.put(schema, consumer);
            }
            consumer.accept(result);
        }
        return result;
    }

    @Override
    public Collection<Object> generate(Iterable<Object> input, Schema<Object, ?> schema) {
        Collection<Object> collection;
        if (input instanceof FakeSequence) {
            if (((FakeSequence) input).isInfinite()) {
                throw new IllegalArgumentException("Should be finite size");
            }
            collection = new ArrayList<>(((FakeSequence<Object>) input).get());
        } else {
            collection = new ArrayList<>();
            for (Object o : input) {
                collection.add(o);
            }
        }
        for (Object elem : collection) {
            apply(elem, schema);
        }
        return collection;
    }

    @Override
    public Object generate(Schema<Object, ?> schema, int limit) {
        throw new UnsupportedOperationException("Object as input is required");
    }

    private Object getObject(Schema<Object, ?> schema, Object result, Constructor<?> recordConstructor) {
        final Field<Object, ?>[] fields = schema.getFields();
        final Object[] values = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            values[i] = fields[i].transform(result);
        }

        try {
            return recordConstructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasParameterlessPublicConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }
}
