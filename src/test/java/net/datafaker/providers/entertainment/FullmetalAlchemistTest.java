package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

public class FullmetalAlchemistTest extends EntertainmentFakerTest {

    private final FullmetalAlchemist fullmetalAlchemist = getFaker().fullMetalAlchemist();
    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(fullmetalAlchemist::character, "fma_brotherhood.characters"),
            TestSpec.of(fullmetalAlchemist::city, "fma_brotherhood.cities"),
            TestSpec.of(fullmetalAlchemist::country, "fma_brotherhood.countries")
        );
    }
}
