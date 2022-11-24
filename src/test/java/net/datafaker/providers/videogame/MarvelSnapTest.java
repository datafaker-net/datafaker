package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarvelSnapTest extends VideoGameFakerTest {

    @Test
    void charactersTest() {
        String character = faker.marvelSnap().characters();
        assertThat(character).matches("[ A-Za-z']+");
    }

    @Test
    void zonesTest() {
        String zone = faker.marvelSnap().zones();
        assertThat(zone).matches("[ A-Za-z]+");
    }

    @Test
    void eventsTest() {
        String event = faker.marvelSnap().events();
        assertThat(event).matches("[ A-Za-z]+");
    }

    @Test
    void rankTest() {
        String rank = faker.marvelSnap().rank();
        assertThat(rank).matches("[ A-Za-z]+");
    }
}
