package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasketballTest extends AbstractFakerTest {

    @Test
    public void testPositions() {
        assertThat(faker.basketball().positions(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testTeams() {
        assertThat(faker.basketball().teams(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testCoaches() {
        assertThat(faker.basketball().coaches(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testPlayers() {
        assertThat(faker.basketball().players(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }
}
