package net.datafaker.service;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class LocalePickerTest extends AbstractFakerTest {

    private LocalePicker localePicker;
    private List<String> allLocales;

    /**
     * Initialize tests by instantiating a LocalePicker object and list of all supported locales
     */
    @BeforeEach
    void init() {
        localePicker = new LocalePicker();
        allLocales = localePicker.getAllSupportedLocales();
    }

    /**
     * Test to check that list of all locales support is loaded
     */
    @Test
    void testGetAllSuppportedLocales() {
        // Check that directory of locale resources exists
        File resourceDirectory = new File("./src/main/resources");
        assertThat(resourceDirectory).exists();

        // Check that list of locales is not empty
        assertThat(allLocales).isNotEmpty();
    }

    /**
     * Test to check LocalePicker's getLocaleString method is using the random number generator
     * passed as an argument. This is checked with a Random object that has a fixed seed and
     * should have deterministic results.
     */
    @Test
    void testGetLocaleStringRandom() {
        // Check that we get the same locale when using pseudorandom number generator with a fixed seed
        final long fixedSeed = 5;

        Random random1 = new Random(fixedSeed);
        String randomLocale1 = localePicker.getLocaleString(random1);

        Random random2 = new Random(fixedSeed);
        String randomLocale2 = localePicker.getLocaleString(random2);

        assertThat(randomLocale1).isEqualTo(randomLocale2);
    }

    /**
     * Test to check LocalePicker's getLocaleString method. It verifies that the randomly selected
     * locale is within the set of all supported locales
     */
    @RepeatedTest(100)
    void testGetLocaleString() {
        Random random = new Random();
        String randomLocale = localePicker.getLocaleString(random);
        assertThat(allLocales).contains(randomLocale);
    }

    /**
     * Test to check LocalePicker's getLocaleStringWithoutReplacement method.
     * It randomly selects n locales where n is the number of locales.
     * It ensures that all the locales supported are represented once.
     */
    @Test
    void testGetLocaleStringWithoutReplacement() {
        Random random = new Random();

        // loop through all supported locales
        for (int i = 0; i < 2; i++) {
            List<String> returnedLocales = IntStream.range(0, allLocales.size())
                .mapToObj(j -> localePicker.getLocaleStringWithoutReplacement(random))
                .sorted()
                .collect(Collectors.toList());

            Collections.sort(allLocales);
            assertThat(returnedLocales).isEqualTo(allLocales);
        }
    }

    @Test
    void testGetLocale() {
        assertThat(localePicker.getLocale()).isNotNull();
    }

    @Test
    void testGetLocaleWithoutReplacement() {
        assertThat(localePicker.getLocaleWithoutReplacement()).isNotNull();
    }
}
