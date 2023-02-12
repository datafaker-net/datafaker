package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;


public class SoulKnightTest extends VideoGameFakerTest {

    private final SoulKnight soulKnight = getFaker().soulKnight();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(soulKnight::bosses, "soul_knight.bosses"),
            TestSpec.of(soulKnight::buffs, "soul_knight.buffs"),
            TestSpec.of(soulKnight::enemies, "soul_knight.enemies"),
            TestSpec.of(soulKnight::statues, "soul_knight.statues"),
            TestSpec.of(soulKnight::weapons, "soul_knight.weapons")
        );
    }
}
