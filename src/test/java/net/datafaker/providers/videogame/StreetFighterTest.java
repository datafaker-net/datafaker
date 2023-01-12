package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StreetFighterTest extends net.datafaker.AbstractFakerTest {

    @Test
    void characters() {
        assertThat(faker.streetFighter().characters()).isNotEmpty();
    }

    @Test
    void stages() {
        assertThat(faker.streetFighter().stages()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.streetFighter().quotes()).isNotEmpty();
    }

    @Test
    void moves() {
        assertThat(faker.streetFighter().moves()).isNotEmpty();
    }

}
