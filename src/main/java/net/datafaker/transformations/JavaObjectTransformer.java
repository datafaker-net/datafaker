package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

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
    private static final Map<Class<?>, Object> CLASS2RESULT = new IdentityHashMap<>();
    private static final Map<Schema<Object, ?>, Consumer<Object>> SCHEMA2CONSUMER = new IdentityHashMap<>();

    @Override
    public Object apply(Object input, Schema<Object, ?> schema) {
        Class clazz;
        Object result;
        if (input instanceof Class) {
            clazz = (Class) input;
            result = CLASS2RESULT.get(clazz);
        } else {
            clazz = input.getClass();
            result = input;
        }

        if (result == null) {
            if (clazz.isRecord()) {
                Class<?>[] componentTypes = Arrays.stream(clazz.getRecordComponents())
                    .map(RecordComponent::getType)
                    .toArray(Class<?>[]::new);

                final Field<Object, ?>[] fields = schema.getFields();
                final Object[] values = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    values[i] = fields[i].transform(result);
                }

                try {
                    result = clazz.getDeclaredConstructor(componentTypes).newInstance(values);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

                return result;
            } else {
                try {
                    result = clazz.getDeclaredConstructor().newInstance();
                    CLASS2RESULT.put(clazz, result);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
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
            for (Object o: input) {
                collection.add(o);
            }
        }
        for (Object elem: collection) {
            apply(elem, schema);
        }
        return collection;
    }

    @Override
    public Object generate(Schema<Object, ?> schema, int limit) {
        throw new UnsupportedOperationException("Object as input is required");
    }
}
