package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class EntertainmentTest extends EntertainmentFakerTest {

    private final Show show = getFaker().show();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(show::adultMusical, "show.adult_musical"),
            TestSpec.of(show::play, "show.play"),
            TestSpec.of(show::kidsMusical, "show.kids_musical")
        );
    }
}
