package net.datafaker.transformations;

import net.datafaker.providers.base.AbstractProvider;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Field<IN, OUT> {
    @Nullable
    String getName();

    @Nullable
    OUT transform(@Nullable IN input);

    static <MyObject, MyType> SimpleField<MyObject, MyType> field(
        String name, Function<MyObject, MyType> transform) {
        return new SimpleField<>(name, transform);
    }

    static <MyObject, MyType> SimpleField<MyObject, MyType> field(
        @Nullable String name, Supplier<@Nullable MyType> supplier) {
        return new SimpleField<>(name, supplier);
    }

    static <MyObject extends AbstractProvider<?>, MyType>
    CompositeField<MyObject, MyType> compositeField(
        @Nullable String name, Field<MyObject, MyType>[] fields) {
        return new CompositeField<>(name, fields);
    }

}
