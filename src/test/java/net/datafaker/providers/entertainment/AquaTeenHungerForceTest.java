package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class AquaTeenHungerForceTest extends EntertainmentFakerTest {

    private final AquaTeenHungerForce aquaTeenHungerForce = getFaker().aquaTeenHungerForce();
    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(aquaTeenHungerForce::character, "aqua_teen_hunger_force.character"));
    }
}
