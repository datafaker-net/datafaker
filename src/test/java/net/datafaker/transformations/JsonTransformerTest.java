package net.datafaker.transformations;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

class JsonTransformerTest{

    private final Faker faker = new Faker();

    @Test
    void numericValuesAreRepresentedAsNumbersInJson() {
        Schema<Object, ?> schema = Schema.of(
            field("int", () -> 67),
            field("long", () -> 67000000000067L),
            field("short", () -> (short) 42),
            field("float", () -> 1.25f),
            field("double", () -> 0.67d),
            field("byte", () -> (byte) -12),
            field("bigDecimal", () -> new BigDecimal("-1000.99")),
            field("bigInteger", () -> new BigInteger("-2000000000")),
            field("boolean", () -> true),
            field("text", () -> "1.25"));

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();

        assertThat(transformer.generate(schema, 1))
            .isEqualToIgnoringNewLines("""
                {"int": 67, "long": 67000000000067,
                 "short": 42, "float": 1.25, "double": 0.67, "byte": -12,
                 "bigDecimal": -1000.99, "bigInteger": -2000000000,
                 "boolean": true,
                 "text": "1.25"}""");
    }

    @Test
    void numericValuesInCollectionsAreRepresentedAsNumbersInJson() {
        Schema<Object, ?> schema = Schema.of(
            field("values", () -> List.of(
                67, 67000000000067L, (short) 42, -1.25f, -0.67d, (byte) 12,
                new BigDecimal("-1000.99"), new BigInteger("-2000000000"),
                false,
                "1.25"
            )));

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();

        assertThat(transformer.generate(schema, 1))
            .isEqualTo("{\"values\": [67, 67000000000067, 42, -1.25, -0.67, 12, -1000.99, -2000000000, false, \"1.25\"]}");
    }

    @Test
    void nonFiniteFloatsRemainStrings() {
        Schema<Object, ?> schema = Schema.of(
            field("values", () -> List.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)));

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();

        assertThat(transformer.generate(schema, 1))
            .isEqualTo("{\"values\": [\"NaN\", \"Infinity\", \"-Infinity\"]}");
    }

    @Test
    void nonFiniteDoublesRemainStrings() {
        Schema<Object, ?> schema = Schema.of(
            field("values", () -> List.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)));

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();

        assertThat(transformer.generate(schema, 1))
            .isEqualTo("{\"values\": [\"NaN\", \"Infinity\", \"-Infinity\"]}");
    }

    @Test
    void issue1556Double() {
        // Given
        double aDouble = faker.number().randomDouble(3, 1, 2);
        Schema<Object, ?> schema = Schema.of(
                field("Dbl", () -> aDouble));
        // When
        JsonTransformer<Object> transformer = JsonTransformer.builder().build();
        String json = transformer.generate(schema, 1);
        // Then - Number not quoted
        assertThat(json).isEqualTo("{\"Dbl\": %s}".formatted(aDouble));
    }

    @Test
    void issue1556BigDecimal() {
        // Given
        BigDecimal aBigDec = BigDecimal.valueOf(faker.number().randomDouble(7, 1, 2));
        Schema<Object, ?> schema = Schema.of(
                field("BigDecimal", () -> aBigDec));
        // When
        JsonTransformer<Object> transformer = JsonTransformer.builder().build();
        String json = transformer.generate(schema, 1);
        // Then - Number not quoted
        assertThat(json).isEqualTo("{\"BigDecimal\": %s}".formatted(aBigDec));
    }
}
