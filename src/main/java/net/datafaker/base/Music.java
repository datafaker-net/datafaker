package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Music extends AbstractProvider<BaseProviders> {

    private static final String[] KEYS = new String[]{"C", "D", "E", "F", "G", "A", "B"};
    private static final String[] KEY_VARIANTS = new String[]{"b", "#", ""};
    private static final String[] CHORD_TYPES = new String[]{"", "maj", "6", "maj7", "m", "m7", "-7", "7", "dom7", "dim", "dim7", "m7b5"};

    protected Music(BaseFaker faker) {
        super(faker);
    }

    public String instrument() {
        return resolve("music.instruments");
    }

    public String key() {
        return faker.options().option(KEYS) + faker.options().option(KEY_VARIANTS);
    }

    public String chord() {
        return key() + faker.options().option(CHORD_TYPES);
    }

    public String genre() {
        return resolve("music.genres");
    }
}
