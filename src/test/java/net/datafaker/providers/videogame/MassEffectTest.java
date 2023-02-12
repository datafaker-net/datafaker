package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class MassEffectTest extends VideoGameFakerTest {

    private final MassEffect massEffect = getFaker().massEffect();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(massEffect::character, "mass_effect.characters"),
            TestSpec.of(massEffect::cluster, "mass_effect.cluster"),
            TestSpec.of(massEffect::planet, "mass_effect.planets"),
            TestSpec.of(massEffect::quote, "mass_effect.quotes"),
            TestSpec.of(massEffect::specie, "mass_effect.species")
        );
    }
}
