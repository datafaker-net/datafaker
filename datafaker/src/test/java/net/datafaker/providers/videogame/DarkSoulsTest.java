package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

class DarkSoulsTest extends VideoGameFakerTest {

    private final DarkSouls darkSouls = getFaker().darkSouls();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(darkSouls::classes, "dark_souls.classes"),
            TestSpec.of(darkSouls::covenants, "dark_souls.covenants"),
            TestSpec.of(darkSouls::shield, "dark_souls.shield"),
            TestSpec.of(darkSouls::stats, "dark_souls.stats")
        );
    }
}
