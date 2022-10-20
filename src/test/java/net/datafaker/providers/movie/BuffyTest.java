package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuffyTest extends MovieFakerTest {
    @Test
    void characters() {
        assertThat(faker.buffy().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.buffy().quotes()).isNotEmpty();
    }

    @Test
    void celebrities() {
        assertThat(faker.buffy().celebrities()).isNotEmpty();
    }

    @Test
    void bigBads() {
        assertThat(faker.buffy().bigBads()).isNotEmpty();
    }

    @Test
    void episodes() {
        assertThat(faker.buffy().episodes()).isNotEmpty();
    }
}
