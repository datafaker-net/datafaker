package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeinfeldTest extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.seinfeld().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.seinfeld().quote()).isNotEmpty();
    }

    @Test
    void business() {
        assertThat(faker.seinfeld().business()).isNotEmpty();
    }

}
