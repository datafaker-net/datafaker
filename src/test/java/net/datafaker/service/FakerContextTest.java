package net.datafaker.service;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Locality;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

class FakerContextTest {
    private final RandomService random = new RandomService();

    @Test
    void localeChain() {
        assertThat(chain(ENGLISH)).containsExactly(ENGLISH);
        assertThat(chain(new Locale("et", "EE"))).containsExactly(new Locale("et", "EE"), new Locale("et"), new Locale("", "EE"), ENGLISH);
        assertThat(chain(new Locale("hu_HU"))).containsExactly(new Locale("hu", "HU"), new Locale("hu"), new Locale("", "HU"), ENGLISH);
    }

    @ParameterizedTest
    @MethodSource("languagesMatchingCountryCode")
    void appendsDefaultCountryForGivenLanguage(String language) {
        String countryCode = language.toUpperCase(Locale.ROOT);
        assertThat(chain(new Locale(language))).containsExactly(new Locale(language), new Locale("", countryCode), ENGLISH);
    }

    @ParameterizedTest
    @MethodSource("languagesDifferentFromCountryCode")
    void appendsDefaultCountryForGivenLanguage2(String language, String country) {
        assertThat(chain(new Locale(language))).containsExactly(
            new Locale(language),
            new Locale("", country),
            ENGLISH
        );
    }

    private static Stream<Arguments> languagesDifferentFromCountryCode() {
        return languageCountry().entrySet().stream()
            .map(e -> Arguments.of(e.getKey(), e.getValue()));
    }

    private static List<String> languagesMatchingCountryCode() {
        return new Locality(new BaseFaker()).allSupportedLocales().stream()
            .filter(l -> !l.startsWith("_") && l.length() == 2)
            .filter(l -> !languageCountry().containsKey(l))
            .filter(l -> !"en".equals(l))
            .toList();
    }

    private static Map<String, String> languageCountry() {
        return Map.ofEntries(
            Map.entry("be", "BY"),
            Map.entry("cs", "CZ"),
            Map.entry("da", "DK"),
            Map.entry("el", "GR"),
            Map.entry("et", "EE"),
            Map.entry("he", "IL"),
            Map.entry("hy", "AM"),
            Map.entry("ja", "JP"),
            Map.entry("ka", "GE"),
            Map.entry("ko", "KR"),
            Map.entry("nb", "NO"),
            Map.entry("sq", "AL"),
            Map.entry("sv", "SE"),
            Map.entry("ta", "IN"),
            Map.entry("uk", "UA"),
            Map.entry("vi", "VN"),
            Map.entry("zh", "CN"),
            Map.entry("", "")
        );
    }

    private List<Locale> chain(Locale locale) {
        return new FakerContext(locale, random).getLocaleChain().stream()
            .map(singletonLocale -> singletonLocale.getLocale())
            .toList();
    }
}
