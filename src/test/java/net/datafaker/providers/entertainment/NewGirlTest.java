package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class NewGirlTest extends EntertainmentFakerTest {

    private final NewGirl newGirl = getFaker().newGirl();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(newGirl::characters, "new_girl.characters"),
            TestSpec.of(newGirl::quotes, "new_girl.quotes")
        );
    }
}
