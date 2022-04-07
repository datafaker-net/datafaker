package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class BojackHorsemanTest extends AbstractFakerTest {

    @Test
    public void testCharacters1() {
        assertThat(faker.bojackHorseman().characters()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    public void testQuotes1() {
        assertFalse(faker.bojackHorseman().quotes().isEmpty());
    }

    @Test
    public void testTongueTwisters1() {
        assertFalse(faker.bojackHorseman().tongueTwisters().isEmpty());
    }
}
