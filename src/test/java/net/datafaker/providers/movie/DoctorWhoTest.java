package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoctorWhoTest extends MovieFakerTest {

    @Test
    void character() {
        assertThat(faker.doctorWho().character()).isNotEmpty();
    }

    @Test
    void doctor() {
        assertThat(faker.doctorWho().doctor()).isNotEmpty();
    }

    @Test
    void actor() {
        assertThat(faker.doctorWho().actor()).isNotEmpty();
    }

    @Test
    void catchPhrase() {
        assertThat(faker.doctorWho().catchPhrase()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.doctorWho().quote()).isNotEmpty();
    }

    @Test
    void villain() {
        assertThat(faker.doctorWho().villain()).isNotEmpty();
    }

    @Test
    void species() {
        assertThat(faker.doctorWho().species()).isNotEmpty();
    }
}
