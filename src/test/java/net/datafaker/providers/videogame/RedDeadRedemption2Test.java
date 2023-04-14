package net.datafaker.providers.videogame;

import java.util.Collection;
import java.util.List;

public class RedDeadRedemption2Test extends VideoGameFakerTest {

    private final RedDeadRedemption2 redDeadRedemption2 = getFaker().redDeadRedemption2();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(redDeadRedemption2::protagonist, "red_dead_redemption2.protagonists"),
            TestSpec.of(redDeadRedemption2::gangMember, "red_dead_redemption2.gang_members"),
            TestSpec.of(redDeadRedemption2::majorCharacter, "red_dead_redemption2.major_characters"),
            TestSpec.of(redDeadRedemption2::animal, "red_dead_redemption2.animals"),
            TestSpec.of(redDeadRedemption2::state, "red_dead_redemption2.states"),
            TestSpec.of(redDeadRedemption2::region, "red_dead_redemption2.regions"),
            TestSpec.of(redDeadRedemption2::settlement, "red_dead_redemption2.settlements"),
            TestSpec.of(redDeadRedemption2::quote, "red_dead_redemption2.quotes"),
            TestSpec.of(redDeadRedemption2::weapon, "red_dead_redemption2.weapons")
        );
    }
}
