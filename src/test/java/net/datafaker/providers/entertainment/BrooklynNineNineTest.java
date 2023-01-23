package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BrooklynNineNineTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.brooklynNineNine().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.brooklynNineNine().quotes()).isNotEmpty();
    }
}
