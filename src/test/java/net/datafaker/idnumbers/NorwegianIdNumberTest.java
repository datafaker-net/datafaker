package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.providers.base.PersonIdNumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

class NorwegianIdNumberTest {
    private final NorwegianIdNumber generator = new NorwegianIdNumber();
    private final Faker faker = new Faker();

    @RepeatedTest(100)
    void valid() {
        String pin = generator.generateValid(faker);
        assertThat(pin)
            .as(() -> "Presumably valid PIN: '%s'".formatted(pin))
            .hasSize(11)
            .matches("\\d{11}");
    }

    @ParameterizedTest
    @CsvSource({
        "110779410, 12",
        "010203987, 67",
    })
    void checksum(String basePart, int expectedChecksum) {
        assertThat(generator.checksum(basePart)).isEqualTo(expectedChecksum);
    }

    @ParameterizedTest
    @CsvSource({
        "1854-12-31, MALE, 311254..ODD, 50, 74",
        "1854-12-31, FEMALE, 311254..EVEN, 50, 74",
        "1900-01-01, MALE, 010100..ODD, 0, 49 ",
        "1940-12-31, MALE, 311240..ODD, 90, 99",
        "2010-12-31, MALE, 311210..ODD, 50, 99",
    })
    void basePart(
        LocalDate birthday,
        PersonIdNumber.Gender gender,
        String expectedRegex,
        int expectedI12Min,
        int expectedI12Max
    ) {
        String pin = generator.basePart(faker, birthday, gender);
        assertThat(pin)
            .as(() -> "PIN '%s' does not match %s".formatted(pin, expectedRegex))
            .hasSize(9)
            .matches(expectedRegex.replace("ODD", "[13579]").replace("EVEN", "[02468]").replace(".", "\\d"));

        assertThat(parseInt(pin.substring(6, 8)))
            .isBetween(expectedI12Min, expectedI12Max);
    }

    @RepeatedTest(100)
    void invalid() {
        String generated = generator.generateInvalid(faker);
        String basePart = generated.substring(0, generated.length() - 2);
        int checksum = parseInt(generated.substring(generated.length() - 2));
        int validChecksum = generator.checksum(basePart);

        assertThat(generated)
            .describedAs("Invalid ID number should have a valid length")
            .hasSize(11)
            .describedAs("Invalid ID number should consist from numbers only")
            .matches("\\d+");
        assertThat(checksum)
            .describedAs("Checksum should be broken")
            .isNotEqualTo(validChecksum);
    }
}
