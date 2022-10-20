

package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ElderScrollsTest extends VideoGameFakerTest {

    @Test
    void city() {
        assertThat(faker.elderScrolls().city()).isNotEmpty();
    }

    @Test
    void creature() {
        assertThat(faker.elderScrolls().creature()).isNotEmpty();
    }

    @Test
    void dragon() {
        assertThat(faker.elderScrolls().dragon()).isNotEmpty();
    }

    @Test
    void firstName() {
        assertThat(faker.elderScrolls().firstName()).isNotEmpty();
    }

    @Test
    void lastName() {
        assertThat(faker.elderScrolls().lastName()).isNotEmpty();
    }

    @Test
    void race() {
        assertThat(faker.elderScrolls().race()).isNotEmpty();
    }

    @Test
    void region() {
        assertThat(faker.elderScrolls().region()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.elderScrolls().quote()).isNotEmpty();
    }
}
