package net.datafaker.transformations;

import net.datafaker.providers.base.AbstractProvider;

import java.util.List;

public interface Transformer<IN extends AbstractProvider<?>, OUT> {
    OUT apply(Object input, Schema<?, ? extends OUT> schema);
    String generate(List<IN> input, final Schema<IN, ? extends OUT> schema);
    String generate(final Schema<?, ? extends OUT> schema, int limit);
}
