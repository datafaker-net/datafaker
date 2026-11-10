package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * Serializes schemas as TOML tables and arrays.
 * Null field values are omitted. Null keys, null container elements, and containers
 * mixing Schema with ordinary values are rejected with an IllegalArgumentException.
 */
public class TomlTransformer<IN> implements Transformer<IN, String> {
    private static final Pattern BARE_KEY = Pattern.compile("[A-Za-z0-9_-]+");

    @Override
    public String apply(@Nullable IN input, @Nullable Schema<IN, ?> schema) {
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
        for (var in : input) {
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

    private void writeSchema(StringBuilder sb, @Nullable IN input, Schema<IN, ?> schema, String pathPrefix) {
        writeSchema(sb, input, schema, pathPrefix, pathPrefix);
    }

    private void writeSchema(StringBuilder sb, @Nullable IN input, Schema<IN, ?> schema,
                             String pathPrefix, String diagnosticPrefix) {
        List<ResolvedField> fields = resolveFields(input, schema, diagnosticPrefix);
        // Assignments must precede headers, which change the current TOML table.
        for (ResolvedField field : fields) {
            if (!field.isTable()) {
                sb.append(field.key()).append(" = ")
                    .append(value2String(field.value(), field.path())).append(LINE_SEPARATOR);
            }
        }
        for (ResolvedField field : fields) {
            if (!field.isTable()) {
                continue;
            }
            String fullPath = appendPath(pathPrefix, field.key());
            if (field.value() instanceof Schema<?, ?> nested) {
                sb.append("[").append(fullPath).append("]").append(LINE_SEPARATOR);
                writeSchema(sb, null, (Schema<IN, ?>) nested, fullPath, field.path());
            } else {
                List<?> elements = field.elements();
                for (int i = 0; i < elements.size(); i++) {
                    sb.append("[[").append(fullPath).append("]]").append(LINE_SEPARATOR);
                    writeSchema(sb, null, (Schema<IN, ?>) elements.get(i), fullPath, field.path() + "[" + i + "]");
                }
            }
        }
    }

    private List<ResolvedField> resolveFields(@Nullable IN input, Schema<IN, ?> schema, String pathPrefix) {
        List<ResolvedField> fields = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Field<IN, ?> field : schema.getFields()) {
            String key = field.getName();
            if (key == null) {
                throw new IllegalArgumentException("TOML key must not be null at "
                    + (pathPrefix.isEmpty() ? "<root>" : pathPrefix));
            }
            if (!seen.add(key)) {
                continue;
            }
            String outputKey = formatKey(key);
            String path = appendPath(pathPrefix, outputKey);
            Object value = field.transform(input);
            if (value == null) {
                continue;
            }
            fields.add(new ResolvedField(outputKey, path, value, elements(value, path)));
        }
        return fields;
    }

    private String formatKey(String key) {
        return BARE_KEY.matcher(key).matches() ? key : quoteAndEscapeString(key);
    }

    private String appendPath(String prefix, String key) {
        return prefix.isEmpty() ? key : prefix + "." + key;
    }

    private record ResolvedField(String key, String path, Object value, @Nullable List<?> elements) {
        boolean isTable() {
            return value instanceof Schema<?, ?>
                || (elements != null && !elements.isEmpty() && elements.get(0) instanceof Schema<?, ?>);
        }
    }

    private @Nullable List<?> elements(Object value, String path) {
        List<?> elements;
        if (value instanceof Collection<?> collection) {
            elements = new ArrayList<>(collection);
        } else if (value.getClass().isArray()) {
            List<Object> array = new ArrayList<>(Array.getLength(value));
            for (int i = 0; i < Array.getLength(value); i++) {
                array.add(Array.get(value, i));
            }
            elements = array;
        } else {
            return null;
        }
        for (int i = 0; i < elements.size(); i++) {
            Object element = elements.get(i);
            if (element == null) {
                throw new IllegalArgumentException("TOML value must not be null at " + path + "[" + i + "]");
            }
            if ((element instanceof Schema<?, ?>) != (elements.get(0) instanceof Schema<?, ?>)) {
                throw new IllegalArgumentException("Cannot mix Schema and ordinary values at " + path + "[" + i + "]");
            }
        }
        return elements;
    }

    private String value2String(Object value, String path) {
        List<?> elements = elements(value, path);
        if (elements != null) {
            if (elements.isEmpty()) {
                return "[]";
            }
            StringJoiner joiner = new StringJoiner(", ", "[ ", " ]");
            for (int i = 0; i < elements.size(); i++) {
                joiner.add(value2String(elements.get(i), path + "[" + i + "]"));
            }
            return joiner.toString();
        }
        if (value instanceof Schema<?, ?> schema) {
            // A Schema inside a nested array needs an inline table, not a section header.
            StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
            for (ResolvedField field : resolveFields(null, (Schema<IN, ?>) schema, path)) {
                joiner.add(field.key() + " = " + value2String(field.value(), field.path()));
            }
            return joiner.toString();
        }
        if (value instanceof Double || value instanceof Float) {
            double number = ((Number) value).doubleValue();
            if (Double.isNaN(number)) {
                return "nan";
            }
            if (Double.isInfinite(number)) {
                return number > 0 ? "inf" : "-inf";
            }
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        return quoteAndEscapeString(String.valueOf(value));
    }

    private String quoteAndEscapeString(String value) {
        StringBuilder escaped = new StringBuilder();
        escaped.append('"');

        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '"' -> escaped.append("\\\"");
                case '\\' -> escaped.append("\\\\");
                case '\b' -> escaped.append("\\b");
                case '\t' -> escaped.append("\\t");
                case '\n' -> escaped.append("\\n");
                case '\f' -> escaped.append("\\f");
                case '\r' -> escaped.append("\\r");
                default -> {
                    if (ch <= 0x1F || ch == 0x7F) {
                        escaped.append(String.format("\\u%04X", (int) ch));
                    } else {
                        escaped.append(ch);
                    }
                }
            }
        }

        escaped.append('"');
        return escaped.toString();
    }
}
