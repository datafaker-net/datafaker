package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WorldOfWarcraftTest extends VideoGameFakerTest {

    @Test
    void hero() {
        assertThat(faker.worldOfWarcraft().hero()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.worldOfWarcraft().quotes()).isNotEmpty();
    }

}
