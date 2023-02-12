package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class SupernaturalTest extends EntertainmentFakerTest {

    private final Supernatural supernatural = getFaker().supernatural();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(supernatural::character, "supernatural.character"),
            TestSpec.of(supernatural::creature, "supernatural.creature"),
            TestSpec.of(supernatural::weapon, "supernatural.weapon")
        );
    }
}

