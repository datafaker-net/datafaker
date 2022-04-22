package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BojackHorsemanTest extends AbstractFakerTest {

    @Test
    public void testCharacters1() {
        assertThat(faker.bojackHorseman().characters()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    public void testQuotes1() {
        assertThat(faker.bojackHorseman().quotes()).isNotEmpty();
    }

    @Test
    public void testTongueTwisters1() {
        assertThat(faker.bojackHorseman().tongueTwisters()).isNotEmpty();
    }
}
