package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.providers.base.IdNumber;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class MexicanIdNumberTest {

    private static final Pattern REGEX = Pattern.compile(
        "[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z,0-9]\\d");
    private static final Faker faker = new Faker(new Locale("es", "MX"));
    private final IdNumber idNumber = faker.idNumber();

    @RepeatedTest(100)
    void valid() {
        assertThat(idNumber.valid()).matches(REGEX);
    }

    @RepeatedTest(100)
    @SuppressWarnings("deprecation")
    void validEsMXSsn() {
        assertThat(idNumber.validEsMXSsn()).matches(REGEX);
    }

    @RepeatedTest(100)
    void invalid() {
        assertThat(idNumber.invalid()).matches(REGEX);
    }

    @RepeatedTest(100)
    @SuppressWarnings("deprecation")
    void invalidEsMXSsn() {
        assertThat(idNumber.invalidEsMXSsn()).matches(REGEX);
    }
}
