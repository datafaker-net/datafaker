package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class ResidentEvilTest extends EntertainmentFakerTest {

    private final ResidentEvil residentEvil = getFaker().residentEvil();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(residentEvil::biologicalAgent, "games.resident_evil.biological-agents"),
            TestSpec.of(residentEvil::character, "games.resident_evil.characters"),
            TestSpec.of(residentEvil::creature, "games.resident_evil.creatures"),
            TestSpec.of(residentEvil::equipment, "games.resident_evil.equipments"),
            TestSpec.of(residentEvil::location, "games.resident_evil.locations")
        );
    }
}
