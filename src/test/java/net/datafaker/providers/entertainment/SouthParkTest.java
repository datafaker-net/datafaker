package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class SouthParkTest extends EntertainmentFakerTest {

    private final SouthPark southPark = getFaker().southPark();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(southPark::characters, "south_park.characters"),
            TestSpec.of(southPark::quotes, "south_park.quotes")
        );
    }
}
