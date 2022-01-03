package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class NationTest extends AbstractFakerTest {

    @Test
    public void nationality() {
        assertThat(faker.nation().nationality(), matchesRegularExpression("\\P{Cc}+"));
    }

    @Test
    public void language() {
        assertThat(faker.nation().language(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @Test
    public void capitalCity() {
        assertThat(faker.nation().capitalCity(), matchesRegularExpression("[A-Za-z .'()-]+"));
    }

    @Test
    public void flag() {
        String flag = faker.nation().flag();

        // all utf8 emoji flags are at least 4 characters long and start with the same char
        assertThat(flag.length(), is(greaterThanOrEqualTo(4)));
        assertThat(flag.charAt(0), is('\uD83C'));
    }

}
