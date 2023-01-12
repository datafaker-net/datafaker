package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class Babylon5Test extends ShowFakerTest {

    @Test
    void character() {
        assertThat(faker.babylon5().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.babylon5().quote()).isNotEmpty();
    }
}
