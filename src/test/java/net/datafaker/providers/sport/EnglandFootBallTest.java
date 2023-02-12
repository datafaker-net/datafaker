package net.datafaker.providers.sport;


import java.util.List;
import java.util.Collection;

class EnglandFootBallTest extends SportFakerTest {

    private final EnglandFootBall englandFootBall = getFaker().englandfootball();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(englandFootBall::league, "englandfootball.leagues"),
            TestSpec.of(englandFootBall::team, "englandfootball.teams")
        );
    }
}
