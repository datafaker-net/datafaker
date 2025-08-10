package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;

import java.util.*;
import java.util.stream.Collectors;

public class TomlTransformer<IN> implements Transformer<IN, String> {
    @Override
    public String apply(IN input, Schema<IN, ?> schema) {
        if (schema == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        writeSchema(sb, input, schema, "");
        int len = sb.length();
        int sepLen = LINE_SEPARATOR.length();
        if (len >= sepLen && sb.lastIndexOf(LINE_SEPARATOR, len - sepLen) >= 0) {
            sb.setLength(len - sepLen);
        }
        return sb.toString();
    }

    @Override
    public String generate(Iterable<IN> input, Schema<IN, ?> schema) {
        if (input instanceof FakeSequence<IN> fs && fs.isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size: " + fs);
        }
        StringJoiner joiner = new StringJoiner(LINE_SEPARATOR);
        for (IN in : input) {
            joiner.add(apply(in, schema));
        }
        return joiner.toString();
    }

    @Override
    public String generate(Schema<IN, ?> schema, int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            sb.append(apply(null, schema));
            if (i < limit - 1) {
                sb.append(LINE_SEPARATOR);
            }
        }
        return sb.toString();
    }

    @Override
    public String getStartStream(Schema<IN, ?> schema) {
        return "";
    }

    @Override
    public String getEndStream() {
        return "";
    }

    private void writeSchema(StringBuilder sb, IN input, Schema<IN, ?> schema, String pathPrefix) {
        Set<String> seen = new HashSet<>();
        for (Field<IN, ?> f : schema.fields()) {
            String key = f.getName();
            if (!seen.add(key)) {
                continue;
            }
            Object val = f.transform(input);
            writeValue(sb, key, pathPrefix, val);
        }
    }

    private void writeValue(StringBuilder sb, String key, String pathPrefix, Object val) {
        String fullPath = pathPrefix.isEmpty() ? key : pathPrefix + "." + key;

        if (val instanceof Schema<?, ?> nested) {
            sb.append("[").append(fullPath).append("]").append(LINE_SEPARATOR);
            writeSchema(sb, null, (Schema<IN, ?>) nested, fullPath);
            return;
        }

        if (val instanceof Collection<?> col) {
            if (col.isEmpty()) {
                sb.append(key).append(" = []").append(LINE_SEPARATOR);
                return;
            }
            Object first = col.iterator().next();
            if (first instanceof Schema<?, ?>) {
                for (Object item : col) {
                    sb.append("[[").append(fullPath).append("]]").append(LINE_SEPARATOR);
                    writeSchema(sb, null, (Schema<IN, ?>) item, fullPath);
                }
            } else {
                String array = col.stream().map(this::value2String).collect(Collectors.joining(", "));
                sb.append(key).append(" = [ ").append(array).append(" ]").append(LINE_SEPARATOR);
            }
            return;
        }

        if (val != null && val.getClass().isArray()) {
            handleArray(sb, key, val);
            return;
        }

        // Scalar value
        sb.append(key).append(" = ").append(value2String(val)).append(LINE_SEPARATOR);
    }

    private void handleArray(StringBuilder sb, String key, Object array) {
        int length = java.lang.reflect.Array.getLength(array);
        if (length == 0) {
            sb.append(key).append(" = []").append(LINE_SEPARATOR);
            return;
        }
        List<Object> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(java.lang.reflect.Array.get(array, i));
        }
        String joined = list.stream().map(this::value2String).collect(Collectors.joining(", "));
        sb.append(key).append(" = [ ").append(joined).append(" ]").append(LINE_SEPARATOR);
    }

    private String value2String(Object val) {
        if (val == null) {
            return "null";
        }
        if (val instanceof Number || val instanceof Boolean) {
            return val.toString();
        }
        String s = String.valueOf(val).replace("\\", "\\\\").replace("\"", "\\\"");
        return "\"" + s + "\"";
    }
}
