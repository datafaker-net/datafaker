package net.datafaker.sequence;

import net.datafaker.service.RandomService;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FakeStream<T> extends FakeSequence<T> {
    private FakeStream(List<Supplier<T>> suppliers, int minLength, int maxLength, RandomService randomService, double nullRate) {
        super(suppliers, minLength, maxLength, randomService, nullRate);
    }

    @SuppressWarnings("unchecked")
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

    @Override
    public Iterator<T> iterator() {
        return get().iterator();
    }

    public static class Builder<T> extends FakeSequence.Builder<T> {
        public Builder() {
            super();
        }

        public Builder(List<Supplier<T>> list) {
            super(list);
        }

        @SafeVarargs
        public Builder(Supplier<T>... elems) {
            super(elems);
        }

        @Override
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
    }
}
