package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.helpers.IdNumberPatterns;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class IrishIdNumberTest {
    private final IrishIdNumber irishIdNumber = new IrishIdNumber();
    private final Faker faker = new Faker(new Locale("en", "IE"));

    @RepeatedTest(100)
    void validIrishIdNumber() {
        String actual = irishIdNumber.generateValid(faker);
        assertThat(actual).matches(IdNumberPatterns.IRISH);
        assertThat(irishIdNumber.validateAndCheckModulo23(actual)).isTrue();
    }

    @RepeatedTest(100)
    void invalidIrishIdNumber() {
        String actual = irishIdNumber.generateInvalid(faker);
        assertThat(actual).matches(IdNumberPatterns.IRISH);
        assertThat(irishIdNumber.validateAndCheckModulo23(actual)).isFalse();
    }
}
