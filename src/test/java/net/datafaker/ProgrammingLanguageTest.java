package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProgrammingLanguageTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.programmingLanguage().name(), matchesRegularExpression("[A-Za-z0-9 :,.+*()#/\\–\\-@πéöü'′!]+"));
    }

    @Test
    public void creator() {
        assertThat(faker.programmingLanguage().creator(), matchesRegularExpression("[A-Za-z .,\\-]+"));
    }
}
