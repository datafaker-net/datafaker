package net.datafaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Csv {
    private final String separator;
    private final String lineSeparator = System.lineSeparator();
    private final char quote;
    private final List<Column> columns;
    private final int limit;
    private final boolean withHeader;
    private Csv(List<Column> columns, String separator, char quote, boolean withHeader, int limit) {
        this.separator = separator;
        this.columns = columns;
        this.limit = limit;
        this.quote = quote;
        this.withHeader = withHeader;
    }

    public String get() {
        StringBuilder sb = new StringBuilder();
        if (withHeader) {
            addLine(sb, integer -> columns.get(integer).getName());
        }

        for (int line = 0; line < limit; line++) {
            addLine(sb, integer -> columns.get(integer).getValue());
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
            result.append(i == columns.size() - 1 ? lineSeparator : separator);
        }
    }

    public static class Column {
        private final String name;
        private final Supplier<String> valueSupplier;

        public Column(String name, Supplier<String> valueSupplier) {
            this.name = name;
            this.valueSupplier = valueSupplier;
        }

        public static Column of(String name, Supplier<String> valueSupplier) {
            return new Column(name, valueSupplier);
        }

        public String getName() {
            return name;
        }

        public Supplier<String> getValueSupplier() {
            return valueSupplier;
        }

        public String getValue() {
            return valueSupplier.get();
        }
    }

    public static class CsvBuilder {
        private String separator = ",";
        private char quote = '"';
        private final List<Column> columns = new ArrayList<>();
        private boolean withHeader = true;
        private int limit = 10;

        public CsvBuilder separator(String separator) {
            this.separator = separator;
            return this;
        }

        public CsvBuilder quote(char quote) {
            this.quote = quote;
            return this;
        }

        public final CsvBuilder columns(Column... columns) {
            this.columns.addAll(Arrays.asList(columns));
            return this;
        }

        public CsvBuilder columns(Collection<Column> columns) {
            this.columns.addAll(columns);
            return this;
        }

        public CsvBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public CsvBuilder header(boolean withHeader) {
            this.withHeader = withHeader;
            return this;
        }

        public Csv build() {
            return new Csv(columns, separator, quote, withHeader, limit);
        }
    }
}
