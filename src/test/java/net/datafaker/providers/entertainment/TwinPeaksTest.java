package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;


class TwinPeaksTest extends EntertainmentFakerTest {

    private final TwinPeaks twinPeaks = getFaker().twinPeaks();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(twinPeaks::character, "twin_peaks.characters"),
            TestSpec.of(twinPeaks::location, "twin_peaks.locations"),
            TestSpec.of(twinPeaks::quote, "twin_peaks.quotes")
        );
    }
}
