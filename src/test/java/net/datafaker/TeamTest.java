package net.datafaker;

import org.junit.Test;

import java.util.Locale;

import static net.datafaker.matchers.IsStringWithContents.isStringWithContents;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.team().name(), matchesRegularExpression("(\\w+( )?){2,4}"));
    }

    @Test
    public void testCreature() {
        assertThat(faker.team().creature(), matchesRegularExpression("\\w+( \\w+)?"));
    }

    @Test
    public void testState() {
        assertThat(faker.team().state(), matchesRegularExpression("(\\w+( )?){1,2}"));
    }


    @Test
    public void testStateWithZaLocale() {
        Faker zaFaker = new Faker(new Locale("en-ZA"));
        assertThat(zaFaker.team().state(), isStringWithContents());
    }

    @Test
    public void testSport() {
        assertThat(faker.team().sport(), matchesRegularExpression("(\\p{L}|\\s)+"));
    }


}
