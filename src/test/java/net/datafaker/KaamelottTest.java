package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class KaamelottTest extends AbstractFakerTest {

    @Test
    public void testCharacter() {
        assertThat(faker.kaamelott().character(), matchesRegularExpression("[A-Za-z' -ÇÉàçêèéïîüùú]+"));
    }

    @Test
    public void testQuote() {
        assertThat(faker.kaamelott().quote(), matchesRegularExpression("[-A-Za-z0-9 —ÇÉàçêèéïîüùú;:…\\?\\!\\.’‘'”“,\\[\\]]+"));
    }
}
