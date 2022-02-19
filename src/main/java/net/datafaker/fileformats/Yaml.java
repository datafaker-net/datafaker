package net.datafaker.fileformats;

import java.util.*;
import java.util.function.Supplier;

public class Yaml {
    private static final String INDENTATION = "  ";
    private final Map<Supplier<String>, Supplier<Object>> map;

    public Yaml(Map<Supplier<String>, Supplier<Object>> map) {
        this.map = map;
    }

    public String generate() {
        return generate(new StringBuilder(), map, "");
    }

    private String generate(final StringBuilder sb, final Map<Supplier<String>, Supplier<Object>> map, final String offset) {
        Set<String> keys = new HashSet<>();
        for (Map.Entry<Supplier<String>, Supplier<Object>> entry : map.entrySet()) {
            String key = String.valueOf(entry.getKey().get()).trim();
            if (!keys.add(key)) continue;
            Object value = entry.getValue().get();
            sb.append(offset).append(key).append(":");
            if (value instanceof Map) {
                sb.append(System.lineSeparator());
                value2String(value, sb, offset + INDENTATION);
            } else {
                value2String(value, sb, offset);
            }


            if (sb.lastIndexOf(System.lineSeparator()) != sb.length() - System.lineSeparator().length()) {
                sb.append(System.lineSeparator());
            }

        }
        return sb.toString();
    }

    private void addCollection(StringBuilder sb, Collection<Object> collection, String offset) {
        for (Object value : collection) {
            value2String(value, sb, offset + "-");
            sb.append(System.lineSeparator());
        }
    }

    private void value2String(Object value, StringBuilder sb, String offset) {
        if (value instanceof Map) {
            generate(sb, (Map<Supplier<String>, Supplier<Object>>) value, offset);
        } else if (value instanceof Collection) {
            sb.append(System.lineSeparator());
            offset += INDENTATION;
            addCollection(sb, (Collection) value, offset);
        } else if (value != null && value.getClass().isArray()) {
            sb.append(System.lineSeparator());
            offset += INDENTATION;
            addCollection(sb, Arrays.asList((Object[]) value), offset);
        } else {
            if (sb.charAt(sb.length() - 1) != ':') {
                sb.append(offset);
            }
            sb.append(" ").append(String.valueOf(value).trim());
        }
    }
}
