package net.datafaker;

/**
 * @since 0.8.0
 */
public class TwinPeaks extends AbstractProvider {

    protected TwinPeaks(final Faker faker) {
        super(faker);
    }

    public String character() {
        return resolve("twin_peaks.characters");
    }

    public String location() {
        return resolve("twin_peaks.locations");
    }

    public String quote() {
        return resolve("twin_peaks.quotes");
    }
}
