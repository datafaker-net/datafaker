package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BrooklynNineNineTest extends AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.brooklynNineNine().characters()).isNotEmpty();
    }

    @Test
    public void quotes() {
        assertThat(faker.brooklynNineNine().quotes()).isNotEmpty();
    }
}
