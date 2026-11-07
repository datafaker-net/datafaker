package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Runs the {@link Address} list specs once per supported locale, so each locale's own data is checked
 * (including the default whitespace validation of {@link TestSpec}).
 * <p>
 * The locale is supplied by {@link #internationalFakers()}, which builds one {@link BaseFaker} per supported
 * locale. Each {@link TestSpec} is paired with its locale's faker in {@link #localeProviderListTest()}, and
 * {@link ProviderListLocaleTest} passes that pair to the shared {@code testProviderList(TestSpec, BaseFaker)} check.
 * <p>
 * Specs are skipped for a locale when the key can't be list-checked there (see {@link #isPlainList}):
 * <ul>
 *   <li>the key is a scalar placeholder rather than a list, e.g. {@code ""} for uk and es-MX prefixes/suffixes,
 *       or {@code "#{region_abbr}"} for en-NZ</li>
 *   <li>the list contains templates ({@code "#{...}"}), e.g. {@code "#{Address.provinces}"} for some states</li>
 * </ul>
 * Not covered at all: {@code address.city}, {@code address.city_name} (templates in every locale) and
 * {@code address.street_prefix} (en defines a scalar placeholder that every locale inherits).
 * Values that are numerified/bothified ({@code postcode}, {@code building_number}, ...) can't be matched
 * against the stored list and are also left out.
 */
class AddressLocaleTest extends ProviderListLocaleTest {

    /** One argument pair (spec, locale faker) per key and supported locale, minus the keys skipped for that locale. */
    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return internationalFakers().stream().flatMap(faker -> {
            Address address = faker.address();
            return Stream.of(
                arguments(TestSpec.of(address::streetSuffix, "address.street_suffix"), faker),
                arguments(citySuffixSpec(faker), faker),
                arguments(TestSpec.of(address::cityPrefix, "address.city_prefix"), faker),
                arguments(TestSpec.of(address::state, "address.state"), faker),
                arguments(TestSpec.of(address::stateAbbr, "address.state_abbr"), faker),
                arguments(TestSpec.of(address::country, "address.country"), faker),
                arguments(TestSpec.of(address::countryCode, "address.country_code"), faker),
                arguments(TestSpec.of(address::timeZone, "address.time_zone"), faker)
            ).filter(args -> isPlainList(faker, ((TestSpec) args.get()[0]).key()));
        });
    }

    /** Dutch city suffixes such as " aan de Rijn" intentionally start with a space, they are appended to a city name. */
    private static TestSpec citySuffixSpec(BaseFaker faker) {
        TestSpec spec = TestSpec.of(faker.address()::citySuffix, "address.city_suffix");
        return "nl".equals(faker.getContext().getLocale().getLanguage()) ? spec.allowOuterWhitespace() : spec;
    }

    /** Some locales define a key as a scalar placeholder or as templates ("#{...}") instead of plain values, those can't be list-checked. */
    private static boolean isPlainList(BaseFaker faker, String key) {
        return faker.fakeValuesService().fetchObject(key, faker.getContext()) instanceof List<?> values
            && values.stream().noneMatch(value -> String.valueOf(value).contains("#{"));
    }
}
