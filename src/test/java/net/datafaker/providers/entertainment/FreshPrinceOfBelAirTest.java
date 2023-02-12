package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class FreshPrinceOfBelAirTest extends EntertainmentFakerTest {

    private final FreshPrinceOfBelAir freshPrinceOfBelAir = getFaker().freshPrinceOfBelAir();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(freshPrinceOfBelAir::characters, "fresh_prince_of_bel_air.characters"),
            TestSpec.of(freshPrinceOfBelAir::celebrities, "fresh_prince_of_bel_air.celebrities"),
            TestSpec.of(freshPrinceOfBelAir::quotes, "fresh_prince_of_bel_air.quotes")
        );
    }
}

