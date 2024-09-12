package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Locale.ROOT;
import static org.assertj.core.api.Assertions.assertThat;

class LocalityTest extends BaseFakerTest<BaseFaker> {
    private final BaseFaker f = new Faker();
    private final Locality locality = f.locality();

    /**
     * Test to check that list of all locales support is loaded
     */
    @Test
    void allSupportedLocales() {
        // Check that directory of locale resources exists
        File resourceDirectory = new File("./src/main/resources");
        assertThat(resourceDirectory).exists();

        List<String> allLocales = locality.allSupportedLocales();
        assertThat(allLocales).hasSize(87);
        assertThat(allLocales)
            .as("Somebody forgot to add the new locale to Locality.LOCALES")
            .containsExactlyInAnyOrderElementsOf(findAllSupportedLocales(resourceDirectory));
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
    void localeStringRandom() {
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
    void localeStringWithRandom() {
        Random random = new Random();
        String randomLocale = locality.localeStringWithRandom(random);
        assertThat(locality.allSupportedLocales()).contains(randomLocale);
    }

    @Test
    void localeStringWithoutReplacement() {
        Random random = new Random();
        // loop through all supported locales
        for (int i = 0; i < 2; i++) {
            Set<String> returnedLocales = IntStream.range(0, locality.allSupportedLocales().size())
                .mapToObj(j -> locality.localeStringWithoutReplacement(random))
                .collect(Collectors.toSet());

            assertThat(locality.allSupportedLocales()).containsAll(returnedLocales);
        }
    }

    @Test
    void localeString() {
        assertThat(locality.allSupportedLocales()).contains(locality.localeString());
    }

    @Test
    void localeWithoutReplacement() {
        assertThat(locality.localeStringWithoutReplacement()).isNotNull();
    }

    private List<String> findAllSupportedLocales(File resourceDirectory) {
        File[] localeFiles = resourceDirectory.listFiles((dir, name) -> name.endsWith(".yml"));
        assert localeFiles != null;
        return Stream.of(localeFiles)
            .peek(f -> assertThat(f).isFile())
            .peek(f -> assertThat(f).isReadable())
            .map(f -> f.getName().toLowerCase(ROOT).replace(".yml", ""))
            .toList();
    }
}
