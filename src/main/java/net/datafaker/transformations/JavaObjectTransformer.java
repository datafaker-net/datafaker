package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaObjectTransformer implements Transformer<Object, Object> {
    @Override
    public Object apply(Object input, Schema<Object, ?> schema) {
        Class clazz;
        Object result;
        if (input instanceof Class) {
            clazz = (Class) input;
            try {
                result = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            clazz = input.getClass();
            result = input;
        }
        Field<Object, ?>[] fields = schema.getFields();
        Map<String, java.lang.reflect.Field> name2ClassField = Stream.of(clazz.getDeclaredFields()).collect(
            Collectors.toMap(java.lang.reflect.Field::getName, Function.identity()));
        try {
            for (Field<Object, ?> f: fields) {
                name2ClassField.get(f.getName()).setAccessible(true);
                name2ClassField.get(f.getName()).set(result, f.transform(result));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Collection<Object> generate(FakeSequence<Object> input, Schema<Object, ?> schema) {
        if (input.isInfinite()) {
            throw new IllegalArgumentException("Should be finite size");
        }
        Collection<Object> collection = new ArrayList<>(input.get());
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
