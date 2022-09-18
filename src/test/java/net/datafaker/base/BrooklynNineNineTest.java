package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BrooklynNineNineTest extends AbstractBaseFakerTest {

    @Test
    void characters() {
        assertThat(faker.brooklynNineNine().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.brooklynNineNine().quotes()).isNotEmpty();
    }
}
