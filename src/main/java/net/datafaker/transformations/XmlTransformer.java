package net.datafaker.transformations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import net.datafaker.sequence.FakeSequence;

public class XmlTransformer<IN> implements Transformer<IN, CharSequence> {

    private static final Map<Character, String> ESCAPING_MAP = createEscapeMap();
    private static final int INDENTATION_STEP = 4;

    private int tagIndex = 0;

    private final boolean pretty;

    private XmlTransformer(boolean pretty) {
        this.pretty = pretty;
    }

    @Override
    public CharSequence apply(IN input, Schema<IN, ?> schema) {
        StringBuilder sb = new StringBuilder();
        schema.fields().forEach(it -> apply(input, sb, it));
        return sb.toString();
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
    public CharSequence generate(Schema<IN, ?> schema, int limit) {
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

    public static class XmlTransformerBuilder<IN> {

        private boolean pretty = false;

        public XmlTransformer.XmlTransformerBuilder<IN> pretty(boolean pretty) {
            this.pretty = pretty;
            return this;
        }

        public XmlTransformer<IN> build() {
            return new XmlTransformer<>(pretty);
        }
    }

    private void apply(IN input, StringBuilder sb, Field<IN, ?> xmlNode) {

        if (pretty && tagIndex > 0) {
            sb.append(System.lineSeparator()).append(offset(tagIndex));
        }

        final String tag = xmlNode.getName().trim();
        sb.append("<").append(tag);
        if (xmlNode instanceof CompositeField compositeField) {
            Collection<Field<IN, ?>> attrs = compositeField.fields();
            applyAttributes(input, sb, attrs);

            xmlNode = attrs.stream()
                .filter(inField -> !isAttribute(inField.getName())).findFirst()
                .orElse(null);
        }

        applyTag(input, sb, xmlNode, tag);
    }

    private void applyTag(IN input, StringBuilder sb, Field<IN, ?> field, String tag) {
        if (field == null ) {
            applyValue(sb, tag, null);
            return;
        }

        Object xmlNodeValue = field.transform(input);
        if (xmlNodeValue instanceof Collection<?> children) {
            if (children.isEmpty()) {
                applyValue(sb, tag, null);
            } else {
                sb.append(">");
                tagIndex++;
                for (Object xmlNodeElem : children) {
                    apply(input, sb, (Field<IN, ?>) xmlNodeElem);
                }
                tagIndex--;
                if (pretty) {
                    sb.append(System.lineSeparator()).append(offset(tagIndex));
                }
                sb.append("</").append(tag).append(">");
            }

        } else if (xmlNodeValue instanceof String) {
            applyValue(sb, tag, (String) xmlNodeValue);
        } else if (xmlNodeValue == null) {
            applyValue(sb, tag, null);
        }
    }

    private boolean isAttribute(String name) {
        return name != null;
    }

    private void applyAttributes(IN input, StringBuilder sb, Iterable<Field<IN, ?>> attrs) {
        for (Field<IN, ?> attr : attrs) {
            String name = attr.getName();
            if (isAttribute(name)) {
                String value = (String) attr.transform(input);
                sb.append(" ").append(name).append("=\"").append(escape(value)).append("\"");
            }
        }
    }

    private void applyValue(StringBuilder sb, String tag, String xmlNodeValue) {
        if (xmlNodeValue != null) {
            sb.append(">");
            sb.append(escape(xmlNodeValue));
            sb.append("</").append(tag).append(">");
        } else {
            sb.append("/>");
        }
    }

    private String offset(int length) {
        return " ".repeat(Math.max(0, length * INDENTATION_STEP));
    }

    private String escape(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            sb.append(ESCAPING_MAP.getOrDefault(c, String.valueOf(c)));
        }
        return sb.toString();
    }

    private static Map<Character, String> createEscapeMap() {
        return Map.of('<', "&lt;", '>', "&gt;", '&', "&amp;", '\'', "&apos;", '"', "&quot;");
    }
}
