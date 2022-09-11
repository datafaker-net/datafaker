package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StarWarsTest extends MovieFakerTest {

    @Test
    void character() {
        assertThat(faker.starWars().character()).isNotEmpty();
    }

    @Test
    void callSign() {
        assertThat(faker.starWars().callSign()).isNotEmpty().matches("\\w+\\s(Leader|\\d)$");
    }

    @Test
    void species() {
        assertThat(faker.starWars().species()).isNotEmpty();
    }

    @Test
    void planets() {
        assertThat(faker.starWars().planets()).isNotEmpty();
    }

    @Test
    void droid() {
        assertThat(faker.starWars().droids()).isNotEmpty();
    }

    @Test
    void wookieWords() {
        assertThat(faker.starWars().wookieWords()).isNotEmpty();
    }

    @Test
    void vehicles() {
        assertThat(faker.starWars().vehicles()).isNotEmpty();
    }

    @Test
    void alternativeSpelling() {
        assertThat(faker.starWars().alternateCharacterSpelling()).isNotEmpty();
    }
}
