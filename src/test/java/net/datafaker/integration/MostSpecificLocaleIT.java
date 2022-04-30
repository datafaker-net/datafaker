package net.datafaker.integration;

import net.datafaker.service.FakeValuesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The purpose of these tests is to ensure that the Locales have been properly configured
 * and that methods return values. The unit tests should ensure what the values returned
 * are correct. These tests just ensure that the methods can be invoked.
 */
class MostSpecificLocaleIT {

    private FakeValuesService en;
    private FakeValuesService en_US;

    @BeforeEach
    void setupFakers() {
        en = new FakeValuesService(new Locale("en"), null);
        en_US = new FakeValuesService(new Locale("en", "US"), null);
    }

    @Test
    @SuppressWarnings("unchecked")
    void resolvesTheMostSpecificLocale() {
        final List<String> enDefaultCountries = (List<String>) en.fetchObject("address.default_country");
        final List<String> enUsDefaultCountries = (List<String>) en_US.fetchObject("address.default_country");

        assertThat(enDefaultCountries).hasSize(1);
        assertThat(enUsDefaultCountries).hasSize(3);

        assertThat(enDefaultCountries).as("the default country for en is not en_US").isNotEqualTo(enUsDefaultCountries);
    }
}
