package net.datafaker.transformations;

import net.datafaker.sequence.FakeSequence;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class JsonTransformer<IN> implements Transformer<IN, Object> {

    private static final Map<Character, String> ESCAPING_MAP = createEscapeMap();
    private static final char[] WRAPPERS = "[]".toCharArray();
    private final boolean commaBetweenObjects;
    private final boolean prettyPrint;


    private JsonTransformer(boolean commaBetweenObjects, boolean prettyPrint) {
        this.commaBetweenObjects = commaBetweenObjects;
        this.prettyPrint = prettyPrint;
    }

    public static <IN> JsonTransformer.JsonTransformerBuilder<IN> builder() {
        return new JsonTransformer.JsonTransformerBuilder<>();
    }

    @Override
    public String apply(IN input, Schema<IN, ?> schema) {
        Field<?, ?>[] fields = schema.getFields();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < fields.length; i++) {
            value2String((fields[i].getName()), sb);
            sb.append(": ");
            if (fields[i] instanceof CompositeField) {
                sb.append(apply(input, (CompositeField) fields[i], i));
            } else {
                applyValue(input, sb, ((SimpleField) fields[i]).transform(input));
            }
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String generate(Iterable<IN> input, Schema<IN, ?> schema) {
        if (input instanceof FakeSequence && ((FakeSequence) input).isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size");
        }

        StringBuilder sb = new StringBuilder();
        Iterator<IN> iterator = input.iterator();
        int size = 0;
        while (iterator.hasNext()) {
            size++;
            sb.append(apply(iterator.next(), schema)).append(commaBetweenObjects && iterator.hasNext() ? "," : "");
        }

        String result = size > 1 ? "" + WRAPPERS[0] + sb + WRAPPERS[1] : sb.toString();

        return prettyPrint ? result.startsWith("{")
            ? new JSONObject(result).toString(2) : new JSONArray(result).toString(2) : result;
    }

    @Override
    public String generate(Schema<IN, ?> schema, int limit) {
        StringBuilder sb = new StringBuilder();

        if (limit == 1) {
            sb.append(apply(null, schema, 0));
            return sb.toString();
        } else {
            for (int i = 0; i < limit; i++) {
                sb.append(apply(null, schema, i));
                if (commaBetweenObjects && i < limit - 1) sb.append(",");
            }
        }

        String result = "" + WRAPPERS[0] + sb + WRAPPERS[1];

        return prettyPrint ? result.startsWith("{")
            ? new JSONObject(result).toString(2) : new JSONArray(result).toString(2) : result;
    }

    private void applyValue(IN input, StringBuilder sb, Object value) {
        if (value instanceof Collection<?>) {
            sb.append(generate(input, (Collection) value));
        } else if (value != null && value.getClass().isArray()) {
            sb.append(generate(input, Arrays.asList((Object[]) value)));
        } else {
            value2String(value, sb);
        }
    }

    private String generate(IN input, Collection<Object> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = 0;
        for (Object value : collection) {
            if (i > 0) {
                sb.append(", ");
            }
            i++;
            if (value instanceof CompositeField<?, ?>) {
                sb.append(apply(input, ((CompositeField) value)));
            } else {
                applyValue(input, sb, value);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static void value2String(Object value, StringBuilder sb) {
        if (value == null) {
            sb.append("null");
        } else if (value instanceof Integer
            || value instanceof Long
            || value instanceof Short
            || value instanceof BigInteger
            || value instanceof Boolean
            || (value instanceof Double
            && BigDecimal.valueOf((Double) value).remainder(BigDecimal.ONE).doubleValue() == 0)
            || (value instanceof BigDecimal
            && ((BigDecimal) value).remainder(BigDecimal.ONE).doubleValue() == 0)) {
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
                final String escapedValue = ESCAPING_MAP.get(c);
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

    private static Map<Character, String> createEscapeMap() {
        return Map.ofEntries(Map.entry('\\', "\\\\"),
            Map.entry('\"', "\\\""),
            Map.entry('\b', "\\b"),
            Map.entry('\f', "\\f"),
            Map.entry('\n', "\\n"),
            Map.entry('\r', "\\r"),
            Map.entry('\t', "\\t"),
            Map.entry('/', "\\/"),
            Map.entry('\u0000', "\\u0000"),
            Map.entry('\u0001', "\\u0001"),
            Map.entry('\u0002', "\\u0002"),
            Map.entry('\u0003', "\\u0003"),
            Map.entry('\u0004', "\\u0004"),
            Map.entry('\u0005', "\\u0005"),
            Map.entry('\u0006', "\\u0006"),
            Map.entry('\u0007', "\\u0007"),
            // map.put('\u0008', "\\u0008");
            // covered by map.put('\b', "\\b");
            // map.put('\u0009', "\\u0009");
            // covered by map.put('\t', "\\t");
            // map.put((char) 10, "\\u000A");
            // covered by map.put('\n', "\\n");
            Map.entry('\u000B', "\\u000B"),
            // map.put('\u000C', "\\u000C");
            // covered by map.put('\f', "\\f");
            // map.put((char) 13, "\\u000D");
            // covered by map.put('\r', "\\r");
            Map.entry('\u000E', "\\u000E"),
            Map.entry('\u000F', "\\u000F"),
            Map.entry('\u0010', "\\u0010"),
            Map.entry('\u0011', "\\u0011"),
            Map.entry('\u0012', "\\u0012"),
            Map.entry('\u0013', "\\u0013"),
            Map.entry('\u0014', "\\u0014"),
            Map.entry('\u0015', "\\u0015"),
            Map.entry('\u0016', "\\u0016"),
            Map.entry('\u0017', "\\u0017"),
            Map.entry('\u0018', "\\u0018"),
            Map.entry('\u0019', "\\u0019"),
            Map.entry('\u001A', "\\u001A"),
            Map.entry('\u001B', "\\u001B"),
            Map.entry('\u001C', "\\u001C"),
            Map.entry('\u001D', "\\u001D"),
            Map.entry('\u001E', "\\u001E"),
            Map.entry('\u001F', "\\u001F"));
    }

    public static class JsonTransformerBuilder<IN> {
        private JsonTransformerBuilder() {
        }

        private boolean commaBetweenObjects = true;

        private boolean prettyPrint = true;

        public JsonTransformerBuilder<IN> withCommaBetweenObjects(boolean commaBetweenObjects) {
            this.commaBetweenObjects = commaBetweenObjects;

            // cannot pretty print if there's no comma between the objects
            if (!commaBetweenObjects) this.prettyPrint = false;

            return this;
        }

        public JsonTransformerBuilder<IN> prettyPrint(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
            return this;
        }

        public JsonTransformer<IN> build() {
            return new JsonTransformer<>(commaBetweenObjects, prettyPrint);
        }
    }
}
