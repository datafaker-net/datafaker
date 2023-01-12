package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MystTest extends net.datafaker.AbstractFakerTest {

    @Test
    void games() {
        assertThat(faker.myst().games()).isNotEmpty();
    }

    @Test
    void creatures() {
        assertThat(faker.myst().creatures()).isNotEmpty();
    }

    @Test
    void characters() {
        assertThat(faker.myst().characters()).isNotEmpty();
    }

    @Test
    void ages() {
        assertThat(faker.myst().ages()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.myst().quotes()).isNotEmpty();
    }

}
