package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeinfeldTest extends MovieFakerTest {

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
