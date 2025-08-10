package net.datafaker.formats;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.sequence.FakeStream;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.SimpleField;
import net.datafaker.transformations.XmlTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.datafaker.transformations.Field.compositeField;
import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class XmlTest {

    @ParameterizedTest
    @MethodSource("generateTestXmlSchema")
    void xmlSchemaTest(Schema<String, String> schema, String expected) {
        XmlTransformer<String> xmlTransformer = new XmlTransformer.XmlTransformerBuilder<String>().build();
        assertThat(xmlTransformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestXmlSchema() {
        return Stream.of(
            of(Schema.of(field("root", Collections::emptyList)), "<root/>"),
            of(Schema.of((field("root", () -> "value"))), "<root>value</root>"),
            of(Schema.of(
                    compositeField("root",
                        field("attribute1", () -> "value1"), field("attribute2", () -> "value2"))),
                "<root attribute1=\"value1\" attribute2=\"value2\"/>"),
            of(Schema.of(
                    compositeField("root",
                        field("attribute1", () -> "value1"), field("attribute2", () -> "value2"), field(null, () -> "value"))),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            of(Schema.of(
                    compositeField("root",
                        field("attribute1", () -> "value1"), field("attribute2", () -> "value2"),
                        field(null, () -> List.of(field("child", () -> "value")))
                    )),
                "<root attribute1=\"value1\" attribute2=\"value2\"><child>value</child></root>"),
            of(Schema.of(field("root", () -> "<> value\"")), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    @ParameterizedTest
    @MethodSource("generateTestXmlPrettySchema")
    void xmlPrettySchemaTest(Schema<String, String> schema, String expected) {
        XmlTransformer<String> xmlTransformer = new XmlTransformer.XmlTransformerBuilder<String>().pretty(true).build();
        assertThat(xmlTransformer.generate(schema, 1)).isEqualTo(expected);
    }

    private static Stream<Arguments> generateTestXmlPrettySchema() {
        return Stream.of(
            of(Schema.of(field("root", Collections::emptyList)), "<root/>"),
            of(Schema.of((field("root", () -> "value"))), "<root>value</root>"),
            of(Schema.of(
                    compositeField("root",
                        field("attribute1", () -> "value1"), field("attribute2", () -> "value2")
                    )),
                "<root attribute1=\"value1\" attribute2=\"value2\"/>"),
            of(Schema.of(
                    compositeField("root",
                        field("attribute1", () -> "value1"), field("attribute2", () -> "value2"), field(null, () -> "value")
                    )),
                "<root attribute1=\"value1\" attribute2=\"value2\">value</root>"),
            of(Schema.of(
                    compositeField("root",
                        field("attribute1", () -> "value1"), field("attribute2", () -> "value2"),
                        field(null, () -> List.of(field("child", () -> "value")))
                    )),
                "<root attribute1=\"value1\" attribute2=\"value2\">" + System.lineSeparator() + "    <child>value</child>" + System.lineSeparator() + "</root>"),
            of(Schema.of(field("root", () -> "<> value\"")), "<root>&lt;&gt; value&quot;</root>")
        );
    }

    @Test
    void generateFromFakeSequence() {
        final BaseFaker faker = new BaseFaker();

        Schema<Name, List<SimpleField<Name, String>>> schema = Schema.of(
            field("root",
                () -> List.of(
                    field("firstName", Name::firstName),
                    field("lastName", Name::lastName))));

        XmlTransformer<Name> transformer = new XmlTransformer.XmlTransformerBuilder<Name>().pretty(true).build();
        String xml = transformer.generate(
            faker.<Name>collection().suppliers(faker::name).maxLen(1).build(),
            schema);

        int numberOfLines = getNumberOfLines(xml);
        assertThat(numberOfLines).isEqualTo(4);
    }

    @Test
    void generateXMLWithThreeNestedLevels() {
        final BaseFaker faker = new BaseFaker();

        FakeStream<?> address = (FakeStream<SimpleField<String, List<Object>>>)
            faker.<SimpleField<String, List<Object>>>stream()
                .suppliers(() ->
                    field("address",
                        () -> List.of(
                            field("country", () -> faker.address().country()),
                            field("city", () -> faker.address().city()),
                            field("streetAddress", () -> faker.address().streetAddress()))))
                .maxLen(3).build();

        FakeStream<?> persons = (FakeStream<SimpleField<Object, List<Object>>>)
            faker.<SimpleField<Object, List<Object>>>stream()
                .suppliers(() ->
                    field("person",
                        () -> List.of(
                            field("firstname", () -> faker.name().firstName()),
                            field("lastname", () -> faker.name().lastName()),
                            field("addresses", () -> address.get().collect(Collectors.toList())))))
                .maxLen(3).build();


        XmlTransformer<Object> xmlTransformer = new XmlTransformer.XmlTransformerBuilder<>().pretty(true).build();
        String xml = xmlTransformer.generate(Schema.of(field("persons", () -> persons.get().collect(Collectors.toList()))), 1).toString();
        assertThat(xml).isNotEmpty();
        int numberOfLines = getNumberOfLines(xml);
        assertThat(numberOfLines).isEqualTo(65);
    }

    @Test
    void generateXMLWithThreeNestedLevelsAndAttributes() {
        final BaseFaker faker = new BaseFaker();
        FakeStream<Object> address =
            (FakeStream<Object>) faker.stream()
                .suppliers(() ->
                    compositeField("address",
                            field("country", () -> faker.address().country()),
                            field("city", () -> faker.address().city()),
                            field("streetAddress", () -> faker.address().streetAddress())
                    ))
                .maxLen(3).build();

        FakeStream<Object> persons =
            (FakeStream<Object>) faker.stream()
                .suppliers(() ->
                    compositeField("person",
                            field("firstname", () -> faker.name().firstName()),
                            field("lastname", () -> faker.name().lastName()),
                            field(null, () -> List.of(field("addresses", () -> address.get().collect(Collectors.toList()))))
                    ))
                .maxLen(3).build();

        XmlTransformer<Object> xmlTransformer = new XmlTransformer.XmlTransformerBuilder<>().pretty(true).build();
        String xml = xmlTransformer.generate(Schema.of(field("persons", () -> persons.get().collect(Collectors.toList()))), 1).toString();
        assertThat(xml).isNotEmpty();
        int numberOfLines = getNumberOfLines(xml);
        assertThat(numberOfLines).isEqualTo(23);
    }

    private static int getNumberOfLines(String xml) {
        int numberOfLines = 1;
        for (int i = 0; i < xml.length(); i++) {
            if (xml.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }
        return numberOfLines;
    }
}
