package net.datafaker.providers.videogame;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class MarvelSnapTest extends VideoGameFakerTest {

    @RepeatedTest(10)
    void charactersTest() {
        String character = faker.marvelSnap().character();
        assertThat(character).matches("[ A-Za-z']+");
    }

    @RepeatedTest(10)
    void zonesTest() {
        String zone = faker.marvelSnap().zone();
        assertThat(zone).matches("[- A-Za-z']+");
    }

    @RepeatedTest(10)
    void eventsTest() {
        String event = faker.marvelSnap().event();
        assertThat(event).matches("[ A-Za-z-]+");
    }

    @RepeatedTest(10)
    void rankTest() {
        String rank = faker.marvelSnap().rank();
        assertThat(rank).matches("[ A-Za-z]+");
    }
}
