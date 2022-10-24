package net.datafaker.transformations;


import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleField<MyObject, MyType> implements Field<MyObject, MyType> {
    private final String name;
    private final Function<MyObject, MyType> transform;
    private final Supplier<MyType> supplier;

    protected SimpleField(String name, Function<MyObject, MyType> transform) {
        this(name, transform, null);
    }

    protected SimpleField(String name, Supplier<MyType> supplier) {
        this(name, null, supplier);
    }

    private SimpleField(String name, Function<MyObject, MyType> transform, Supplier<MyType> supplier) {
        this.name = name;
        this.transform = transform;
        this.supplier = supplier;
        if (this.transform == null && this.supplier == null) {
            throw new IllegalArgumentException("Either transform or supplier should be non-null");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MyType transform(MyObject input) {
        if (transform == null) {
            return supplier.get();
        }
        if (input == null) {
            throw new IllegalArgumentException("Input could be null only if suppliers are defined");
        }
        return transform.apply(input);
    }

    public Function<MyObject, MyType> getTransform() {
        return transform;
    }

    public Supplier<MyType> getSupplier() {
        return supplier;
    }
}
