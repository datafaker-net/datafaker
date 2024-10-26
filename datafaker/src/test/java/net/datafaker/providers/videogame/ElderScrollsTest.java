

package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


class ElderScrollsTest extends VideoGameFakerTest {

    private final ElderScrolls elderScrolls = getFaker().elderScrolls();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(elderScrolls::city, "games.elder_scrolls.city"),
            TestSpec.of(elderScrolls::creature, "games.elder_scrolls.creature"),
            TestSpec.of(elderScrolls::dragon, "games.elder_scrolls.dragon"),
            TestSpec.of(elderScrolls::firstName, "games.elder_scrolls.first_name"),
            TestSpec.of(elderScrolls::lastName, "games.elder_scrolls.last_name"),
            TestSpec.of(elderScrolls::quote, "games.elder_scrolls.quote"),
            TestSpec.of(elderScrolls::race, "games.elder_scrolls.race"),
            TestSpec.of(elderScrolls::region, "games.elder_scrolls.region")
        );
    }
}
