package net.datafaker.integration;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
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

    private final FakerContext en = new FakerContext(new Locale("en"), null);
    private final FakerContext en_US = new FakerContext(new Locale("en", "US"), null);

    @Test
    void resolvesTheMostSpecificLocale() {
        List<String> enDefaultCountries = fakeValuesService(en).fetchObject("address.default_country", en);
        List<String> enUsDefaultCountries = fakeValuesService(en_US).fetchObject("address.default_country", en_US);

        assertThat(enDefaultCountries).hasSize(1);
        assertThat(enUsDefaultCountries).hasSize(3);

        assertThat(enDefaultCountries).as("the default country for en is not en_US").isNotEqualTo(enUsDefaultCountries);
    }

    private static FakeValuesService fakeValuesService(FakerContext context) {
        FakeValuesService service = new FakeValuesService();
        service.updateFakeValuesInterfaceMap(context.getLocaleChain());
        return service;
    }
}
