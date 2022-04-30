package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoodTest extends AbstractFakerTest {

    @Test
    void feeling() {
        assertThat(faker.mood().feeling()).matches("[a-zA-Z]+");
    }

    @Test
    void emotion() {
        assertThat(faker.mood().emotion()).matches("[a-zA-Z]+");
    }

    @Test
    void tone() {
        assertThat(faker.mood().tone()).matches("[a-zA-Z]+");
    }

}
