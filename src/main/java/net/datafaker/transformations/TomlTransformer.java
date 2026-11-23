package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Serializes schemas as TOML tables and arrays. Null fields, map values and container
 * elements are omitted; null keys, mixed Schema/value containers, cyclic structures
 * and integers outside the signed 64-bit range are rejected.
 * Multiple records are written under {@code [[records]]}; a single record stays at the root.
 */
public class TomlTransformer<IN> implements Transformer<IN, String> {
    private static final Pattern BARE_KEY = Pattern.compile("[A-Za-z0-9_-]+");
    private static final Pattern INTEGER = Pattern.compile("[+-]?[0-9]+");
    private static final int MAX_DEPTH = 128;
    private static final int MAX_STREAM_ELEMENTS = 100_000;

    @Override
    public String apply(@Nullable IN input, @Nullable Schema<IN, ?> schema) {
        return schema == null ? "" : renderRecord(input, schema, false);
    }

    private String renderRecord(@Nullable IN input, Schema<IN, ?> schema, boolean wrapped) {
        Set<Object> active = Collections.newSetFromMap(new IdentityHashMap<>());
        Table table = resolveSchema(input, schema, wrapped ? "records" : "", active);
        StringBuilder sb = new StringBuilder();
        if (wrapped) {
            sb.append("[[records]]").append(LINE_SEPARATOR);
        }
        writeSchema(sb, table, wrapped ? "records" : "");
        if (sb.length() > 0) {
            sb.setLength(sb.length() - LINE_SEPARATOR.length());
        }
        return sb.toString();
    }

    @Override
    public String generate(Iterable<IN> input, Schema<IN, ?> schema) {
        if (input instanceof FakeSequence<?> sequence && sequence.isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size: " + sequence);
        }
        Iterator<IN> iterator = input.iterator();
        if (!iterator.hasNext()) {
            return "";
        }
        IN first = iterator.next();
        boolean wrapped = iterator.hasNext();
        StringJoiner result = new StringJoiner(LINE_SEPARATOR);
        result.add(renderRecord(first, schema, wrapped));
        while (iterator.hasNext()) {
            result.add(renderRecord(iterator.next(), schema, true));
        }
        return result.toString();
    }

    @Override
    public String generate(Schema<IN, ?> schema, int limit) {
        StringJoiner result = new StringJoiner(LINE_SEPARATOR);
        for (int i = 0; i < limit; i++) {
            result.add(renderRecord(null, schema, limit > 1));
        }
        return result.toString();
    }

    @Override
    public Stream<String> generateStream(Schema<IN, ?> schema, long limit) {
        // Preserve the interface's lifecycle and row-id hooks, including for subclasses.
        Schema<IN, ?> streamSchema = limit > 1
            ? Schema.of(Field.field("records", () -> List.of(schema))) : schema;
        return Transformer.super.generateStream(streamSchema, limit);
    }

    @Override
    public String getStartStream(Schema<IN, ?> schema) {
        return "";
    }

    @Override
    public String getEndStream() {
        return "";
    }

    private Table resolveSchema(@Nullable IN input, Schema<IN, ?> schema, String path, Set<Object> active) {
        enter(schema, path, active);
        try {
            List<ResolvedField> fields = new ArrayList<>();
            Set<String> seen = new HashSet<>();
            for (Field<IN, ?> field : schema.getFields()) {
                String key = checkedKey(field.getName(), path);
                if (seen.contains(key)) {
                    continue;
                }
                Object value = field instanceof CompositeField<?, ?> ? field : field.transform(input);
                if (value != null) {
                    seen.add(key);
                    String outputKey = formatKey(key);
                    fields.add(new ResolvedField(outputKey,
                        resolveValue(input, value, appendPath(path, outputKey), active)));
                }
            }
            return new Table(fields, false);
        } finally {
            active.remove(schema);
        }
    }

