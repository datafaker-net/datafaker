package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

public class JsonTransformer<IN> implements Transformer<IN, CharSequence> {

    private static final String[] ESCAPES = createEscapes();
    private final boolean commaBetweenObjects;

    private JsonTransformer(boolean commaBetweenObjects) {
        this.commaBetweenObjects = commaBetweenObjects;
    }

    public static <IN> JsonTransformer.JsonTransformerBuilder<IN> builder() {
        return new JsonTransformer.JsonTransformerBuilder<>();
    }

    @Override
    public String apply(@Nullable IN input, Schema<IN, ?> schema) {
        StringBuilder sb = new StringBuilder();
        appendObject(sb, input, schema);
        return sb.toString();
    }

    private void appendObject(StringBuilder sb, @Nullable IN input, Schema<IN, ?> schema) {
        Field<?, ?>[] fields = schema.getFields();
        sb.append('{');
        for (int i = 0; i < fields.length; i++) {
            value2String(fields[i].getName(), sb);
            sb.append(": ");
            if (fields[i] instanceof CompositeField compositeField) {
                appendObject(sb, input, compositeField);
            } else if (fields[i] instanceof SimpleField simpleField) {
                applyValue(input, sb, simpleField.transform(input));
            } else {
                applyValue(input, sb, ((SimpleField) fields[i]).transform(input));
            }
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append('}');
    }

    @Override
    public String generate(Iterable<IN> input, Schema<IN, ?> schema) {
        if (input instanceof FakeSequence<?> fakeSequence && fakeSequence.isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size: " + fakeSequence);
        }

        var iterator = input.iterator();
        if (!iterator.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder().append('[').append(LINE_SEPARATOR);
        appendObject(sb, iterator.next(), schema);
        while (iterator.hasNext()) {
            if (commaBetweenObjects) {
                sb.append(',');
            }
            sb.append(LINE_SEPARATOR);
            appendObject(sb, iterator.next(), schema);
        }
        return sb.append(LINE_SEPARATOR).append(']').toString();
    }

    @Override
    public String generate(Schema<IN, ?> schema, int limit) {
        StringBuilder sb = new StringBuilder();
        if (limit > 1) {
            sb.append('[').append(LINE_SEPARATOR);
        }
        for (int i = 0; i < limit; i++) {
            appendObject(sb, null, schema);
            if (commaBetweenObjects && i < limit - 1) {
                sb.append(",").append(LINE_SEPARATOR);
            }
        }

        if (limit > 1) {
            sb.append(LINE_SEPARATOR).append(']');
        }
        return sb.toString();
    }

    @Override
    public String getStartStream(Schema<IN, ?> schema) {
        return "[";
    }

    @Override
    public String getEndStream() {
        return "]";
    }

    @Override
    public String getElementSeparator() {
        return ",";
    }

    private void applyValue(@Nullable IN input, StringBuilder sb, @Nullable Object value) {
        if (value instanceof Collection<?> collection) {
            appendArray(sb, input, collection);
        } else if (value != null && value.getClass().isArray()) {
            appendArray(sb, input, Arrays.asList((Object[]) value));
        } else {
            value2String(value, sb);
        }
    }

    private void appendArray(StringBuilder sb, @Nullable IN input, Collection<?> collection) {
        sb.append('[');
        int i = 0;
        for (Object value : collection) {
            if (i > 0) {
                sb.append(", ");
            }
            i++;
            if (value instanceof CompositeField compositeField) {
                appendObject(sb, input, compositeField);
            } else {
                applyValue(input, sb, value);
            }
        }
        sb.append(']');
    }

    private static void value2String(@Nullable Object value, StringBuilder sb) {
        if (value == null) {
            sb.append("null");
        } else if (value instanceof Integer
                || value instanceof Long
                || value instanceof Short
                || value instanceof Byte
                || value instanceof BigInteger
                || value instanceof Boolean
                || value instanceof Double doubleValue && Double.isFinite(doubleValue)
                || (value instanceof Float floatValue && Float.isFinite(floatValue))
                || value instanceof BigDecimal) {
            sb.append(value);
        } else {
            String val = String.valueOf(value);
            boolean toWrap = !val.startsWith("#{json");
            if (toWrap) {
                sb.append("\"");
            }
            int start = 0;
            final int length = val.length();
            for (int i = 0; i < length; i++) {
                final char c = val.charAt(i);
                final String escapedValue = c < ESCAPES.length ? ESCAPES[c] : null;
                if (escapedValue != null) {
                    sb.append(val, start, i).append(escapedValue);
                    start = i + 1;
                }
            }
            sb.append(val, start, length);
            if (toWrap) {
                sb.append("\"");
            }
        }
    }

    private static String[] createEscapes() {
        String[] escapes = new String[128];
        String hex = "0123456789ABCDEF";
        for (int i = 0; i < 32; i++) {
            escapes[i] = "\\u00" + hex.charAt(i >>> 4) + hex.charAt(i & 15);
        }
        escapes['\\'] = "\\\\";
        escapes['"'] = "\\\"";
        escapes['\b'] = "\\b";
        escapes['\f'] = "\\f";
        escapes['\n'] = "\\n";
        escapes['\r'] = "\\r";
        escapes['\t'] = "\\t";
        escapes['/'] = "\\/";
        return escapes;
    }

    public static class JsonTransformerBuilder<IN> {

        private JsonTransformerBuilder() {
        }

        private boolean commaBetweenObjects = true;

        public JsonTransformerBuilder<IN> withCommaBetweenObjects(boolean commaBetweenObjects) {
            this.commaBetweenObjects = commaBetweenObjects;
            return this;
        }

        public JsonTransformer<IN> build() {
            return new JsonTransformer<>(commaBetweenObjects);
        }
    }
}
