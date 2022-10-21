package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class FinalSpaceTest extends MovieFakerTest {

    @Test
    void character() {
        assertThat(faker.finalSpace().character()).isNotEmpty();
    }

    @Test
    void vehicle() {
        assertThat(faker.finalSpace().vehicle()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.finalSpace().quote()).isNotEmpty();
    }
}
