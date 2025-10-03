package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaObjectTransformer implements Transformer<Object, Object> {
    private static final Map<Schema<Object, ?>, Consumer<Object>> SCHEMA2CONSUMER = new IdentityHashMap<>();
    private static final Map<Class<?>, Constructor<?>> CLASS2CONSTRUCTOR = new IdentityHashMap<>();

    private Optional<Object> sourceClazz = Optional.empty();

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
                    throw new RuntimeException("Failed to initialize class " + clazz.getName(), e);
                }
            }

            result = getObject(schema, result, recordConstructor);
        } else if (!hasParameterlessPublicConstructor(clazz)) {
            Constructor<?> primaryConstructor = CLASS2CONSTRUCTOR.computeIfAbsent(clazz, c -> getAnyPublicConstructor(c).orElse(null));

            if (primaryConstructor == null) {
                throw new RuntimeException("Failed to initialize class " + clazz.getName() + ", no appropriate public constructor found");
            }

            result = getObject(schema, result, primaryConstructor);
        } else {
            if (result == null) {
                try {
                    Constructor<?> primaryConstructor = CLASS2CONSTRUCTOR.computeIfAbsent(clazz, c -> getParameterlessPublicConstructor(c).orElse(null));
                    if (primaryConstructor != null) {
                        result = primaryConstructor.newInstance();
                    } else {
                        throw new RuntimeException("Failed to locate a parameterless public constructor for class " + clazz.getName());
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
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
                    for (int i = 0; i < fields.length; i++) {
                        try {
                            rFields[i].set(classObject, fields[i].transform(classObject));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Failed to transform field " + fields[i], e);
                        }
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
        if (input instanceof FakeSequence<Object> fakeSequence) {
            if (fakeSequence.isInfinite()) {
                throw new IllegalArgumentException("Should be finite size: " + fakeSequence);
            }
            collection = new ArrayList<>(fakeSequence.get());
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

    public JavaObjectTransformer from(Class input) {
        sourceClazz = Optional.of(input);
        return this;
    }

    /**
     * The output is tied to provided Class source.
     * An empty source will output an empty stream.
     *
     * Configure available input with {@link #from(Class source)}.
     */
    @Override
    public Stream<Object> generateStream(final Schema<Object, ?> schema, long limit) {
        if(sourceClazz.isEmpty())
            return Stream.empty();
        else
            return Stream
                .generate(() -> apply(sourceClazz.get(), schema))
                .limit(limit);
    }

    @Override
    public void writeToOutputStream(OutputStream outputStream, Schema<Object, ?> schema, long limit) {
        // Before implementing please provide a valid use case
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Collection<Object> generate(Schema<Object, ?> schema, int limit) {
        return this.generateStream(schema, limit).collect(Collectors.toList());
    }

    @Override
    public String getStartStream(Schema<Object, ?> schema) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getEndStream() {
        throw new UnsupportedOperationException();
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
            throw new RuntimeException("Failed to instantiate " + recordConstructor.getDeclaringClass().getName(), e);
        }
    }

    private boolean hasParameterlessPublicConstructor(Class<?> clazz) {
        return getParameterlessPublicConstructor(clazz).isPresent();
    }

    private Optional<Constructor<?>> getParameterlessPublicConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors())
            .filter(constructor -> constructor.getParameterCount() == 0)
            .filter(constructor -> Modifier.isPublic(constructor.getModifiers()))
            .findFirst();
    }

    private Optional<Constructor<?>> getAnyPublicConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors())
            .filter(constructor -> Modifier.isPublic(constructor.getModifiers()))
            .findFirst();
    }
}
