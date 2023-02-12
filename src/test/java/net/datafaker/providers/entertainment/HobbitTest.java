package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


class HobbitTest extends EntertainmentFakerTest {

    private final Hobbit hobbit = getFaker().hobbit();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(hobbit::character, "hobbit.character"),
            TestSpec.of(hobbit::location, "hobbit.location"),
            TestSpec.of(hobbit::thorinsCompany, "hobbit.thorins_company"),
            TestSpec.of(hobbit::quote, "hobbit.quote")
        );
    }
}
