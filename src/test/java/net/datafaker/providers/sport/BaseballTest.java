package net.datafaker.providers.sport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseballTest extends SportFakerTest {

    @Test
    void testTeams() {
        assertThat(faker.baseball().teams()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testCoaches() {
        assertThat(faker.baseball().coaches()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testPositions() {
        assertThat(faker.baseball().positions()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testPlayers() {
        assertThat(faker.baseball().players()).matches("[\\p{L}'()., 0-9-’]+");
    }
}
