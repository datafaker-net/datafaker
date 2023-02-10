package net.datafaker.providers.videogame;

import java.util.Arrays;
import java.util.Collection;


class TouhouTest extends VideoGameFakerTest {

    private final Touhou touhou = getFaker().touhou();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(touhou::characterFirstName, "touhou.first_name"),
            TestSpec.of(touhou::characterLastName, "touhou.last_name"),
            TestSpec.of(touhou::characterName, "touhou.full_name"),
            TestSpec.of(touhou::gameName, "touhou.game_name"),
            TestSpec.of(touhou::trackName, "touhou.track_name")
        );
    }
}
