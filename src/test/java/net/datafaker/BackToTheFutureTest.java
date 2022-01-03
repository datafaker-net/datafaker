package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.IsStringWithContents.isStringWithContents;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class BackToTheFutureTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.backToTheFuture().character(), isStringWithContents());
    }

    @Test
    public void date() {
        assertThat(faker.backToTheFuture().date(), matchesRegularExpression("([A-za-z]{3,8}) ([1-9]|[1-2]\\d|3[0-1]), (18[8-9]\\d|19[0-9]\\d|200\\d|201[0-5])"));
    }

    @Test
    public void quote() {
        assertThat(faker.backToTheFuture().quote(), isStringWithContents());
    }
}
