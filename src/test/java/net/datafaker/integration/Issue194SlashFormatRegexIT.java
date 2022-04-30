package net.datafaker.integration;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class Issue194SlashFormatRegexIT {

    @Test
    void enGBZipCodeReturnsProperRegexifiedValue() {
        final Locale uk = new Locale("en-GB");

        final String postalCode = new Faker(uk).address().zipCode();

        assertThat(postalCode).matches("[A-PR-UWYZ]([A-HK-Y][0-9][ABEHMNPRVWXY0-9]?|[0-9][ABCDEFGHJKPSTUW0-9]?) [0-9][ABD-HJLNP-UW-Z]{2}");
    }

    @Test
    void enCAZipCodeReturnsProperRegexifiedValue() {
        final Locale uk = new Locale("en-CA");

        final String postalCode = new Faker(uk).address().zipCode();

        assertThat(postalCode).matches("[A-CEJ-NPR-TVXY][0-9][A-CEJ-NPR-TV-Z] ?[0-9][A-CEJ-NPR-TV-Z][0-9]");
    }

    @Test
    void viZipCodeReturnsProperRegexifiedValue() {
        final Locale uk = new Locale("vi");

        final String postalCode = new Faker(uk).address().zipCode();

        assertThat(postalCode).matches("[A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}");
    }
}
