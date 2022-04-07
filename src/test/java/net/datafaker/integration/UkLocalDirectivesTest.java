package net.datafaker.integration;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The purpose of these tests is to ensure that the Locales have been properly configured
 * and that methods return values. The unit tests should ensure what the values returned
 * are correct. These tests just ensure that the methods can be invoked.
 */
public class UkLocalDirectivesTest {

    /**
     * uk is interesting in that it has feminine and masculine prefixes for street names.  the feminine
     * and masculine prefixes are NOT methods on Address though as they only make sense for this locale (and possibly
     * others).  This test shows we can resolve within the yml file without reaching out to any of the {@link Faker}
     * child objects.
     */
    @Test
    public void resolvesDirectivesOnlyInYmlFile() {
        final Locale uk = new Locale("uk");

        final String streetName = new Faker(uk).address().streetName();

        final List<String> masc = Arrays.asList("пр.", "проспект", "пров.", "провулок");
        final List<String> fem = Arrays.asList("вул.", "вулиця", "пл.", "площа");

        boolean startsWithMascPrefix = false;
        boolean startsWithFemPrefix = false;

        for (String mascPrefix : masc) {
            startsWithMascPrefix |= streetName.startsWith(mascPrefix);
        }
        for (String femPrefix : fem) {
            startsWithFemPrefix |= streetName.startsWith(femPrefix);
        }

        assertTrue(startsWithFemPrefix || startsWithMascPrefix,
            "the streetname starts with a fem or masc prefix");
    }
}
