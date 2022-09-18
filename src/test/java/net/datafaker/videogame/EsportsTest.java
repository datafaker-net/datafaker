package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class EsportsTest extends VideoGameFakerTest {

    @Test
    void player() {
        Assertions.assertThat(faker.esports().player()).matches("(\\w|.)+");
    }

    @Test
    void team() {
        Assertions.assertThat(faker.esports().team()).matches("((\\w|.)+ ?)+");
    }

    @Test
    void event() {
        Assertions.assertThat(faker.esports().event()).matches("(\\w+ ?)+");
    }

    @Test
    void league() {
        Assertions.assertThat(faker.esports().league()).matches("\\w+");
    }

    @Test
    void game() {
        Assertions.assertThat(faker.esports().game()).matches("([\\w:.]+ ?)+");
    }
}
