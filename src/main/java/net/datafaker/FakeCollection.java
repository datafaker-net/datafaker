package net.datafaker;

import net.datafaker.service.RandomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class FakeCollection<T> {
    private final RandomService randomService = new RandomService();
    private final List<Supplier<T>> suppliers;
    private final int minLength;
    private final int maxLength;

    public List<T> get() {
        List<T> result = new ArrayList<>();
        int size = randomService.nextInt(minLength, maxLength);
        while (result.size() < size) {
            result.add(suppliers.get(randomService.nextInt(suppliers.size())).get());
        }
        return result;
    }

    private FakeCollection(List<Supplier<T>> suppliers, int minLength, int maxLength) {
        this.suppliers = suppliers;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public static class Builder<T> {
        private final List<Supplier<T>> suppliers = new ArrayList<>();
        private int minLength = -1; // negative means same as maxLength
        private int maxLength = 10;

        public Builder<T> minLen(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public Builder<T> maxLen(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        @SafeVarargs
        public final Builder<T> suppliers(Supplier<T>... suppliers) {
            Objects.requireNonNull(suppliers);
            this.suppliers.addAll(Arrays.asList(suppliers));
            return this;
        }

        public FakeCollection<T> build() {
            if (minLength > maxLength || maxLength < 0) {
                throw new IllegalArgumentException("Max length must be not less than min length and not negative");
            }
            minLength = minLength < 0 ? maxLength : minLength;
            return new FakeCollection<>(suppliers, minLength, maxLength);
        }
    }
}
