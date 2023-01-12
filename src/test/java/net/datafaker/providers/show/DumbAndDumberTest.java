package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DumbAndDumberTest extends ShowFakerTest {

    @Test
    void actor() {
        assertThat(faker.dumbAndDumber().actor()).isNotEmpty();
    }

    @Test
    void character() {
        assertThat(faker.dumbAndDumber().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.dumbAndDumber().quote()).isNotEmpty();
    }
}
