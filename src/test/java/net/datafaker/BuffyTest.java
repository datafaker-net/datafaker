package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuffyTest extends AbstractFakerTest {
    @Test
    public void testCharacters() {
        assertThat(faker.buffy().characters()).isNotEmpty();
    }

    @Test
    public void testQuotes() {
        assertThat(faker.buffy().quotes()).isNotEmpty();
    }

    @Test
    public void testCelebrities() {
        assertThat(faker.buffy().celebrities()).isNotEmpty();
    }

    @Test
    public void testBigBads() {
        assertThat(faker.buffy().bigBads()).isNotEmpty();
    }

    @Test
    public void testEpisodes() {
        assertThat(faker.buffy().episodes()).isNotEmpty();
    }
}
