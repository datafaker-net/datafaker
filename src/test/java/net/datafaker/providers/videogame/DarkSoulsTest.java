package net.datafaker.providers.videogame;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class DarkSoulsTest extends VideoGameFakerTest {

    public static final String DARK_SOUL_REGEX = "[A-Za-z '-]+";

    @RepeatedTest(10)
    void testClasses() {
        assertThat(faker.darkSouls().classes()).matches(DARK_SOUL_REGEX);
    }

    @RepeatedTest(10)
    void testCovenants() {
        assertThat(faker.darkSouls().covenants()).matches(DARK_SOUL_REGEX);
    }

    @RepeatedTest(10)
    void testShield() {
        assertThat(faker.darkSouls().shield()).matches(DARK_SOUL_REGEX);
    }

    @RepeatedTest(10)
    void testStats() {
        assertThat(faker.darkSouls().stats()).matches(DARK_SOUL_REGEX);
    }
}
