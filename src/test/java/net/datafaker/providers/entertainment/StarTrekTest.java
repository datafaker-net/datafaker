package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


class StarTrekTest extends EntertainmentFakerTest {

    private final StarTrek starTrek = getFaker().starTrek();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(starTrek::character, "star_trek.character"),
            TestSpec.of(starTrek::location, "star_trek.location"),
            TestSpec.of(starTrek::klingon, "star_trek.klingon"),
            TestSpec.of(starTrek::species, "star_trek.species"),
            TestSpec.of(starTrek::villain, "star_trek.villain"),
            TestSpec.of(starTrek::starship, "star_trek.starship")
        );
    }
}
