package net.datafaker.integration;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
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
class MostSpecificLocaleTest {

    private FakerContext en;
    private FakerContext en_US;

    @BeforeEach
    void setupFakers() {
        en = new FakerContext(new Locale("en"), null);
        en_US = new FakerContext(new Locale("en", "US"), null);
    }

    @Test
    @SuppressWarnings("unchecked")
    void resolvesTheMostSpecificLocale() {
        FakeValuesService fakeValuesService = new FakeValuesService();
        fakeValuesService.updateFakeValuesInterfaceMap(en.getLocaleChain());
        final List<String> enDefaultCountries = (List<String>) fakeValuesService.fetchObject("address.default_country", en);
        fakeValuesService = new FakeValuesService();
        fakeValuesService.updateFakeValuesInterfaceMap(en_US.getLocaleChain());
        final List<String> enUsDefaultCountries = (List<String>) fakeValuesService.fetchObject("address.default_country", en_US);

        assertThat(enDefaultCountries).hasSize(1);
        assertThat(enUsDefaultCountries).hasSize(3);

        assertThat(enDefaultCountries).as("the default country for en is not en_US").isNotEqualTo(enUsDefaultCountries);
    }
}
