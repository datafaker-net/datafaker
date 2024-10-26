package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class PassportTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(10)
    void testDefaultLocale() {
        assertThat(new BaseFaker().passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void testValidDutch() {
        assertThat(new BaseFaker(new Locale("nl", "nl")).passport().valid())
            .hasSize(9)
            .doesNotContain("O")
            .matches(".*?\\d$");
    }

    @RepeatedTest(10)
    void testValidChinese() {
        assertThat(new BaseFaker(new Locale("zh", "CN")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void testValidUnitedStates() {
        assertThat(new BaseFaker(new Locale("en", "US")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void testValidAustralia() {
        assertThat(new BaseFaker(new Locale("en", "AU")).passport().valid())
            .hasSize(8)
            .matches("[A-Z][0-9]{7}");
    }

    @RepeatedTest(10)
    void testValidCanada() {
        assertThat(new BaseFaker(new Locale("en", "CA")).passport().valid())
            .hasSize(8);
    }

    @RepeatedTest(10)
    void testValidUnitedKingdom() {
        assertThat(new BaseFaker(new Locale("en", "GB")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void testValidJapan() {
        assertThat(new BaseFaker(new Locale("ja")).passport().valid())
            .hasSize(9)
            .matches("[MT][A-Z][0-9]{7}");
    }

    @RepeatedTest(10)
    void testValidSpain() {
        assertThat(new BaseFaker(new Locale("es")).passport().valid())
            .matches("[A-z0-9]{2,3}[0-9]{6}");
    }

    @RepeatedTest(10)
    void testValidBulgaria() {
        assertThat(new BaseFaker(new Locale("bg")).passport().valid())
            .hasSize(9);
    }

    @RepeatedTest(10)
    void testValidFinland() {
        assertThat(new BaseFaker(new Locale("fi", "FI")).passport().valid())
            .hasSize(9);
    }

}
