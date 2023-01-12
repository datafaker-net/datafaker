package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WarhammerFantasyTest extends net.datafaker.AbstractFakerTest {

    @Test
    void heros() {
        assertThat(faker.warhammerFantasy().heros()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.warhammerFantasy().quotes()).isNotEmpty();
    }

    @Test
    void locations() {
        assertThat(faker.warhammerFantasy().locations()).isNotEmpty();
    }

    @Test
    void factions() {
        assertThat(faker.warhammerFantasy().factions()).isNotEmpty();
    }

    @Test
    void creatures() {
        assertThat(faker.warhammerFantasy().creatures()).isNotEmpty();
    }

}
