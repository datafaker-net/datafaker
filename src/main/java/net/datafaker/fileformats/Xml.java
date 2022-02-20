package net.datafaker.fileformats;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Xml {
    private static final Map<Character, String> ESCAPING_MAP = createEscapeMap();
    private static final int INDENTATION_STEP = 4;
    private final XmlNode xmlNode;
    private int tagIndex = 0;

    public Xml(XmlNode xmlNode) {
        this.xmlNode = xmlNode;
    }

    public String generate() {
        return generate(false);
    }

    public String generate(boolean pretty) {
        return generate(xmlNode, new StringBuilder(), pretty);
    }

    private String generate(XmlNode xmlNode, StringBuilder sb, boolean pretty) {
        final String tag = xmlNode.tagName;
        if (pretty && tagIndex > 0) {
            sb.append(System.lineSeparator()).append(offset(tagIndex));
        }
        sb.append("<").append(tag);
        for (Map.Entry<String, String> entry : xmlNode.attributes.entrySet()) {
            sb.append(" ").append(entry.getKey()).append("=\"").append(escape(entry.getValue())).append("\"");
        }

        if (xmlNode.children.isEmpty()) {
            if (xmlNode.value != null) {
                sb.append(">");
                sb.append(escape(xmlNode.value));
                sb.append("</").append(tag).append(">");
            } else {
                sb.append("/>");
            }
        } else {
            sb.append(">");
            tagIndex++;
            for (XmlNode xmlNodeElem : xmlNode.children) {
                generate(xmlNodeElem, sb, pretty);
            }
            tagIndex--;
            if (pretty) {
                sb.append(System.lineSeparator()).append(offset(tagIndex));
            }
            sb.append("</").append(tag).append(">");

        }
        return sb.toString();
    }

    private String escape(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            sb.append(ESCAPING_MAP.getOrDefault(c, c + ""));
        }
        return sb.toString();
    }

    private String offset(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length * INDENTATION_STEP; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static class XmlNode {
        private final String tagName;
        private final Map<String, String> attributes;
        private final Collection<XmlNode> children;
        private final String value;

        public XmlNode(String tagName, Collection<XmlNode> children) {
            this.tagName = tagName;
            this.attributes = Collections.emptyMap();
            this.children = children;
            value = null;
        }

        public XmlNode(String tagName, Map<String, String> attributes, Collection<XmlNode> children) {
            this.tagName = tagName;
            this.attributes = attributes;
            this.children = children;
            value = null;
        }

        public XmlNode(String tagName, Map<String, String> attributes, String value) {
            this.tagName = tagName;
            this.attributes = attributes;
            this.children = Collections.emptyList();
            this.value = value;
        }

        public XmlNode(String tagName, String value) {
            this.tagName = tagName;
            this.attributes = Collections.emptyMap();
            this.children = Collections.emptyList();
            this.value = value;
        }
    }

    private static Map<Character, String> createEscapeMap() {
        final Map<Character, String> map = new HashMap<>();
        map.put('<', "&lt;");
        map.put('>', "&gt;");
        map.put('&', "&amp;");
        map.put('\'', "&apos;");
        map.put('"', "&quot;");
        return Collections.unmodifiableMap(map);
    }
}