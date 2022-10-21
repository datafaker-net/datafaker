package net.datafaker.formats;

import net.datafaker.sequence.FakeSequence;
import net.datafaker.sequence.FakeStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Deprecated // Use CsvTransformer
public class Csv<T> {
    public static final char DEFAULT_QUOTE = '"';
    public static final String DEFAULT_SEPARATOR = ",";

    private final FakeSequence<T> sequence;
    private final String separator;
    private final char quote;
    private final boolean withHeader;
    private final List<CollectionColumn<T>> columns;
    private final int limit;

    Csv(FakeSequence<T> sequence, String separator, char quote, boolean withHeader, List<CollectionColumn<T>> columns, int limit) {
        this.sequence = sequence;
        this.separator = separator;
        this.quote = quote;
        this.withHeader = withHeader;
        this.columns = columns;
        this.limit = limit == -1 && sequence == null ? 10 : limit;
    }

    public String get() {
        if (columns == null) {
            throw new IllegalArgumentException("Length of headers should be equal to length of columns");
        }

        if (sequence != null && sequence.isInfinite() && limit < 0) {
            throw new IllegalArgumentException("The sequence should be finite of size");
        }

        StringBuilder sb = new StringBuilder();
        if (withHeader) {
            addLine(sb, integer -> columns.get(integer).getName());
        }

        List<T> res = getValuesFrom(sequence, limit);
        final int initialLength = sb.length();
        int maxLength = 0;
        final int totalLines = (res == null ? limit : limit != -1 ? Math.min(limit, res.size()) : res.size());
        for (int line = 0; line < totalLines; line++) {
            int rowNum = line;
            int current = sb.length();
            addLine(sb, integer -> columns.get(integer).getValue(res == null || res.isEmpty() ? null : res.get(rowNum)));
            maxLength = Math.max(maxLength, sb.length() - current);
            sb.ensureCapacity(initialLength + maxLength * totalLines);
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

    private List<T> getValuesFrom(FakeSequence<T> sequence, int limitValue) {
        if (sequence == null) {
            return null;
        }

        if (sequence instanceof FakeStream) {
            Stream<T> stream = sequence.get();
            if (limitValue >= 0) {
                stream = stream.limit(limitValue);
            }

            return stream.collect(Collectors.toList());
        }

        return sequence.get();
    }

    public static class Column extends CollectionColumn<String> {
        private Column(Supplier<String> name, Supplier<String> valueSupplier) {
            super(name, valueSupplier);
        }

        public static Column of(String name, Supplier<String> valueSupplier) {
            return new Column(() -> name, valueSupplier);
        }

        public static Column of(Supplier<String> name, Supplier<String> valueSupplier) {
            return new Column(name, valueSupplier);
        }
    }

    public static class CollectionColumn<T> {
        private final Supplier<String> name;
        private final Function<T, String> valueSupplier;

        private CollectionColumn(Supplier<String> name, Supplier<String> valueSupplier) {
            this.name = name;
            this.valueSupplier = t -> valueSupplier.get();
        }

        private CollectionColumn(Supplier<String> name, Function<T, String> valueSupplier) {
            this.name = name;
            this.valueSupplier = valueSupplier;
        }

        public static <T> CollectionColumn<T> of(Supplier<String> name, Function<T, String> valueSupplier) {
            return new CollectionColumn<T>(name, valueSupplier);
        }

        public String getName() {
            return name.get();
        }

        public String getValue(T object) {
            return valueSupplier.apply(object);
        }
    }

    public static class CsvColumnBasedBuilder<T, U extends CollectionColumn<T>> extends CsvBuilder {
        private List<U> columns = new ArrayList<>();

        public final CsvColumnBasedBuilder<T, U> columns(List<U> columns) {
            this.columns = columns;
            return this;
        }

        @SafeVarargs
        public final CsvColumnBasedBuilder<T, U> columns(U... columns) {
            this.columns = new ArrayList<>(columns.length);
            this.columns.addAll(Arrays.asList(columns));
            return this;
        }

        public Csv build() {
            return new Csv(null, getSeparator(), getQuote(), isWithHeader(), columns, getLimit());
        }
    }

    public static class CsvCollectionBasedBuilder<T> extends CsvBuilder {
        protected FakeSequence<T> sequence;
        protected Supplier<String>[] headers;
        protected Function<T, String>[] columnValues;

        CsvCollectionBasedBuilder<T> sequence(FakeSequence<T> sequence) {
            this.sequence = sequence;
            return this;
        }

        @SafeVarargs
        public final CsvCollectionBasedBuilder<T> headers(Supplier<String>... headers) {
            this.headers = headers;
            return this;
        }

        @SafeVarargs
        public final CsvCollectionBasedBuilder<T> columns(Function<T, String>... columns) {
            this.columnValues = columns;
            return this;
        }

        @SafeVarargs
        public final CsvCollectionBasedBuilder<T> columns(Supplier<String>... columns) {
            this.columnValues = Stream.of(columns).map(c -> (Function<T, String>) t -> c.get()).collect(Collectors.toList()).toArray(new Function[0]);
            return this;
        }

        public Csv<T> build() {
            if (headers != null && columnValues != null && headers.length != columnValues.length) {
                throw new IllegalArgumentException("Length of headers should be equal to length of columns");
            }
            List<CollectionColumn<T>> cols = columnValues == null ? Collections.emptyList() : new ArrayList<>(columnValues.length);
            for (int i = 0; i < (columnValues == null ? 0 : columnValues.length); i++) {
                cols.add(CollectionColumn.of(headers == null ? null : headers[i], columnValues[i]));
            }
            return new Csv<>(sequence, getSeparator(), getQuote(), isWithHeader(), cols, getLimit());
        }
    }


    @SuppressWarnings("unchecked")
    public static abstract class CsvBuilder {
        private String separator = DEFAULT_SEPARATOR;
        private char quote = DEFAULT_QUOTE;
        private int limit = -1;
        private boolean withHeader = true;

        public <T extends CsvBuilder> T header(boolean withHeader) {
            this.withHeader = withHeader;
            return (T) this;
        }

        public <T extends CsvBuilder> T separator(String separator) {
            this.separator = separator;
            return (T) this;
        }

        public <T extends CsvBuilder> T quote(char quote) {
            this.quote = quote;
            return (T) this;
        }

        public <T extends CsvBuilder> T limit(int limit) {
            this.limit = limit;
            return (T) this;
        }

        public String getSeparator() {
            return separator;
        }

        public char getQuote() {
            return quote;
        }

        public int getLimit() {
            return limit;
        }

        public boolean isWithHeader() {
            return withHeader;
        }

        public abstract Csv build();
    }
}
