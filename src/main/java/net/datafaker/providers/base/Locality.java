package net.datafaker.providers.base;

import net.datafaker.annotations.Deterministic;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    @Deterministic
    public List<String> allSupportedLocales() {
        return allSupportedLocales(Set.of("datafaker"));
    }

    private boolean addLocaleIfPresent(Path file, Set<String> langs, Set<String> locales) {
        final String filename = file.getFileName().toString().toLowerCase(Locale.ROOT);
        if ((filename.endsWith(".yml") || filename.endsWith(".yaml")) && Files.isRegularFile(file) && Files.isReadable(file)) {
            final String parentFileName = file.getParent().getFileName().toString();
            if (langs.contains(parentFileName)) {
                locales.add(parentFileName);
            } else {
                locales.add(filename.substring(0, filename.indexOf('.')));
            }
            return true;
        }
        return false;
    }

    public List<String> allSupportedLocales(Set<String> fileMasks) {
        Set<String> langs = Set.of(Locale.getISOLanguages());
        String[] paths = ManagementFactory.getRuntimeMXBean().getClassPath().split(File.pathSeparator);
        Set<String> locales = new HashSet<>();
        final SimpleFileVisitor<Path> simpleFileVisitor = new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getNameCount() > 2) {
                    return super.visitFile(file, attrs);
                }
                addLocaleIfPresent(file, langs, locales);
                return super.visitFile(file, attrs);
            }
        };

        final SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                final String filename = file.getFileName().toString().toLowerCase(Locale.ROOT);
                if (addLocaleIfPresent(file, langs, locales)) {
                    // do nothing, everything is done at addLocaleIfPresent
                } else if (filename.endsWith(".jar") && fileMasks.stream().anyMatch(filename::contains) && Files.isRegularFile(file) && Files.isReadable(file)) {

                    try (FileSystem jarfs = FileSystems.newFileSystem(file, (ClassLoader) null)) {
                        for (Path rootPath : jarfs.getRootDirectories()) {
                            Files.walkFileTree(rootPath, simpleFileVisitor);
                        }
                    }
                }
                return super.visitFile(file, attrs);
            }
        };
        for (String s: paths) {
            try {
                Files.walkFileTree(Paths.get(s).toAbsolutePath(), visitor);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<>(locales);
    }

    /**
     * Select a locale at random and returns display name of the locale
     *
     * @return locale in the form: "English (United States) or English"
     */
    public String displayName() {
        int randomIndex = faker.random().nextInt(locales.size());
        Locale locale = Locale.forLanguageTag(locales.get(randomIndex));

        String displayLanguage = locale.getDisplayLanguage(Locale.ROOT);
        String displayCountry = locale.getDisplayCountry(Locale.ROOT);
        if (!displayCountry.isEmpty()) {
            displayLanguage += " (" + displayCountry + ")";
        }

        return displayLanguage.isEmpty() ? Locale.ENGLISH.getDisplayLanguage(Locale.ROOT) : displayLanguage;
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
        String pickedLocale = shuffledLocales.get(shuffledLocales.size() - 1);
        shuffledLocales.remove(shuffledLocales.size() - 1);

        return pickedLocale;
    }

}
