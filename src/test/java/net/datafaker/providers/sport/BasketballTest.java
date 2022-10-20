package net.datafaker.providers.sport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasketballTest extends SportFakerTest {

    @Test
    void positions() {
        assertThat(faker.basketball().positions()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void teams() {
        assertThat(faker.basketball().teams()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void coaches() {
        assertThat(faker.basketball().coaches()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void players() {
        assertThat(faker.basketball().players()).matches("[\\p{L}'()., 0-9-’]+");
    }
}
