package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class TwinPeaks extends AbstractProvider<MovieProviders> {

    protected TwinPeaks(final MovieProviders faker) {
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
