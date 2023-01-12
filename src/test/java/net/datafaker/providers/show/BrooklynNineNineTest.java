package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BrooklynNineNineTest extends ShowFakerTest {

    @Test
    void characters() {
        assertThat(faker.brooklynNineNine().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.brooklynNineNine().quotes()).isNotEmpty();
    }
}
