package net.datafaker.fileformats;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Supplier;

public class Json {
    private static final Map<Character, String> ESCAPING_MAP = createEscapeMap();
    private final Map<Supplier<String>, Supplier<Object>> map;

    public Json(Map<Supplier<String>, Supplier<Object>> map) {
        this.map = map;
    }

    public String generate() {
        return generate(map);
    }

    private String generate(Collection<Object> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = 0;
        for (Object value: collection) {
            if (i > 0) {
                sb.append(", ");
            }
            i++;
            value2String(value, sb);
        }
        sb.append("]");
        return sb.toString();
    }

    private void value2String(Object value, StringBuilder sb) {
        if (value == null) {
            sb.append("null");
        } else if (value instanceof Integer
                || value instanceof Long
                || value instanceof Short
                || value instanceof BigInteger
                || value instanceof Boolean
                || (value instanceof BigDecimal && ((BigDecimal) value).remainder(BigDecimal.ONE).doubleValue() == 0)) {
            sb.append(value);
        } else if (value instanceof Map) {
            sb.append(generate((Map<Supplier<String>, Supplier<Object>>) value));
        } else if (value instanceof Collection) {
            sb.append(generate((Collection) value));
        } else if (value.getClass().isArray()) {
            sb.append(generate(Arrays.asList((Object[]) value)));
        } else {
            sb.append("\"");
            for (char c: String.valueOf(value).toCharArray()) {
                sb.append(ESCAPING_MAP.getOrDefault(c, c + ""));
            }
            sb.append("\"");
        }
    }

    private String generate(Map<Supplier<String>, Supplier<Object>> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Set<String> keys = new HashSet<>();
        for (Map.Entry<Supplier<String>, Supplier<Object>> entry: map.entrySet()) {
            String key = entry.getKey().get();
            if (!keys.add(key)) continue;
            if (keys.size() > 1) {
                sb.append(", ");
            }
            sb.append("\"");
            for (char c: key.toCharArray()) {
                sb.append(ESCAPING_MAP.getOrDefault(c, c + ""));
            }
            sb.append("\": ");
            Object value = entry.getValue().get();
            value2String(value, sb);
        }
        sb.append("}");
        return sb.toString();
    }

    private static Map<Character, String> createEscapeMap() {
        final Map<Character, String> map = new HashMap<>();
        map.put('\\', "\\\\");
        map.put('\"', "\\\"");
        map.put('\b', "\\b");
        map.put('\f', "\\f");
        map.put('\n', "\\n");
        map.put('\r', "\\r");
        map.put('\t', "\\t");
        map.put('/', "\\/");
        map.put('\u0000', "\\u0000");
        map.put('\u0001', "\\u0001");
        map.put('\u0002', "\\u0002");
        map.put('\u0003', "\\u0003");
        map.put('\u0004', "\\u0004");
        map.put('\u0005', "\\u0005");
        map.put('\u0006', "\\u0006");
        map.put('\u0007', "\\u0007");
        // map.put('\u0008', "\\u0008");
        // covered by map.put('\b', "\\b");
        // map.put('\u0009', "\\u0009");
        // covered by map.put('\t', "\\t");
        // map.put((char) 10, "\\u000A");
        // covered by map.put('\n', "\\n");
        map.put('\u000B', "\\u000B");
        // map.put('\u000C', "\\u000C");
        // covered by map.put('\f', "\\f");
        // map.put((char) 13, "\\u000D");
        // covered by map.put('\r', "\\r");
        map.put('\u000E', "\\u000E");
        map.put('\u000F', "\\u000F");
        map.put('\u0010', "\\u0010");
        map.put('\u0011', "\\u0011");
        map.put('\u0012', "\\u0012");
        map.put('\u0013', "\\u0013");
        map.put('\u0014', "\\u0014");
        map.put('\u0015', "\\u0015");
        map.put('\u0016', "\\u0016");
        map.put('\u0017', "\\u0017");
        map.put('\u0018', "\\u0018");
        map.put('\u0019', "\\u0019");
        map.put('\u001A', "\\u001A");
        map.put('\u001B', "\\u001B");
        map.put('\u001C', "\\u001C");
        map.put('\u001D', "\\u001D");
        map.put('\u001E', "\\u001E");
        map.put('\u001F', "\\u001F");
        return Collections.unmodifiableMap(map);
    }
}
