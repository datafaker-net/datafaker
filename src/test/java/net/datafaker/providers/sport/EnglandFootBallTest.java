package net.datafaker.providers.sport;


import java.util.Arrays;
import java.util.Collection;

class EnglandFootBallTest extends SportFakerTest {

    private final EnglandFootBall englandFootBall = getFaker().englandfootball();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(englandFootBall::league, "englandfootball.leagues"),
            TestSpec.of(englandFootBall::team, "englandfootball.teams")
        );
    }
}
