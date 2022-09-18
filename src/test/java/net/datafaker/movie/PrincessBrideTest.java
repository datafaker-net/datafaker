package net.datafaker.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrincessBrideTest extends MovieFakerTest {
    @Test
    void character() {
        assertThat(faker.princessBride().character()).matches("[A-Za-z .-]+");
    }

    @Test
    void quote() {
        assertThat(faker.princessBride().quote()).isNotEmpty();
    }
}
