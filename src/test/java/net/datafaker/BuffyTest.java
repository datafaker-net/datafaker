package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuffyTest extends AbstractFakerTest {
    @Test
    void testCharacters() {
        assertThat(faker.buffy().characters()).isNotEmpty();
    }

    @Test
    void testQuotes() {
        assertThat(faker.buffy().quotes()).isNotEmpty();
    }

    @Test
    void testCelebrities() {
        assertThat(faker.buffy().celebrities()).isNotEmpty();
    }

    @Test
    void testBigBads() {
        assertThat(faker.buffy().bigBads()).isNotEmpty();
    }

    @Test
    void testEpisodes() {
        assertThat(faker.buffy().episodes()).isNotEmpty();
    }
}
