package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;


class MassEffectTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.massEffect().character()).isNotEmpty();
    }

    @Test
    void species() {
        assertThat(faker.massEffect().species()).isNotEmpty();
    }

    @Test
    void cluster() {
        assertThat(faker.massEffect().cluster()).isNotEmpty();
    }

    @Test
    void planets() {
        assertThat(faker.massEffect().planets()).isNotEmpty();
    }
}
