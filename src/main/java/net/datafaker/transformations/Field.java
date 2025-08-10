package net.datafaker.transformations;

import net.datafaker.providers.base.AbstractProvider;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Field<IN, OUT> {
    String getName();

    OUT transform(IN input);

    static <MyObject, MyType> SimpleField<MyObject, MyType> field(
        String name, Function<MyObject, MyType> transform) {
        return new SimpleField<>(name, transform);
    }

    static <MyObject, MyType> SimpleField<MyObject, MyType> field(
        String name, Supplier<MyType> supplier) {
        return new SimpleField<>(name, supplier);
    }

    @SafeVarargs
    static <MyObject extends AbstractProvider<?>, MyType>
    CompositeField<MyObject, MyType> compositeField(
        String name, Field<MyObject, MyType>... fields) {
        return new CompositeField<>(name, fields);
    }

}
