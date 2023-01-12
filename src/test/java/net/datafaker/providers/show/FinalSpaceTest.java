package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class FinalSpaceTest extends ShowFakerTest {

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
