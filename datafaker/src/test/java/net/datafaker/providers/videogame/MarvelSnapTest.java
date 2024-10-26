package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

public class MarvelSnapTest extends VideoGameFakerTest {

    private final MarvelSnap marvelSnap = getFaker().marvelSnap();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(marvelSnap::character, "games.marvel_snap.characters"),
            TestSpec.of(marvelSnap::event, "games.marvel_snap.events"),
            TestSpec.of(marvelSnap::rank, "games.marvel_snap.rank"),
            TestSpec.of(marvelSnap::zone, "games.marvel_snap.zones")
        );
    }
}
