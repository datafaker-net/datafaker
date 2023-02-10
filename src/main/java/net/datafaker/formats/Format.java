package net.datafaker.formats;

import net.datafaker.sequence.FakeSequence;


@Deprecated // Use Transformer
public class Format {

    public static Json.JsonBuilder toJson() {
        return new Json.JsonBuilder();
    }

    public static <T> Json.JsonFromCollectionBuilder<T> toJson(FakeSequence<T> sequence) {
        return new Json.JsonFromCollectionBuilder<>(sequence);
    }

}
