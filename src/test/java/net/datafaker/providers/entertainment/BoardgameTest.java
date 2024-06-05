package net.datafaker.providers.entertainment;

import java.util.Collection;
import java.util.List;

class BoardgameTest extends EntertainmentFakerTest {

    private final Boardgame boardgame = faker.boardgame();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(boardgame::name, "boardgame.name"),
            TestSpec.of(boardgame::category, "boardgame.category"),
            TestSpec.of(boardgame::mechanic, "boardgame.mechanic"),
            TestSpec.of(boardgame::subdomain, "boardgame.subdomain"),
            TestSpec.of(boardgame::designer, "boardgame.designer"),
            TestSpec.of(boardgame::artist, "boardgame.artist"),
            TestSpec.of(boardgame::publisher, "boardgame.publisher")
        );
    }
}
