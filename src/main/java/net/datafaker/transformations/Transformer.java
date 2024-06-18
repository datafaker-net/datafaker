package net.datafaker.transformations;

import java.util.stream.Stream;

public interface Transformer<IN, OUT> {
    String LINE_SEPARATOR = System.lineSeparator();

    OUT apply(IN input, Schema<IN, ?> schema);

    default OUT apply(IN input, Schema<IN, ?> schema, long rowId) {
        // ignore rowId by default
        return apply(input, schema);
    }

    OUT generate(Iterable<IN> input, final Schema<IN, ?> schema);

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

    class Item {
        private long current;

        public Item(long current) {
            this.current = current;
        }
    }

}
