package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class PassportTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(10)
    void defaultLocale() {
        assertThat(new BaseFaker().passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void validDutch() {
        assertThat(new BaseFaker(new Locale("nl", "nl")).passport().valid())
            .hasSize(9)
            .doesNotContain("O")
            .matches(".*?\\d$");
    }

    @RepeatedTest(10)
    void validChinese() {
        assertThat(new BaseFaker(new Locale("zh", "CN")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void validUnitedStates() {
        assertThat(new BaseFaker(new Locale("en", "US")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void validAustralia() {
        assertThat(new BaseFaker(new Locale("en", "AU")).passport().valid())
            .hasSize(8)
            .matches("[A-Z][0-9]{7}");
    }

    @RepeatedTest(10)
    void validCanada() {
        assertThat(new BaseFaker(new Locale("en", "CA")).passport().valid())
            .hasSize(8);
    }

    @RepeatedTest(10)
    void validUnitedKingdom() {
        assertThat(new BaseFaker(new Locale("en", "GB")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void validJapan() {
        assertThat(new BaseFaker(new Locale("ja")).passport().valid())
            .hasSize(9)
            .matches("[MT][A-Z][0-9]{7}");
    }

    @RepeatedTest(10)
    void validSpain() {
        assertThat(new BaseFaker(new Locale("es")).passport().valid())
            .matches("[A-z0-9]{2,3}[0-9]{6}");
    }

    @RepeatedTest(10)
    void validBulgaria() {
        assertThat(new BaseFaker(new Locale("bg")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void validFinland() {
        assertThat(new BaseFaker(new Locale("fi", "FI")).passport().valid())
            .hasSize(9);
    }

}
