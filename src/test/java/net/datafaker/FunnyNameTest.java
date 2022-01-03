package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class FunnyNameTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.funnyName().name(), matchesRegularExpression("^(\\w+\\.?\\s?'?-?)+$"));
    }
}
