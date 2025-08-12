package net.datafaker.transformations;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

class JsonTransformerTest{

    private final Faker faker = new Faker();

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
