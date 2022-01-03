package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

public class OverwatchTest extends AbstractFakerTest {

    @Test
    public void hero() {
        assertThat(faker.overwatch().hero(), matchesRegularExpression("^(\\w+\\.?\\s?)+$"));
    }

    @Test
    public void location() {
        assertThat(faker.overwatch().location(), matchesRegularExpression("^(.+'?:?\\s?)+$"));
    }

    @Test
    public void quote() {
        assertFalse(faker.overwatch().quote().isEmpty());
    }
}
