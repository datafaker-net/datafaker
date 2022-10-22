package net.datafaker.transformations;

import java.util.List;

public interface Transformer<IN, OUT> {
    OUT apply(IN input, Schema<IN, ?> schema);
    OUT generate(List<IN> input, final Schema<IN, ?> schema);
    OUT generate(final Schema<IN, ?> schema, int limit);
}
