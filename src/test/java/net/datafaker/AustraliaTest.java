package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AustraliaTest extends AbstractFakerTest {

    @Test
    void locations() {
        assertThat(faker.australia().locations()).isNotEmpty();
    }

    @Test
    void animals() {
        assertThat(faker.australia().animals()).isNotEmpty();
    }

    @Test
    void states() {
        assertThat(faker.australia().states()).isNotEmpty();
    }
}
