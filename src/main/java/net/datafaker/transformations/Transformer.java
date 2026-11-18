package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public interface Transformer<IN, OUT> {
    String LINE_SEPARATOR = System.lineSeparator();

    OUT apply(@Nullable IN input, Schema<IN, ?> schema);

    default OUT apply(@Nullable IN input, Schema<IN, ?> schema, long rowId) {
        // ignore rowId by default
        return apply(input, schema);
    }

    OUT generate(Iterable<IN> input, final Schema<IN, ?> schema);

    /**
     * Shared check for {@link #generate(Iterable, Schema)}: an infinite sequence can never be fully written out.
     *
     * @throws IllegalArgumentException if {@code input} is an infinite {@link FakeSequence}
     */
    default void requireFiniteSequence(Iterable<?> input) {
        if (input instanceof FakeSequence<?> fakeSequence && fakeSequence.isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size: " + fakeSequence);
        }
    }

    OUT generate(final Schema<IN, ?> schema, int limit);


    String getStartStream(final Schema<IN, ?> schema);

    String getEndStream();

    default String getLineSeparator() {
        return LINE_SEPARATOR;
    }

    default String getElementSeparator() {
        return "";
    }

    default Stream<OUT> generateStream(final Schema<IN, ?> schema, long limit) {
        Item item = new Item(0);
        return Stream.generate(() -> {
            StringBuilder res = new StringBuilder();
            if (item.current == 0) {
                res.append(getStartStream(schema)).append(getLineSeparator());
            }
            res.append(apply(null, schema, item.current));

            if (item.current == limit - 1) {
                res.append(getLineSeparator()).append(getEndStream());
            } else {
                if (!getElementSeparator().isEmpty()) {
                    res.append(getElementSeparator());
                }
            }
            item.current++;
            return (OUT) res.toString();
        }).limit(limit);
    }

    default void writeToOutputStream(OutputStream outputStream, final Schema<IN, ?> schema, long limit) {
        generateStream(schema, limit).forEach(item -> {
            byte[] bytes = (item + System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    class Item {
        private long current;

        public Item(long current) {
            this.current = current;
        }
    }

}
