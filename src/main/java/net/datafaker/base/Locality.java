package net.datafaker.base;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Locale;
import java.io.File;
import java.util.Collections;

public class Locality extends AbstractProvider<BaseProviders> {

    private final static String resourcePath = "./src/main/resources";
    private final List<String> locales;
    private List<String> shuffledLocales = new ArrayList<>();

    /**
     * Constructor for Locality class
     */
    public Locality(BaseProviders baseProviders) {
        super(baseProviders);
        this.locales = allSupportedLocales();
    }

    /**
     * Retrieves list of all locales supported by Datafaker
     *
     * @return a List of Strings with the name of the locale (eg. "es", "es-MX")
     */
    public List<String> allSupportedLocales() {

        // Retrieve list of all supported locale based on files in "resources" folder
        List<String> locales = new ArrayList<>();

        String[] resourceFiles = new File(resourcePath).list();

        int numResourceFiles = 0;
        if (resourceFiles != null) {
            numResourceFiles = resourceFiles.length;
        }

        for (int i = 0; i < numResourceFiles; i++) {
            String resourceFileName = resourceFiles[i];
            if (resourceFileName.endsWith(".yml")) {
                String localeName = resourceFileName.substring(0, resourceFileName.lastIndexOf('.'));
                locales.add(localeName);
            }
        }

        return locales;
    }

    /**
     * Select a locale at random and returns display name of the locale
     * @return locale in the form: "English (United States) or English"
     */
    public String displayName() {
        int randomIndex = faker.random().nextInt(locales.size());
        Locale locale = Locale.forLanguageTag(locales.get(randomIndex));

        String displayLanguage = locale.getDisplayLanguage(Locale.ENGLISH);
        String displayCountry = locale.getDisplayCountry(Locale.ENGLISH);
        if (!displayCountry.isEmpty()) {
            displayLanguage += " (" + displayCountry + ")";
        }
        return displayLanguage;
    }

    /**
     * @return Randomly selected locale (eg. "es", "es-MX").
     * Locale is selected at random WITH replacement from all supported locales
     */
    public String localeString() {
        return localeStringWithRandom(faker.random().getRandomInternal());
    }

    /**
     * Select a locale at random with replacement
     *
     * @param random random number generator (can utilize seed for deterministic random selection)
     * @return String of a randomly selected locale (eg. "es", "es-MX")
     */
    public String localeStringWithRandom(Random random) {

        // Randomly select a locale from list of all locales supported
        int randomIndex = random.nextInt(locales.size());
        return locales.get(randomIndex);
    }

    /**
     * @return Randomly selected locale (eg. "es", "es-MX").
     * Locale is selected at random WITHOUT replacement from all supported locales
     */
    public String localeStringWithoutReplacement() {
        return localeStringWithoutReplacement(faker.random().getRandomInternal());
    }

    /**
     * Select a locale at random without replacement. This can be used to rotate through all supported locales
     *
     * @param random random number generator (can utilize seed for deterministic random selection)
     * @return String of a randomly selected locale (eg. "es", "es-MX")
     */
    public String localeStringWithoutReplacement(Random random) {
        if (this.shuffledLocales.isEmpty()) {
            // copy list of locales supported into shuffledLocales
            shuffledLocales = new ArrayList<>(this.locales);
            Collections.shuffle(shuffledLocales, random);
        }

        // retrieve next locale in shuffledLocales and remove from list
        String pickedLocale = shuffledLocales.get(0);
        shuffledLocales.remove(0);

        return pickedLocale;
    }

}
