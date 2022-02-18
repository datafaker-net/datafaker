package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class ZeldaTest extends AbstractFakerTest {

    @Test
    public void game() {
        assertThat(faker.zelda().game(), matchesRegularExpression("[A-Za-z'\\- :]+"));
    }

    @Test
    public void character() {
        assertThat(faker.zelda().character(), matchesRegularExpression("(?U)([\\w'\\-.\\(\\)]+ ?)+"));
    }
}
