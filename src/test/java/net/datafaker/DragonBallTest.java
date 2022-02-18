package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class DragonBallTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.dragonBall().character(), matchesRegularExpression("^(\\w+\\.?\\s?-?)+$"));
    }
}
