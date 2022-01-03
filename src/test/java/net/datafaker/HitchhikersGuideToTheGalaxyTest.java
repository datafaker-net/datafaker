package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

public class HitchhikersGuideToTheGalaxyTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().character(), matchesRegularExpression("^(\\w+(\\.?\\s?'?))+$"));
    }

    @Test
    public void location() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().location(), matchesRegularExpression("^(\\w+\\S?\\.?\\s?'?-?)+$"));
    }

    @Test
    public void marvinQuote() {
        assertFalse(faker.hitchhikersGuideToTheGalaxy().marvinQuote().isEmpty());
    }

    @Test
    public void planet() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().planet(), matchesRegularExpression("^(\\w+-?\\s?)+$"));
    }

    @Test
    public void quote() {
        assertFalse(faker.hitchhikersGuideToTheGalaxy().quote().isEmpty());
    }

    @Test
    public void species() {
        assertThat(faker.hitchhikersGuideToTheGalaxy().species(), matchesRegularExpression("^(\\w+'?\\s?)+$"));
    }
}
