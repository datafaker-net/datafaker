package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class TouhouTest extends AbstractFakerTest {

    @RepeatedTest(100)
    public void testCharacterName() {
        assertThat(faker.touhou().characterName(), matchesRegularExpression("[a-zA-Z0-9 \\-']+"));
    }

    @RepeatedTest(100)
    public void testCharacterFirstName() {
        assertThat(faker.touhou().characterFirstName(), matchesRegularExpression("[a-zA-Z0-9 \\-']+"));
    }

    @RepeatedTest(100)
    public void testCharacterLastName() {
        assertThat(faker.touhou().characterLastName(), matchesRegularExpression("[a-zA-Z0-9 \\-']+"));
    }

    @RepeatedTest(100)
    public void testTrackName() {
        assertThat(faker.touhou().trackName(), matchesRegularExpression(".+"));
    }

    @RepeatedTest(100)
    public void testGameName() {
        String s = faker.touhou().gameName();
        assertThat(s, s, matchesRegularExpression("[a-zA-Z0-9 \\-'\\.]+"));
    }

}
