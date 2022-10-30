package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

public interface Transformer<IN, OUT> {
    String LINE_SEPARATOR = System.lineSeparator();

    OUT apply(IN input, Schema<IN, ?> schema);

    default OUT apply(IN input, Schema<IN, ?> schema, int rowId) {
        // ignore rowId by default
        return apply(input, schema);
    }

    OUT generate(FakeSequence<IN> input, final Schema<IN, ?> schema);

    OUT generate(final Schema<IN, ?> schema, int limit);
}
