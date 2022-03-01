package net.datafaker;

/**
 * @since 0.8.0
 */
public class TwinPeaks {
    private final Faker faker;

    protected TwinPeaks(final Faker faker) {
        this.faker = faker;
    }

    public String character() {
        return faker.resolve("twin_peaks.characters");
    }

    public String location() {
        return faker.resolve("twin_peaks.locations");
    }

    public String quote() {
        return faker.resolve("twin_peaks.quotes");
    }
}
