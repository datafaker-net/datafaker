package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class VForVendettaTest extends EntertainmentFakerTest {

    private final VForVendetta vForVendetta = getFaker().vForVendetta();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(vForVendetta::characters, "v_for_vendetta.characters"),
            TestSpec.of(vForVendetta::quotes, "v_for_vendetta.quotes"),
            TestSpec.of(vForVendetta::speeches, "v_for_vendetta.speeches")
        );
    }
}
