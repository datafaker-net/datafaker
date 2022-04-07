package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketballTest extends AbstractFakerTest {

    @Test
    public void testPositions() {
        assertThat(faker.basketball().positions()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    public void testTeams() {
        assertThat(faker.basketball().teams()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    public void testCoaches() {
        assertThat(faker.basketball().coaches()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    public void testPlayers() {
        assertThat(faker.basketball().players()).matches("[\\p{L}'()., 0-9-’]+");
    }
}
