package net.datafaker.matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IsStringWithContentsTest {

    private IsStringWithContents matcher;

    @BeforeEach
    public void before() {
        matcher = new IsStringWithContents();
    }

    @Test
    public void emptyStringShouldFail() {
        assertThat(matcher.matchesSafely(""), is(false));
    }

    @Test
    public void nullStringShouldFail() {
        assertThat(matcher.matchesSafely(null), is(false));
    }

    @Test
    public void stringShouldPass() {
        assertThat(matcher.matchesSafely("123"), is(true));
    }

    @Test
    public void whiteSpaceShouldFail() {
        assertThat(matcher.matchesSafely("    "), is(false));
    }
}
