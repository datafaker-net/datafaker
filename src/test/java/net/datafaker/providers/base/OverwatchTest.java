package net.datafaker.providers.base;

import net.datafaker.providers.videogame.VideoGameFakerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class OverwatchTest extends VideoGameFakerTest {

    @Test
    void hero() {
        Assertions.assertThat(faker.overwatch().hero()).matches("^(\\w+\\.?\\s?)+$");
    }

    @Test
    void location() {
        Assertions.assertThat(faker.overwatch().location()).matches("^(.+'?:?\\s?)+$");
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.overwatch().quote()).isNotEmpty();
    }
}
