package net.datafaker.sequence;

import net.datafaker.providers.base.BaseProviders;
import net.datafaker.service.RandomService;

import java.util.*;
import java.util.function.Supplier;

public abstract class FakeSequence<T> implements Iterable<T> {
    private final RandomService randomService;
    private final List<Supplier<T>> suppliers;
    private final double nullRate;
    private final int minLength;
    private final int maxLength;

    protected FakeSequence(List<Supplier<T>> suppliers, int minLength, int maxLength, RandomService randomService, double nullRate) {
        this.suppliers = suppliers;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.randomService = randomService;
        this.nullRate = nullRate;
    }

    protected RandomService getRandomService() {
        return randomService;
    }

    protected List<Supplier<T>> getSuppliers() {
        return suppliers;
    }

    protected double getNullRate() {
        return nullRate;
    }

    protected int getMinLength() {
        return minLength;
    }

    protected int getMaxLength() {
        return maxLength;
    }

    public abstract <Sequence> Sequence get();

    public boolean isInfinite() {
        return false;
    }

    public T singleton() {
        if (getNullRate() == 0d || getRandomService().nextDouble() >= getNullRate()) {
            return getSuppliers().get(getRandomService().nextInt(getSuppliers().size())).get();
        }
        return null;
    }

    public static abstract class Builder<T> {
        protected final List<Supplier<T>> suppliers;
        protected int minLength = -1;
        protected int maxLength = -1;
        protected double nullRate = 0d;
        protected BaseProviders faker;

        protected Builder() {
            suppliers = new ArrayList<>();
        }

        protected Builder(List<Supplier<T>> list) {
            suppliers = new ArrayList<>(list);
        }

        @SafeVarargs
        protected Builder(Supplier<T>... elems) {
            suppliers = new ArrayList<>(Arrays.asList(elems));
        }

        public FakeSequence.Builder<T> faker(BaseProviders faker) {
            this.faker = faker;
            return this;
        }

        public FakeSequence.Builder<T> minLen(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public FakeSequence.Builder<T> maxLen(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public FakeSequence.Builder<T> len(int length) {
            return len(length, length);
        }

        public FakeSequence.Builder<T> len(int minLength, int maxLength) {
            this.maxLength = maxLength;
            this.minLength = minLength;
            return this;
        }

        public FakeSequence.Builder<T> nullRate(double nullRate) {
            if (nullRate < 0 || nullRate > 1) {
                throw new IllegalArgumentException("Null rate should be between 0 and 1 (received: %s)".formatted(nullRate));
            }
            this.nullRate = nullRate;
            return this;
        }

        @SafeVarargs
        public final FakeSequence.Builder<T> suppliers(Supplier<T>... suppliers) {
            Objects.requireNonNull(suppliers);
            this.suppliers.addAll(Arrays.asList(suppliers));
            return this;
        }

        public abstract FakeSequence<T> build();

        /**
         * @param <S> might be Stream or List.
         * @return List or Stream with generated random values.
         */
        public <S> S generate() {
            return build().get();
        }
    }

}
