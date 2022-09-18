package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SuperMarioTest extends AbstractBaseFakerTest {

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
