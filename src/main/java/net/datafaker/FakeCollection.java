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
    private final int minLength;
    private final int maxLength;

    public T singleton() {
        return suppliers.get(randomService.nextInt(suppliers.size())).get();
    }

    public List<T> get() {
        List<T> result = new ArrayList<>();
        int size = randomService.nextInt(minLength, maxLength);
        while (result.size() < size) {
            result.add(singleton());
        }
        return result;
    }

    private FakeCollection(List<Supplier<T>> suppliers, int minLength, int maxLength, RandomService randomService) {
        this.suppliers = suppliers;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.randomService = randomService;
    }

    public static class Builder<T> {
        private final List<Supplier<T>> suppliers = new ArrayList<>();
        private int minLength = -1; // negative means same as maxLength
        private int maxLength = 10;
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

            return new FakeCollection<>(suppliers, minLength, maxLength, randomService);
        }
    }

    @Deprecated // use net.datafaker.fileformats.Format.toCsv
    public CsvBuilder<T> toCsv() {
        return new CsvBuilder<T>().collection(this);
    }

    public static class Csv<T> {
        private final FakeCollection<T> collection;
        private final String separator;
        private final char quote;
        private final List<Supplier<String>> headers;
        private final List<Function<T, String>> columns;

        Csv(FakeCollection<T> collection, String separator, char quote, List<Supplier<String>> headers, List<Function<T, String>> columns) {
            this.collection = collection;
            this.separator = separator;
            this.quote = quote;
            this.headers = headers;
            this.columns = columns;
        }

        public String get() {
            if (headers != null && columns != null && headers.size() != columns.size()) {
                throw new IllegalArgumentException("Length of headers should be equal to length of columns");
            }
            StringBuilder sb = new StringBuilder();
            if (headers != null) {
                addLine(sb, integer -> headers.get(integer).get());
            }

            List<T> res = collection.get();
            for (int line = 0; line < res.size(); line++) {
                int rowNum = line;
                addLine(sb, integer -> columns.get(integer).apply(res.get(rowNum)));
            }
            return sb.toString();
        }

        private void addLine(StringBuilder result, Function<Integer, String> function) {
            for (int i = 0; i < columns.size(); i++) {
                result.append(quote);
                String header = String.valueOf(function.apply(i));
                for (int j = 0; j < header.length(); j++) {
                    if (quote == header.charAt(j)) {
                        result.append(quote);
                    }
                    result.append(header.charAt(j));
                }
                result.append(quote);
                result.append(i == columns.size() - 1 ? System.lineSeparator() : separator);
            }
        }
    }

    @Deprecated // use net.datafaker.fileformats.Format.toCsv
    public static class CsvBuilder<T> {
        private String separator = ",";
        private char quote = '"';
        private FakeCollection<T> collection;
        private Supplier<String>[] headers;
        private Function<T, String>[] columns;

        private CsvBuilder<T> collection(FakeCollection<T> collection) {
            this.collection = collection;
            return this;
        }

        public CsvBuilder<T> separator(String separator) {
            this.separator = separator;
            return this;
        }

        public CsvBuilder<T> quote(char quote) {
            this.quote = quote;
            return this;
        }

        @SafeVarargs
        public final CsvBuilder<T> headers(Supplier<String> ... headers) {
            this.headers = headers;
            return this;
        }

        @SafeVarargs
        public final CsvBuilder<T> columns(Function<T, String> ... columns) {
            this.columns = columns;
            return this;
        }

        Csv<T> build() {
            return new Csv(collection, separator, quote, headers == null ? null : Arrays.asList(headers), columns == null ? null : Arrays.asList(columns));
        }
    }
}
