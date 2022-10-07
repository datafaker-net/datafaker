package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class Babylon5Test extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.babylon5().character()).isNotEmpty();
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.babylon5().quote()).isNotEmpty();
    }
}
