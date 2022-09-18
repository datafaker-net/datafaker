package net.datafaker.sport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VolleyballTest extends SportFakerTest {

    @Test
    void team() {
        assertThat(faker.volleyball().team()).isNotEmpty();
    }

    @Test
    void player() {
        assertThat(faker.volleyball().player()).isNotEmpty();
    }

    @Test
    void coach() {
        assertThat(faker.volleyball().coach()).isNotEmpty();
    }

    @Test
    void position() {
        assertThat(faker.volleyball().position()).isNotEmpty();
    }

    @Test
    void formation() {
        assertThat(faker.volleyball().formation()).isNotEmpty();
    }

}
