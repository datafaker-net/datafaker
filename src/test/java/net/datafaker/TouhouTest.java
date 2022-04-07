package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;


public class TouhouTest extends AbstractFakerTest {

    @RepeatedTest(100)
    public void testCharacterName() {
        assertThat(faker.touhou().characterName()).matches("[a-zA-Z0-9 \\-']+");
    }

    @RepeatedTest(100)
    public void testCharacterFirstName() {
        assertThat(faker.touhou().characterFirstName()).matches("[a-zA-Z0-9 \\-']+");
    }

    @RepeatedTest(100)
    public void testCharacterLastName() {
        assertThat(faker.touhou().characterLastName()).matches("[a-zA-Z0-9 \\-']+");
    }

    @RepeatedTest(100)
    public void testTrackName() {
        assertThat(faker.touhou().trackName()).matches(".+");
    }

    @RepeatedTest(100)
    public void testGameName() {
        String s = faker.touhou().gameName();
        assertThat(s).matches("[a-zA-Z0-9 \\-'\\.]+");
    }

}
