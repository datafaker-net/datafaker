package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class StarTrekTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.starTrek().character(), matchesRegularExpression("^(\\w+-?'?\\.?\\s?)+$"));
    }

    @Test
    public void location() {
        assertThat(faker.starTrek().location(), matchesRegularExpression("^(\\w+'?\\s?)+$"));
    }

    @Test
    public void species() {
        assertThat(faker.starTrek().species(), matchesRegularExpression("^(\\w+-?'?\\s?)+$"));
    }

    @Test
    public void villain() {
        assertThat(faker.starTrek().villain(), matchesRegularExpression("^(\\w+'?\\.?\\s?)+$"));
    }

    @Test
    public void klingon() { assertThat(faker.starTrek().klingon(), not(isEmptyOrNullString())); }
}
