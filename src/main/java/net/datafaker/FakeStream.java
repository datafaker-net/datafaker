package net.datafaker;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.service.RandomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FakeStream<T> {
    private final RandomService randomService;
    private final List<Supplier<T>> suppliers;
    private final double nullRate;
    private final int minLength;
    private final int maxLength;

    private FakeStream(List<Supplier<T>> suppliers, int minLength, int maxLength, RandomService randomService, double nullRate) {
        this.suppliers = suppliers;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.randomService = randomService;
        this.nullRate = nullRate;
    }

    public T singleton() {
        if (nullRate == 0d || randomService.nextDouble() >= nullRate) {
            return suppliers.get(randomService.nextInt(suppliers.size())).get();
        }
        return null;
    }

    public Stream<T> get() {
        if (isInfinite()) {
            return Stream.generate(this::singleton);
        }

        int size = randomService.nextInt(minLength, maxLength);
        return Stream.generate(this::singleton).limit(size);
    }

    public boolean isInfinite() {
        return maxLength < 0;
    }

    public static class Builder<T> {
        private final List<Supplier<T>> suppliers;
        private int minLength = -1; // negative means same as maxLength
        private int maxLength = -1; // negative means infinite stream
        private double nullRate = 0d;
        private BaseProviders faker;

        public Builder() {
            suppliers = new ArrayList<>();
        }

        public Builder(List<Supplier<T>> list) {
            suppliers = new ArrayList<>(list);
        }

        @SafeVarargs
        public Builder(Supplier<T>... elems) {
            suppliers = new ArrayList<>(Arrays.asList(elems));
        }

        public Builder<T> faker(BaseProviders faker) {
            this.faker = faker;
            return this;
        }

        public Builder<T> minLen(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public Builder<T> maxLen(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Builder<T> len(int length) {
            return len(length, length);
        }

        public Builder<T> len(int minLength, int maxLength) {
            this.maxLength = maxLength;
            this.minLength = minLength;
            return this;
        }

        public Builder<T> nullRate(double nullRate) {
            if (nullRate < 0 || nullRate > 1) {
                throw new IllegalArgumentException("Null rate should be between 0 and 1");
            }
            this.nullRate = nullRate;
            return this;
        }

        @SafeVarargs
        public final Builder<T> suppliers(Supplier<T>... suppliers) {
            Objects.requireNonNull(suppliers);
            this.suppliers.addAll(Arrays.asList(suppliers));
            return this;
        }

        public FakeStream<T> build() {
            if (maxLength >= 0 && minLength > maxLength) {
                throw new IllegalArgumentException("Max length must be not less than min length and not negative");
            }
            minLength = minLength < 0 ? maxLength : minLength;

            RandomService randomService;
            if (faker == null) {
                randomService = new RandomService();
            } else {
                randomService = faker.random();
            }

            return new FakeStream<>(suppliers, minLength, maxLength, randomService, nullRate);
        }

        public Stream<T> generate() {
            return build().get();
        }
    }
}
