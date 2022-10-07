package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class FinalSpaceTest extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.finalSpace().character()).isNotEmpty();
    }

    @Test
    void vehicle() {
        Assertions.assertThat(faker.finalSpace().vehicle()).isNotEmpty();
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.finalSpace().quote()).isNotEmpty();
    }
}