    private Object resolveValue(@Nullable IN input, Object value, String path, Set<Object> active) {
        if (value instanceof Schema<?, ?> schema) {
            return resolveSchema(input, (Schema<IN, ?>) schema, path, active);
        }
        if (value instanceof Map<?, ?> map) {
            enter(value, path, active);
            try {
                List<ResolvedField> fields = new ArrayList<>();
                Set<String> seen = new HashSet<>();
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    String key = formatKey(checkedKey(entry.getKey(), path));
                    Object element = entry.getValue();
                    if (element != null) {
                        if (!seen.add(key)) {
                            throw new IllegalArgumentException("Duplicate TOML map key at " + appendPath(path, key));
                        }
                        fields.add(new ResolvedField(key, resolveValue(input, element, appendPath(path, key), active)));
                    }
                }
                return new Table(fields, true);
            } finally {
                active.remove(value);
            }
        }
        if (value instanceof Collection<?> || value instanceof Stream<?> || value.getClass().isArray()) {
            enter(value, path, active);
            try {
                if (value instanceof Stream<?> stream) {
                    try (stream) {
                        return resolveElements(input, stream.iterator(), path, active, MAX_STREAM_ELEMENTS);
                    }
                }
                if (value instanceof Collection<?> collection) {
                    return resolveElements(input, collection.iterator(), path, active, Integer.MAX_VALUE);
                }
                int length = Array.getLength(value);
                List<Object> elements = new ArrayList<>(length);
                for (int i = 0; i < length; i++) {
                    elements.add(Array.get(value, i));
                }
                return resolveElements(input, elements.iterator(), path, active, Integer.MAX_VALUE);
            } finally {
                active.remove(value);
            }
        }
        if (value instanceof Number) {
            String text = value.toString();
            if (INTEGER.matcher(text).matches() && new BigInteger(text).bitLength() > 63) {
                throw new IllegalArgumentException("TOML integer outside signed 64-bit range at " + path + ": " + text);
            }
        }
        return value;
    }

    private Sequence resolveElements(@Nullable IN input, Iterator<?> iterator, String path, Set<Object> active, int limit) {
        List<Object> values = new ArrayList<>();
        Boolean schemas = null;
        int index = 0;
        while (iterator.hasNext()) {
            if (index >= limit) {
                throw new IllegalArgumentException("TOML stream exceeds " + limit + " elements at " + path);
            }
            Object element = iterator.next();
            String elementPath = path + "[" + index++ + "]";
            if (element == null) {
                continue;
            }
            boolean schema = element instanceof Schema<?, ?>;
            if (schemas != null && schemas != schema) {
                throw new IllegalArgumentException("Cannot mix Schema and ordinary values at " + elementPath);
            }
            schemas = schema;
            values.add(resolveValue(input, element, elementPath, active));
        }
        return new Sequence(values, Boolean.TRUE.equals(schemas));
    }

    private void enter(Object value, String path, Set<Object> active) {
        if (!active.add(value)) {
            throw new IllegalArgumentException("Cyclic TOML value at " + (path.isEmpty() ? "<root>" : path));
        }
        if (active.size() > MAX_DEPTH) {
            throw new IllegalArgumentException("TOML nesting exceeds " + MAX_DEPTH + " levels at " + path);
        }
    }

    private String checkedKey(@Nullable Object key, String path) {
        if (key == null) {
            throw new IllegalArgumentException("TOML key must not be null at "
                + (path.isEmpty() ? "<root>" : path));
        }
        return String.valueOf(key);
    }

    private String formatKey(String key) {
        return BARE_KEY.matcher(key).matches() ? key : quoteAndEscapeString(key);
    }

    private String appendPath(String prefix, String key) {
        return prefix.isEmpty() ? key : prefix + "." + key;
    }

    private record ResolvedField(String key, Object value) {
        boolean isTable() {
            return value instanceof Table table && !table.inline()
                || value instanceof Sequence sequence && sequence.schemas();
        }
    }

    private record Table(List<ResolvedField> fields, boolean inline) { }

    private record Sequence(List<Object> values, boolean schemas) { }

    private void writeSchema(StringBuilder sb, Table table, String path) {
        for (ResolvedField field : table.fields()) {
            if (!field.isTable()) {
                sb.append(field.key()).append(" = ").append(value2String(field.value())).append(LINE_SEPARATOR);
            }
        }
        for (ResolvedField field : table.fields()) {
            String fullPath = appendPath(path, field.key());
            if (field.value() instanceof Table nested && !nested.inline()) {
                sb.append("[").append(fullPath).append("]").append(LINE_SEPARATOR);
                writeSchema(sb, nested, fullPath);
            } else if (field.value() instanceof Sequence sequence && sequence.schemas()) {
                for (Object element : sequence.values()) {
                    sb.append("[[").append(fullPath).append("]]").append(LINE_SEPARATOR);
                    writeSchema(sb, (Table) element, fullPath);
                }
            }
        }
    }

    private String inlineTable(List<ResolvedField> fields) {
        StringJoiner result = new StringJoiner(", ", "{ ", " }");
        for (ResolvedField field : fields) {
            result.add(field.key() + " = " + value2String(field.value()));
        }
        return result.toString();
    }

    private String value2String(Object value) {
        if (value instanceof Sequence sequence) {
            if (sequence.values().isEmpty()) {
                return "[]";
            }
            StringJoiner result = new StringJoiner(", ", "[ ", " ]");
            for (Object element : sequence.values()) {
                result.add(value2String(element));
            }
            return result.toString();
        }
        if (value instanceof Table table) {
            return inlineTable(table.fields());
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
