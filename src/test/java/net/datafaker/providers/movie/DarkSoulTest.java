package net.datafaker.providers.movie;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class DarkSoulTest extends MovieFakerTest {

    public static final String DARK_SOUL_REGEX = "[A-Za-z '-]+";

    @RepeatedTest(10)
    void classes() {
        assertThat(faker.darkSoul().classes()).matches(DARK_SOUL_REGEX);
    }

    @RepeatedTest(10)
    void covenants() {
        assertThat(faker.darkSoul().covenants()).matches(DARK_SOUL_REGEX);
    }

    @RepeatedTest(10)
    void shield() {
        assertThat(faker.darkSoul().shield()).matches(DARK_SOUL_REGEX);
    }

    @RepeatedTest(10)
    void stats() {
        assertThat(faker.darkSoul().stats()).matches(DARK_SOUL_REGEX);
    }
}
