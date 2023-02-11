package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class DragonBallTest extends EntertainmentFakerTest {

    private final DragonBall dragonBall = getFaker().dragonBall();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(dragonBall::character, "dragon_ball.characters")
        );
    }
}
