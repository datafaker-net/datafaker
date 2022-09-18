package net.datafaker;

import net.datafaker.sport.AbstractSportFakerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasketballTest extends AbstractSportFakerTest {

    @Test
    void testPositions() {
        assertThat(faker.basketball().positions()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testTeams() {
        assertThat(faker.basketball().teams()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testCoaches() {
        assertThat(faker.basketball().coaches()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testPlayers() {
        assertThat(faker.basketball().players()).matches("[\\p{L}'()., 0-9-’]+");
    }
}
