package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.helpers.IdNumberPatterns;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SpanishIdNumberTest {
    private final SpanishIdNumber generator = new SpanishIdNumber();
    private final Faker faker = new Faker();

    @ParameterizedTest
    @CsvSource({
        "00000000, T",
        "00000001, R",
        "12345678, Z",
        "99999999, R",
    })
    void checksum(String digits, char expectedLetter) {
        assertThat(SpanishIdNumber.checksum(digits)).isEqualTo(expectedLetter);
    }

    @RepeatedTest(100)
    void valid() {
        String generated = generator.generateValid(faker);

        assertThat(generated).matches(IdNumberPatterns.SPANISH);
        assertThat(generated.charAt(8))
            .as("DNI check letter")
            .isEqualTo(SpanishIdNumber.checksum(generated.substring(0, 8)));
    }

    @RepeatedTest(100)
    void invalid() {
        String generated = generator.generateInvalid(faker);

        assertThat(generated).matches(IdNumberPatterns.SPANISH);
        assertThat(generated.charAt(8))
            .as("DNI check letter should be invalid")
            .isNotEqualTo(SpanishIdNumber.checksum(generated.substring(0, 8)));
    }
}
