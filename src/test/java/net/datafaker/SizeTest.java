package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class SizeTest extends AbstractFakerTest {

    @Test
    public void adjective() {
        assertThat(faker.size().adjective(), matchesRegularExpression("[a-zA-Z]+(-[a-zA-Z]+)?"));
    }

}
