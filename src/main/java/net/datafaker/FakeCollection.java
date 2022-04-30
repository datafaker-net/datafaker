package net.datafaker;

import net.datafaker.service.RandomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class FakeCollection<T> {
    private final RandomService randomService;
    private final List<Supplier<T>> suppliers;
    private final double nullRate;
    private final int minLength;
    private final int maxLength;

    private FakeCollection(List<Supplier<T>> suppliers, int minLength, int maxLength, RandomService randomService, double nullRate) {
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

    public List<T> get() {
        List<T> result = new ArrayList<>();
        int size = randomService.nextInt(minLength, maxLength);
        while (result.size() < size) {
            result.add(singleton());
        }
        return result;
    }

    public static class Builder<T> {
        private final List<Supplier<T>> suppliers = new ArrayList<>();
        private int minLength = -1; // negative means same as maxLength
        private int maxLength = 10;
        private double nullRate = 0d;
        private Faker faker;

        public Builder<T> faker(Faker faker) {
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

        public FakeCollection<T> build() {
            if (minLength > maxLength || maxLength < 0) {
                throw new IllegalArgumentException("Max length must be not less than min length and not negative");
            }
            minLength = minLength < 0 ? maxLength : minLength;

            RandomService randomService;
            if(faker == null) {
                randomService = new RandomService();
            } else {
                randomService = faker.random();
            }

            return new FakeCollection<>(suppliers, minLength, maxLength, randomService, nullRate);
        }
    }
}
