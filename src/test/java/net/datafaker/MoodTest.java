package net.datafaker;

import org.junit.Test;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoodTest extends AbstractFakerTest {

    @Test
    public void feeling() {
        assertThat(faker.mood().feeling(), matchesRegularExpression("[a-zA-Z]+"));
    }

    @Test
    public void emotion() {
        assertThat(faker.mood().emotion(), matchesRegularExpression("[a-zA-Z]+"));
    }

    @Test
    public void tone() {
        assertThat(faker.mood().tone(), matchesRegularExpression("[a-zA-Z]+"));
    }

}
