package net.datafaker.transformations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import net.datafaker.sequence.FakeSequence;

public class YamlTransformer<IN> implements Transformer<IN, CharSequence> {

    private static final String INDENTATION = "  ";

    @Override
    public CharSequence apply(IN input, Schema<IN, ?> schema) {
        Field<IN, ?>[] fields = schema.getFields();

        if (fields.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        return apply(sb, input, fields, "");
    }

    @Override
    public String generate(Iterable<IN> input, Schema<IN, ?> schema) {
        if (input instanceof FakeSequence<?> fakeSequence && fakeSequence.isInfinite()) {
            throw new IllegalArgumentException("The sequence should be finite of size: " + fakeSequence);
        }

        StringJoiner data = new StringJoiner(LINE_SEPARATOR);
        for (IN in : input) {
            data.add(apply(in, schema));
        }

        return data.toString();
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

    private String apply(final StringBuilder sb, final IN input, final Field<IN, ?>[] fields, final String offset) {
        Set<String> keys = new HashSet<>();
        for (Field<IN, ?> field : fields) {
            String key = field.getName().trim();
            if (!keys.add(key)) continue;
            Object value = field.transform(input);
            sb.append(offset).append(key).append(":");
            if (value instanceof Schema) {
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

    private void addCollection(StringBuilder sb, Collection<?> collection, String offset) {
        for (Object value : collection) {
            value2String(value, sb, offset + "-");
            sb.append(System.lineSeparator());
        }
    }
    private void value2String(Object value, StringBuilder sb, String offset) {
        if (value instanceof Schema schema) {
            Field<IN, ?>[] fields = schema.getFields();
            apply(sb, null, fields, offset);
        } else if (value instanceof Collection<?> collection) {
            sb.append(System.lineSeparator());
            offset += INDENTATION;
            addCollection(sb, collection, offset);
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
