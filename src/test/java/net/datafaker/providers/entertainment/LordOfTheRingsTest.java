package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
class LordOfTheRingsTest extends EntertainmentFakerTest {

    private final LordOfTheRings lordOfTheRings = getFaker().lordOfTheRings();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(lordOfTheRings::character, "lord_of_the_rings.characters"),
            TestSpec.of(lordOfTheRings::location, "lord_of_the_rings.locations")
        );
    }
}
