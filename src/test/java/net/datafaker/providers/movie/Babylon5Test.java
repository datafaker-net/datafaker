package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Babylon5Test extends MovieFakerTest {

    @Test
    void character() {
        assertThat(faker.babylon5().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.babylon5().quote()).isNotEmpty();
    }
}
