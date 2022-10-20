package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(10)
    void flag() {
        String flag = faker.country().flag();
        assertThat(flag).matches("^https://flags.fmcdn\\.net/data/flags/w580/[a-zA-Z0-9_]+\\.png$");
    }

    @Test
    void code2() {
        assertThat(faker.country().countryCode2()).matches("([a-z]{2})");
    }

    @Test
    void code3() {
        assertThat(faker.country().countryCode3()).matches("([a-z]{3})");
    }

    @Test
    void capital() {
        assertThat(faker.country().capital()).matches("([\\p{L}0-9+,. '-])+");
    }

    @Test
    void currency() {
        assertThat(faker.country().currency()).matches("([A-Za-zÀ-ÿ'’()-]+ ?)+");
    }

    @Test
    void currencyCode() {
        assertThat(faker.country().currencyCode()).matches("([\\w-’í]+ ?)+");
    }

    @Test
    void name() {
        assertThat(faker.country().name()).isNotEmpty();
    }
}
