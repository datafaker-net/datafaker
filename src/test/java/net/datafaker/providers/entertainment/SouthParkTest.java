package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class SouthParkTest extends EntertainmentFakerTest {

    private final SouthPark southPark = getFaker().southPark();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(southPark::characters, "south_park.characters"),
            TestSpec.of(southPark::quotes, "south_park.quotes")
        );
    }
}
