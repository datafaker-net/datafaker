package net.datafaker.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GratefulDeadTest extends MovieFakerTest {

    @Test
    void players() {
        assertThat(faker.gratefulDead().players()).isNotEmpty();
    }

    @Test
    void songs() {
        assertThat(faker.gratefulDead().songs()).isNotEmpty();
    }

}
