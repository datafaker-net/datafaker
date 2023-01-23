package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudioGhibliTest extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.studioGhibli().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.studioGhibli().quote()).isNotEmpty();
    }

    @Test
    void movie() {
        assertThat(faker.studioGhibli().movie()).isNotEmpty();
    }
}
