package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class CountryTest extends BaseFakerTest<BaseFaker> {

    Country country = faker.country();

    @RepeatedTest(10)
    void testFlag() {
        String flag = country.flag();
        assertThat(flag).matches("^https://flags.fmcdn\\.net/data/flags/w580/[a-zA-Z0-9_]+\\.png$");
    }

    @Test
    void testCurrency() {
        assertThat(faker.country().currency()).isNotEmpty();
    }

    @Test
    void testCurrencyCode() {
        assertThat(faker.country().currencyCode()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(country::countryCode2, "country.code2", "[a-z]{2}"),
                TestSpec.of(country::countryCode3, "country.code3", "[a-z]{3}"),
                TestSpec.of(country::capital, "country.capital", "([\\p{L}0-9+,. '-])+"),
                TestSpec.of(country::name, "country.name"));
    }
}
