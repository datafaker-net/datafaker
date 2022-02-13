package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class TouhouTest extends AbstractFakerTest {

    @Test
    public void testCharacterName() {
        assertThat(faker.touhou().characterName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testCharacterFirstName() {
        assertThat(faker.touhou().characterFirstName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testCharacterLastName() {
        assertThat(faker.touhou().characterLastName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testTrackName() {
        assertThat(faker.touhou().trackName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

    @Test
    public void testGameName() {
        assertThat(faker.touhou().gameName(), matchesRegularExpression("([\\w']+\\.?( )?){2,4}"));
    }

}
