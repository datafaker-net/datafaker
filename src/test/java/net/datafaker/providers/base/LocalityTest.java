package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class LocalityTest extends BaseFakerTest<BaseFaker> {

    private BaseFaker f;
    private Locality locality;
    private List<String> allLocales;

    /**
     * Initialize tests by instantiating a Locality object and list of all supported locales
     */
    @BeforeEach
    void init() {
        f = new Faker();
        locality = f.locality();
        allLocales = locality.allSupportedLocales();
    }

    /**
     * Test to check that list of all locales support is loaded
     */
    @Test
    void testAllSupportedLocales() {
        // Check that directory of locale resources exists
        File resourceDirectory = new File("./src/main/resources");
        assertThat(resourceDirectory).exists();

        // Check that list of locales is not empty
        assertThat(allLocales).isNotEmpty();
    }

    @Test
    void displayName() {
        assertThat(f.locality().displayName()).isNotEmpty();
    }

    /**
     * Test to check Locality's localeStringWithRandom method is using the random number generator
     * passed as an argument. This is checked with a Random object that has a fixed seed and
     * should have deterministic results.
     */
    @Test
    void testLocaleStringRandom() {
        // Check that we get the same locale when using pseudorandom number generator with a fixed seed
        final long fixedSeed = 5;

        Random random1 = new Random(fixedSeed);
        String randomLocale1 = locality.localeStringWithRandom(random1);

        Random random2 = new Random(fixedSeed);
        String randomLocale2 = locality.localeStringWithRandom(random2);

        assertThat(randomLocale1).isEqualTo(randomLocale2);
    }

    /**
     * Test to check Locality's localeStringWithRandom method. It verifies that the randomly selected
     * locale is within the set of all supported locales
     */
    @RepeatedTest(100)
    void testLocaleStringWithRandom() {
        Random random = new Random();
        String randomLocale = locality.localeStringWithRandom(random);
        assertThat(allLocales).contains(randomLocale);
    }

    @Test
    void testLocaleStringWithoutReplacement() {
        Random random = new Random();
        // loop through all supported locales
        for (int i = 0; i < 2; i++) {
            Set<String> returnedLocales = IntStream.range(0, allLocales.size())
                .mapToObj(j -> locality.localeStringWithoutReplacement(random))
                .collect(Collectors.toSet());

            assertThat(allLocales).containsAll(returnedLocales);
        }
    }

    @Test
    void testLocaleString() {
        assertThat(allLocales).contains(locality.localeString());
    }

    @Test
    void testLocaleWithoutReplacement() {
        assertThat(locality.localeStringWithoutReplacement()).isNotNull();
    }

}
