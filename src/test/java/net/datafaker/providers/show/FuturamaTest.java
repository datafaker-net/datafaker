package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FuturamaTest extends ShowFakerTest {

    @Test
    void character() {
        assertThat(faker.futurama().character()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.futurama().location()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.futurama().quote()).isNotEmpty();
    }

    @Test
    void hermesCatchPhrase() {
        assertThat(faker.futurama().hermesCatchPhrase()).isNotEmpty();
    }
}
