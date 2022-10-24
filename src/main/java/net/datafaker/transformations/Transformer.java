package net.datafaker.transformations;

import java.util.List;

public interface Transformer<IN, OUT> {
    String LINE_SEPARATOR = System.lineSeparator();

    OUT apply(IN input, Schema<IN, ?> schema);

    default OUT apply(IN input, Schema<IN, ?> schema, int rowId) {
        // ignore rowId by default
        return apply(input, schema);
    }

    OUT generate(List<IN> input, final Schema<IN, ?> schema);

    OUT generate(final Schema<IN, ?> schema, int limit);
}
