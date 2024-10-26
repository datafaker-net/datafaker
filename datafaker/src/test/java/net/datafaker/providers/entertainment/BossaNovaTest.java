package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class BossaNovaTest extends EntertainmentFakerTest {

    private final BossaNova bossaNova = getFaker().bossaNova();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(bossaNova::artist, "bossa_nova.artists"),
            TestSpec.of(bossaNova::song, "bossa_nova.songs"));
    }
}
