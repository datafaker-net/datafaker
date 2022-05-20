package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SuperMarioTest extends AbstractFakerTest {

    @Test
    void characters() {
        assertThat(faker.superMario().characters()).isNotEmpty();
    }

    @Test
    void games() {
        assertThat(faker.superMario().games()).isNotEmpty();
    }

    @Test
    void locations() {
        assertThat(faker.superMario().locations()).isNotEmpty();
    }

}
