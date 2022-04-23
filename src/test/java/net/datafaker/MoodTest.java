package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoodTest extends AbstractFakerTest {

    @Test
    public void feeling() {
        assertThat(faker.mood().feeling()).matches("[a-zA-Z]+");
    }

    @Test
    public void emotion() {
        assertThat(faker.mood().emotion()).matches("[a-zA-Z]+");
    }

    @Test
    public void tone() {
        assertThat(faker.mood().tone()).matches("[a-zA-Z]+");
    }

}
