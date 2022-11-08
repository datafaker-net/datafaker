package net.datafaker.formats;

import net.datafaker.transformations.Field;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.XmlTransformer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class XmlTest {

    @ParameterizedTest
    @MethodSource("generateTestXmlSchema")
    void xmlSchemaTest(Schema<String, String> schema, String expected) {
        XmlTransformer<String> xml = new XmlTransformer.XmlTransformerBuilder<String>().build();
        assertThat(xml.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestXmlSchema() {
        return Stream.of(
            of(Schema.of(field("root", Collections::emptyList)), "<root/>"),
            of(Schema.of((field("root", () -> "value"))), "<root>value</root>"),
            of(Schema.of(
                    compositeField("root",
                        new Field[]{field("attribute1", () -> "value1"), field("attribute2", () -> "value2")})),
                "<root attribute1=\"value1\" attribute2=\"value2\"/>"),
            of(Schema.of(
                    compositeField("root",
                        new Field[]{field("attribute1", () -> "value1"), field("attribute2", () -> "value2"), field(null, () -> "value"),})),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            of(Schema.of(
                    compositeField("root",
                        new Field[]{field("attribute1", () -> "value1"), field("attribute2", () -> "value2"),
                            field(null, () -> Collections.singletonList(field("child", () -> "value")))})),
                "<root attribute1=\"value1\" attribute2=\"value2\"><child>value</child></root>"),
            of(Schema.of(field("root", () -> "<> value\"")), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    @ParameterizedTest
    @MethodSource("generateTestXmlPrettySchema")
    void xmlPrettySchemaTest(Schema<String, String> schema, String expected) {
        XmlTransformer<String> xml = new XmlTransformer.XmlTransformerBuilder<String>().pretty(true).build();
        assertThat(xml.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestXmlPrettySchema() {
        return Stream.of(
            of(Schema.of(field("root", Collections::emptyList)), "<root/>"),
            of(Schema.of((field("root", () -> "value"))), "<root>value</root>"),
            of(Schema.of(
                    compositeField("root",
                        new Field[]{field("attribute1", () -> "value1"), field("attribute2", () -> "value2")})),
                "<root attribute1=\"value1\" attribute2=\"value2\"/>"),
            of(Schema.of(
                    compositeField("root",
                        new Field[]{field("attribute1", () -> "value1"), field("attribute2", () -> "value2"), field(null, () -> "value"),})),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            of(Schema.of(
                    compositeField("root",
                        new Field[]{field("attribute1", () -> "value1"), field("attribute2", () -> "value2"),
                            field(null, () -> Collections.singletonList(field("child", () -> "value")))})),
                "<root attribute1=\"value1\" attribute2=\"value2\">" + System.lineSeparator() + "    <child>value</child>" + System.lineSeparator() + "</root>"),
            of(Schema.of(field("root", () -> "<> value\"")), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    @ParameterizedTest
    @MethodSource("generateTestXmlPretty")
    void xmlPrettyTest(Xml.XmlNode xmlNode, String expected) {
        Xml xml = new Xml(xmlNode);
        assertThat(xml.generate(true)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestXmlPretty() {
        return Stream.of(
            Arguments.of(new Xml.XmlNode("root", Collections.emptyList()), "<root/>"),
            Arguments.of(new Xml.XmlNode("root", "value"), "<root>value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")), "value"),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")),
                    Collections.singletonList(new Xml.XmlNode("child", "value"))),
                "<root attribute1=\"value1\" attribute2=\"value2\">" + System.lineSeparator() + "    <child>value</child>" + System.lineSeparator() + "</root>"),
            Arguments.of(new Xml.XmlNode("root", "<> value\""), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    @ParameterizedTest
    @MethodSource("generateTestXml")
    void xmlTest(Xml.XmlNode xmlNode, String expected) {
        Xml xml = new Xml(xmlNode);
        assertThat(xml.generate()).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestXml() {
        return Stream.of(
            Arguments.of(new Xml.XmlNode("root", Collections.emptyList()), "<root/>"),
            Arguments.of(new Xml.XmlNode("root", "value"), "<root>value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")), "value"),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            Arguments.of(new Xml.XmlNode("root",
                    map(entry("attribute1", "value1"), entry("attribute2", "value2")),
                    Collections.singletonList(new Xml.XmlNode("child", "value"))),
                "<root attribute1=\"value1\" attribute2=\"value2\"><child>value</child></root>"),
            Arguments.of(new Xml.XmlNode("root", "<> value\""), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    private static Map.Entry<String, String> entry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @SafeVarargs
    private static Map<String, String> map(Map.Entry<String, String>... entries) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
