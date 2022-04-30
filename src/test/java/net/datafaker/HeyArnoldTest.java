package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeyArnoldTest extends AbstractFakerTest {

    @Test
    void characters() {
        assertThat(faker.heyArnold().characters()).isNotEmpty();
    }

    @Test
    void locations() {
        assertThat(faker.heyArnold().locations()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.heyArnold().quotes()).isNotEmpty();
    }
}
