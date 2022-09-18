package net.datafaker.videogame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class SuperMarioTest extends VideoGameFakerTest {

    @Test
    void characters() {
        Assertions.assertThat(faker.superMario().characters()).isNotEmpty();
    }

    @Test
    void games() {
        Assertions.assertThat(faker.superMario().games()).isNotEmpty();
    }

    @Test
    void locations() {
        Assertions.assertThat(faker.superMario().locations()).isNotEmpty();
    }

}
