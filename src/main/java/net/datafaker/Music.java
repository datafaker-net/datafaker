package net.datafaker;

import net.datafaker.core.Faker;

/**
 * @since 0.8.0
 */
public class Music {

    private static final String[] KEYS = new String[]{"C", "D", "E", "F", "G", "A", "B"};
    private static final String[] KEY_VARIANTS = new String[]{"b", "#", ""};
    private static final String[] CHORD_TYPES = new String[]{"", "maj", "6", "maj7", "m", "m7", "-7", "7", "dom7", "dim", "dim7", "m7b5"};

    private final Faker faker;
    private final Options options;

    public Music(Faker faker) {
        this.faker = faker;
        this.options = faker.getProvider(Options.class, () -> new Options(faker));
    }

    public String instrument() {
        return faker.resolve("music.instruments");
    }

    public String key() {
        return options.option(KEYS) + options.option(KEY_VARIANTS);
    }

    public String chord() {
        return key() + options.option(CHORD_TYPES);
    }

    public String genre() {
        return faker.resolve("music.genres");
    }
}
