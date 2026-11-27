package net.datafaker.integration;

import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class Issue194SlashFormatRegexTest {

    // First two digits of the national postal codes: 01-08, 10-18, 20-26, 30-36, 40-57, 59-67, 70-78, 80-87, 90-92, 94-98
    private static final Set<Integer> VIETNAM_POSTAL_PREFIXES = IntStream.rangeClosed(1, 98)
        .filter(p -> !Set.of(9, 19, 27, 28, 29, 37, 38, 39, 58, 68, 69, 79, 88, 89, 93).contains(p))
        .boxed()
        .collect(Collectors.toSet());

    @Test
    void enGBZipCodeReturnsProperRegexifiedValue() {
        final Locale uk = new Locale("en", "GB");

        final String postalCode = new BaseFaker(uk).address().zipCode();

        assertThat(postalCode).matches("[A-PR-UWYZ]([A-HK-Y][0-9][ABEHMNPRVWXY0-9]?|[0-9][ABCDEFGHJKPSTUW0-9]?) [0-9][ABD-HJLNP-UW-Z]{2}");
    }

    @Test
    void enCAZipCodeReturnsProperRegexifiedValue() {
        final Locale uk = new Locale("en", "CA");

        final String postalCode = new BaseFaker(uk).address().zipCode();

        assertThat(postalCode).matches("[A-CEJ-NPR-TVXY][0-9][A-CEJ-NPR-TV-Z] ?[0-9][A-CEJ-NPR-TV-Z][0-9]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"vi", "vi-VN"})
    void viZipCodeReturnsFiveDigitVietnamesePostalCode(String languageTag) {
        final BaseFaker faker = new BaseFaker(Locale.forLanguageTag(languageTag));

        for (int i = 0; i < 100; i++) {
            final String postalCode = faker.address().zipCode();

            assertThat(postalCode).matches("\\d{5}");
            assertThat(VIETNAM_POSTAL_PREFIXES).contains(Integer.parseInt(postalCode.substring(0, 2)));
        }
    }
}
