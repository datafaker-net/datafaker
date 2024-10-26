package net.datafaker.internal.helper;

import java.util.function.Supplier;

public class LazyEvaluated<T> {
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
        return value;
    }
}
