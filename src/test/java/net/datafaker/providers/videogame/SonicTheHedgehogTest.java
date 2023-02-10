package net.datafaker.providers.videogame;

import java.util.Arrays;
import java.util.Collection;

class SonicTheHedgehogTest extends VideoGameFakerTest {

    private final SonicTheHedgehog sonicTheHedgehog = getFaker().sonicTheHedgehog();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(sonicTheHedgehog::character, "games.sonic_the_hedgehog.character"),
            TestSpec.of(sonicTheHedgehog::game, "games.sonic_the_hedgehog.game"),
            TestSpec.of(sonicTheHedgehog::zone, "games.sonic_the_hedgehog.zone")
        );
    }
}
