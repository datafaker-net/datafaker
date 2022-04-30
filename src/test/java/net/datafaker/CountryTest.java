package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryTest extends AbstractFakerTest {

    @RepeatedTest(10)
    void testFlag() {
        String flag = faker.country().flag();
        assertThat(flag).matches("^https://flags.fmcdn\\.net/data/flags/w580/[a-zA-Z0-9_]+\\.png$");
    }

    @Test
    void testCode2() {
        assertThat(faker.country().countryCode2()).matches("([a-z]{2})");
    }

    @Test
    void testCode3() {
        assertThat(faker.country().countryCode3()).matches("([a-z]{3})");
    }

    @Test
    void testCapital() {
        assertThat(faker.country().capital()).matches("([\\p{L}0-9+,. '-])+");
    }

    @Test
    void testCurrency() {
        assertThat(faker.country().currency()).matches("([A-Za-zÀ-ÿ'’()-]+ ?)+");
    }

    @Test
    void testCurrencyCode() {
        assertThat(faker.country().currencyCode()).matches("([\\w-’í]+ ?)+");
    }

    @Test
    void testName() {
        assertThat(faker.country().name()).isNotEmpty();
    }
}
