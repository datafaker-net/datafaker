package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class NarutoTest extends MovieFakerTest {

    @Test
    void character() {
        assertThat(faker.naruto().character()).isNotEmpty();
    }

    @Test
    void village() {
        assertThat(faker.naruto().village()).isNotEmpty();
    }

    @Test
    void eye() {
        assertThat(faker.naruto().eye()).isNotEmpty();
    }

    @Test
    void demon() {
        assertThat(faker.naruto().demon()).isNotEmpty();
    }
}
