package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;
import org.jspecify.annotations.Nullable;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

import static java.util.Collections.synchronizedMap;

public class JavaObjectTransformer implements Transformer<Object, Object> {
    private static final ClassValue<ConstructionPlan> PLANS = new ClassValue<>() {
        @Override
        protected ConstructionPlan computeValue(Class<?> type) {
            if (type.isRecord()) {
                Class<?>[] componentTypes = Arrays.stream(type.getRecordComponents())
                    .map(RecordComponent::getType)
                    .toArray(Class<?>[]::new);
                try {
                    return new ConstructionPlan(type.getDeclaredConstructor(componentTypes), null);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Failed to initialize class " + type.getName(), e);
                }
            }

            Constructor<?>[] constructors = type.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() == 0) {
                    return new ConstructionPlan(constructor, synchronizedMap(new IdentityHashMap<>()));
                }
            }
            if (constructors.length == 0) {
                throw new RuntimeException("Failed to initialize class " + type.getName() + ", no appropriate public constructor found");
            }
            return new ConstructionPlan(constructors[0], null);
        }
    };

    private Optional<Object> sourceClazz = Optional.empty();

    @Override
    public Object apply(Object input, Schema<Object, ?> schema) {
        Class<?> clazz;
        Object result = null;
        if (input instanceof Class<?> inputClass) {
            clazz = inputClass;
        } else {
            clazz = input.getClass();
            result = input;
        }

        ConstructionPlan plan = PLANS.get(clazz);
        Map<Schema<Object, ?>, Consumer<Object>> fieldWriters = plan.fieldWriters();
        if (fieldWriters == null) {
            return getObject(schema, result, plan.constructor());
        }
        if (result == null) {
            try {
                result = plan.constructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        fieldWriters.computeIfAbsent(schema, key -> fieldWriter(clazz, key)).accept(result);
        return result;
    }

    private static Consumer<Object> fieldWriter(Class<?> type, Schema<Object, ?> schema) {
        net.datafaker.transformations.Field<Object, ?>[] fields = schema.getFields();
        Map<String, Field> declaredFields = Stream.of(type.getDeclaredFields())
            .collect(Collectors.toMap(Field::getName, Function.identity()));
        Field[] targets = new Field[fields.length];
        for (int i = 0; i < fields.length; i++) {
            targets[i] = declaredFields.get(fields[i].getName());
            targets[i].setAccessible(true);
        }
        return target -> {
            for (int i = 0; i < fields.length; i++) {
                try {
                    targets[i].set(target, fields[i].transform(target));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to transform field " + fields[i], e);
                }
            }
        };
    }

    @Override
    public Collection<Object> generate(Iterable<Object> input, Schema<Object, ?> schema) {
        if (input instanceof FakeSequence<?> fakeSequence && fakeSequence.isInfinite()) {
            throw new IllegalArgumentException("Should be finite size: " + fakeSequence);
        }

        Collection<Object> result = new ArrayList<>();
        for (Object element : input) {
            result.add(apply(element, schema));
        }
        return result;
    }

    public JavaObjectTransformer from(Class<?> input) {
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
        return this.generateStream(schema, limit).toList();
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
        final net.datafaker.transformations.Field<Object, ?>[] fields = schema.getFields();
        final @Nullable Object[] values = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            values[i] = fields[i].transform(result);
        }

        try {
            return recordConstructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate " + recordConstructor.getDeclaringClass().getName(), e);
        }
    }

    private record ConstructionPlan(Constructor<?> constructor,
                                    @Nullable Map<Schema<Object, ?>, Consumer<Object>> fieldWriters) {
    }
}
