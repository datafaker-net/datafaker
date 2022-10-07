package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BrooklynNineNineTest extends MovieFakerTest {

    @Test
    void characters() {
        assertThat(faker.brooklynNineNine().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.brooklynNineNine().quotes()).isNotEmpty();
    }
}
