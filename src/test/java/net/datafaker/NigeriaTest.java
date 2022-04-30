package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NigeriaTest extends AbstractFakerTest {

    @Test
    void places() {
        assertThat(faker.nigeria().places()).isNotEmpty();
    }

    @Test
    void food() {
        assertThat(faker.nigeria().food()).isNotEmpty();
    }

    @Test
    void names() {
        assertThat(faker.nigeria().name()).isNotEmpty();
    }

    @Test
    void schools() {
        assertThat(faker.nigeria().schools()).isNotEmpty();
    }

    @Test
    void celebrities() {
        assertThat(faker.nigeria().celebrities()).isNotEmpty();
    }
}
