package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Babylon5Test extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.babylon5().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.babylon5().quote()).isNotEmpty();
    }
}
