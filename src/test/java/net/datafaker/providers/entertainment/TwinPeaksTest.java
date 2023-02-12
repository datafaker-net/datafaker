package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


class TwinPeaksTest extends EntertainmentFakerTest {

    private final TwinPeaks twinPeaks = getFaker().twinPeaks();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(twinPeaks::character, "twin_peaks.characters"),
            TestSpec.of(twinPeaks::location, "twin_peaks.locations"),
            TestSpec.of(twinPeaks::quote, "twin_peaks.quotes")
        );
    }
}
