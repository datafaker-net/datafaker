package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FreshPrinceOfBelAirTest extends net.datafaker.AbstractFakerTest {

    @Test
    void characters() {
        assertThat(faker.freshPrinceOfBelAir().characters()).isNotEmpty();
    }

    @Test
    void celebrities() {
        assertThat(faker.freshPrinceOfBelAir().celebrities()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.freshPrinceOfBelAir().quotes()).isNotEmpty();
    }

}

