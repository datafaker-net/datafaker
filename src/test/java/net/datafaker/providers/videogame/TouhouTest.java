package net.datafaker.providers.videogame;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;


class TouhouTest extends VideoGameFakerTest {

    @RepeatedTest(100)
    void testCharacterName() {
        assertThat(faker.touhou().characterName()).matches("[a-zA-Z0-9 \\-']+");
    }

    @RepeatedTest(100)
    void testCharacterFirstName() {
        assertThat(faker.touhou().characterFirstName()).matches("[a-zA-Z0-9 \\-']+");
    }

    @RepeatedTest(100)
    void testCharacterLastName() {
        assertThat(faker.touhou().characterLastName()).matches("[a-zA-Z0-9 \\-']+");
    }

    @RepeatedTest(100)
    void testTrackName() {
        assertThat(faker.touhou().trackName()).matches(".+");
    }

    @RepeatedTest(100)
    void testGameName() {
        String s = faker.touhou().gameName();
        assertThat(s).matches("[a-zA-Z0-9 \\-'.]+");
    }

}
