package net.datafaker.internal.helper;

import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class LazyEvaluated<T> {
    @Nullable
    private volatile T value;
    private final Supplier<T> supplier;

    public LazyEvaluated(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = supplier.get();
                }
            }
        }
        return requireNonNull(value, "Null value not allowed");
    }
}
