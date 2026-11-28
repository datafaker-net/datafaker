package net.datafaker.transformations;


import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class SimpleField<MyObject, MyType> implements Field<MyObject, MyType> {
    private final @Nullable String name;
    private final @Nullable Function<MyObject, MyType> transform;
    private final @Nullable Supplier<MyType> supplier;

    protected SimpleField(@Nullable String name, Function<MyObject, MyType> transform) {
        this.name = name;
        this.transform = requireNonNull(transform);
        this.supplier = null;
    }

    protected SimpleField(@Nullable String name, Supplier<MyType> supplier) {
        this.name = name;
        this.transform = null;
        this.supplier = requireNonNull(supplier);
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @Nullable
    @Override
    public MyType transform(@Nullable MyObject input) {
        if (transform == null) {
            return requireNonNull(supplier).get();
        }
        requireNonNull(input, "Input could be null only if suppliers are defined");
        return transform.apply(input);
    }

    @Nullable
    public Function<MyObject, MyType> getTransform() {
        return transform;
    }

    @Nullable
    public Supplier<MyType> getSupplier() {
        return supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleField<?, ?> that)) return false;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(transform, that.transform)) return false;
        return Objects.equals(supplier, that.supplier);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
