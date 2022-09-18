package net.datafaker.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class StarWarsTest extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.starWars().character()).isNotEmpty();
    }

    @Test
    void callSign() {
        Assertions.assertThat(faker.starWars().callSign()).isNotEmpty().matches("\\w+\\s(Leader|\\d)$");
    }

    @Test
    void species() {
        Assertions.assertThat(faker.starWars().species()).isNotEmpty();
    }

    @Test
    void planets() {
        Assertions.assertThat(faker.starWars().planets()).isNotEmpty();
    }

    @Test
    void droid() {
        Assertions.assertThat(faker.starWars().droids()).isNotEmpty();
    }

    @Test
    void wookieWords() {
        Assertions.assertThat(faker.starWars().wookieWords()).isNotEmpty();
    }

    @Test
    void vehicles() {
        Assertions.assertThat(faker.starWars().vehicles()).isNotEmpty();
    }

    @Test
    void alternativeSpelling() {
        Assertions.assertThat(faker.starWars().alternateCharacterSpelling()).isNotEmpty();
    }
}
