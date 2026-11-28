package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URL;
import java.util.Collection;
import java.util.List;

class CountryTest extends BaseFakerTest {

    private final Country country = faker.country();

    @RepeatedTest(10)
    void testFlag() {
        assertThat(country.flag()).matches("^https://flagcdn\\.com/w580/[a-z]{2}+\\.png$");
    }

    @ParameterizedTest
    @ValueSource(strings = {"w580", "w320", "h80", "32x24"})
    void testFlagWithSizeProducesValidUrl(String size) {
        assertThatNoException().isThrownBy(() -> new URL(country.flag(size, Country.ImageFormat.PNG)));
    }

    @ParameterizedTest
    @EnumSource(Country.ImageFormat.class)
    void testFlagWithFormatProducesValidUrl(Country.ImageFormat format) {
        assertThatNoException().isThrownBy(() -> new URL(country.flag("w320", format)));
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
