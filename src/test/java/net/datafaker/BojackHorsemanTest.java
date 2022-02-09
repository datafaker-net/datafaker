package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;


public class BojackHorsemanTest extends AbstractFakerTest {

    @Test
    public void testCharacters1() {
        assertThat(faker.bojackHorseman().characters(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testQuotes1() {
        assertFalse(faker.bojackHorseman().quotes().isEmpty());
    }

    @Test
    public void testTongueTwisters1() {
        assertFalse(faker.bojackHorseman().tongueTwisters().isEmpty());
    }
}