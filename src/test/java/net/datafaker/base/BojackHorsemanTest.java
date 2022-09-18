package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BojackHorsemanTest extends AbstractBaseFakerTest {

    @Test
    void testCharacters1() {
        assertThat(faker.bojackHorseman().characters()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testQuotes1() {
        assertThat(faker.bojackHorseman().quotes()).isNotEmpty();
    }

    @Test
    void testTongueTwisters1() {
        assertThat(faker.bojackHorseman().tongueTwisters()).isNotEmpty();
    }
}
