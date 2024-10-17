package net.datafaker.providers.base;

import net.datafaker.annotations.Deterministic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

/**
 * Generates random locales in different forms.
 *
 * @since 1.7.0
 */
public class Locality extends AbstractProvider<BaseProviders> {
    private static final List<String> LOCALES = List.of(
        "_al", "_bg", "_by", "_ca", "_ch", "_cn", "_cz", "_ee", "_ge", "_in", "_md", "_mk", "_ru", "_us",
        "ar", "be", "bg", "by", "ca", "ca-cat", "cs", "cs-cz",
        "da-dk", "de", "de-at", "de-ch",
        "el-gr", "en", "en-au", "en-au-ocker", "en-bork", "en-ca", "en-gb", "en-in", "en-md", "en-ms", "en-nep",
        "en-ng", "en-nz", "en-pak", "en-ph", "en-pk", "en-sg", "en-ug", "en-us", "en-za",
        "es", "es-ar", "es-mx", "es-py", "et", "fa", "fi-fi", "fr", "fr-ca", "fr-ch",
        "he", "hr", "hu", "hy", "id", "id-id", "it", "ja", "ka", "ko", "lv", "mk",
        "nb-no", "nl", "nl-be", "no-no", "pl", "pt", "pt-br",
        "ro-md", "ru", "ru-md", "sk", "sq", "sv", "sv-se",
        "ta", "ta-in", "th", "tr", "uk", "vi", "zh-cn", "zh-tw"
    );

    private final List<String> shuffledLocales = new ArrayList<>();
    private int shuffledLocaleIndex = 0;

    /**
     * Constructor for Locality class
     */
    public Locality(BaseProviders baseProviders) {
        super(baseProviders);
    }

    /**
     * @param fileMasks is not used anymore
     * @deprecated Use {{@link #allSupportedLocales()}} instead
     */
    @Deprecated
    @SuppressWarnings("unused")
    public List<String> allSupportedLocales(Set<String> fileMasks) {
        return allSupportedLocales();
    }

    /**
     * Retrieves list of all locales supported by Datafaker
     *
     * @return a List of Strings with the name of the locale (e.g. "es", "es-MX")
     */
    @Deterministic
    public final List<String> allSupportedLocales() {
        return LOCALES;
    }

    /**
     * Select a locale at random and returns display name of the locale
     *
     * @return locale in the form: "English (United States) or English"
     */
    public String displayName() {
        int randomIndex = faker.random().nextInt(LOCALES.size());
        Locale locale = Locale.forLanguageTag(LOCALES.get(randomIndex));

        String displayLanguage = locale.getDisplayLanguage(Locale.ROOT);
        String displayCountry = locale.getDisplayCountry(Locale.ROOT);
        if (!displayCountry.isEmpty()) {
            displayLanguage += " (" + displayCountry + ")";
        }

        return displayLanguage.isEmpty() ? Locale.ENGLISH.getDisplayLanguage(Locale.ROOT) : displayLanguage;
    }

    /**
     * @return Randomly selected locale (e.g. "es", "es-MX").
     * Locale is selected at random WITH replacement from all supported locales
     */
    public String localeString() {
        return localeStringWithRandom(faker.random().getRandomInternal());
    }

    /**
     * Select a locale at random with replacement
     *
     * @param random random number generator (can utilize seed for deterministic random selection)
     * @return String of a randomly selected locale (e.g. "es", "es-MX")
     */
    public String localeStringWithRandom(Random random) {

        // Randomly select a locale from list of all locales supported
        int randomIndex = random.nextInt(LOCALES.size());
        return LOCALES.get(randomIndex);
    }

    /**
     * @return Randomly selected locale (e.g. "es", "es-MX").
     * Locale is selected at random WITHOUT replacement from all supported locales
     */
    public String localeStringWithoutReplacement() {
        return localeStringWithoutReplacement(faker.random().getRandomInternal());
    }

    /**
     * Select a locale at random without replacement. This can be used to rotate through all supported locales
     *
     * @param random random number generator (can utilize seed for deterministic random selection)
     * @return String of a randomly selected locale (e.g. "es", "es-MX")
     */
    public synchronized String localeStringWithoutReplacement(Random random) {
        if (shuffledLocales.isEmpty() || shuffledLocaleIndex >= shuffledLocales.size() - 1) {
            // copy list of locales supported into shuffledLocales
            shuffledLocales.clear();
            shuffledLocales.addAll(LOCALES);
            shuffledLocaleIndex = 0;
            Collections.shuffle(shuffledLocales, random);
        }

        // retrieve next locale in shuffledLocales and increase the index
        return shuffledLocales.get(shuffledLocaleIndex++);
    }

}
