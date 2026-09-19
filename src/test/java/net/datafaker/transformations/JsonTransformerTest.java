package net.datafaker.transformations;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

class JsonTransformerTest{

    private final Faker faker = new Faker();

    @Test
    void floatsAndBytesAreNumbers() {
        Schema<Object, ?> schema = Schema.of(
            field("float", () -> 1.25f),
            field("byte", () -> (byte) -12),
            field("text", () -> "1.25"));

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();

        assertThat(transformer.generate(schema, 1))
            .isEqualTo("{\"float\": 1.25, \"byte\": -12, \"text\": \"1.25\"}");
    }

    @Test
    void floatsAndBytesInCollectionsAreNumbers() {
        Schema<Object, ?> schema = Schema.of(
            field("values", () -> List.of(-1.25f, (byte) 12, "1.25")));

        JsonTransformer<Object> transformer = JsonTransformer.builder().build();

        assertThat(transformer.generate(schema, 1))
            .isEqualTo("{\"values\": [-1.25, 12, \"1.25\"]}");
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
