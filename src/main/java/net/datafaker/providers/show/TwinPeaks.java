package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Twin Peaks is an American mystery serial drama television series created by Mark Frost and David Lynch.
 *
 * @since 0.8.0
 */
public class TwinPeaks extends AbstractProvider<ShowProviders> {

    protected TwinPeaks(final ShowProviders faker) {
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
