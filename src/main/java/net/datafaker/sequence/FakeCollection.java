package net.datafaker.sequence;

import net.datafaker.service.RandomService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class FakeCollection<T> extends FakeSequence<T> {
    private static final int FAKE_COLLECTION_DEFAULT_SIZE = 10;

    private FakeCollection(List<Supplier<T>> suppliers, int minLength, int maxLength, RandomService randomService, double nullRate) {
        super(suppliers, minLength, maxLength, randomService, nullRate);
    }

    @SuppressWarnings("unchecked")
    public List<T> get() {
        int size = randomService.nextInt(minLength, maxLength);
        List<T> result = new ArrayList<>(size);
        while (result.size() < size) {
            result.add(singleton());
        }
        return result;
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

        public FakeCollection<T> build() {
            if (maxLength < 0) {
                maxLen(FAKE_COLLECTION_DEFAULT_SIZE);
            }

            if (minLength > maxLength) {
                throw new IllegalArgumentException("Max length must be not less than min length and not negative");
            }
            minLength = minLength < 0 ? maxLength : minLength;

            RandomService randomService;
            if (faker == null) {
                randomService = new RandomService();
            } else {
                randomService = faker.random();
            }

            return new FakeCollection<>(suppliers, minLength, maxLength, randomService, nullRate);
        }
    }
}
