package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class SwordArtOnlineTest extends EntertainmentFakerTest {

    private final SwordArtOnline swordArtOnline = getFaker().swordArtOnline();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(swordArtOnline::gameName, "sword_art_online.game_name"),
            TestSpec.of(swordArtOnline::item, "sword_art_online.item"),
            TestSpec.of(swordArtOnline::location, "sword_art_online.location"),
            TestSpec.of(swordArtOnline::realName, "sword_art_online.real_name")
        );
    }
}
