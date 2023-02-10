package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

import java.util.StringJoiner;

public class CsvTransformer<IN> implements Transformer<IN, CharSequence> {
    public static final String DEFAULT_SEPARATOR = ";";
    public static final char DEFAULT_QUOTE = '"';

    private final String separator;
    private final char quote;
    private final boolean withHeader;

    private CsvTransformer(String separator, char quote, boolean withHeader) {
        this.separator = separator;
        this.quote = quote;
        this.withHeader = withHeader;
    }

    public static <IN> CsvTransformerBuilder<IN> builder() {
        return new CsvTransformerBuilder<>();
    }

    @Override
    public CharSequence apply(IN input, Schema<IN, ?> schema) {
        Field<IN, ?>[] fields = schema.getFields();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            //noinspection unchecked
            SimpleField<Object, ?> f = (SimpleField<Object, ?>) fields[i];
            addLine(sb, f.transform(input));
            if (i < fields.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    @Override
    public String generate(Iterable<IN> input, Schema<IN, ?> schema) {
        if (input instanceof FakeSequence && ((FakeSequence) input).isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size");
        }

        StringBuilder sb = new StringBuilder();
        generateHeader(schema, sb);

        StringJoiner data = new StringJoiner(LINE_SEPARATOR);
        for (IN in : input) {
            data.add(apply(in, schema));
        }

        sb.append(data);
        return sb.toString();
    }

    private void addLine(StringBuilder sb, Object transform) {
        if (transform instanceof CharSequence) {
            addCharSequence(sb, (CharSequence) transform);
        } else {
            sb.append(transform);
        }
    }

    private void addCharSequence(StringBuilder sb, CharSequence charSequence) {
        sb.append(quote);
        for (int j = 0; j < charSequence.length(); j++) {
            final char c = charSequence.charAt(j);
            if (c == quote) {
                sb.append(quote);
            }
            sb.append(c);
        }
        sb.append(quote);
    }

    private void generateHeader(Schema<?, ?> schema, StringBuilder sb) {
        if (withHeader) {
            for (int i = 0; i < schema.getFields().length; i++) {
                addLine(sb, schema.getFields()[i].getName());
                if (i < schema.getFields().length - 1) {
                    sb.append(separator);
                }
            }
            sb.append(LINE_SEPARATOR);
        }
    }

    @Override
    public String generate(Schema<IN, ?> schema, int limit) {
        StringBuilder sb = new StringBuilder();
        generateHeader(schema, sb);
        for (int i = 0; i < limit; i++) {
            sb.append(apply(null, schema, i));
            if (i < limit - 1) {
                sb.append(LINE_SEPARATOR);
            }
        }
        return sb.toString();
    }

    public static class CsvTransformerBuilder<IN> {
        private String separator = DEFAULT_SEPARATOR;
        private char quote = DEFAULT_QUOTE;
        private boolean withHeader = true;

        public CsvTransformerBuilder<IN> quote(char quote) {
            this.quote = quote;
            return this;
        }

        public CsvTransformerBuilder<IN> separator(String separator) {
            this.separator = separator;
            return this;
        }

        public CsvTransformerBuilder<IN> header(boolean header) {
            this.withHeader = header;
            return this;
        }

        public CsvTransformer<IN> build() {
            return new CsvTransformer<>(separator, quote, withHeader);
        }
    }
}
