package com.github.javafaker;

import com.github.javafaker.repeating.Repeat;
import org.junit.Test;

import static com.github.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class BreakingBadTest extends AbstractFakerTest {

    @Test
    @Repeat(times = 10)
    public void character() {
        assertThat(faker.breakingBad().character(), matchesRegularExpression("[\\p{L}A-Za-z0-9 .\\-;']+"));
    }

    @Test
    @Repeat(times = 10)
    public void episodes() {
        assertThat(faker.breakingBad().episode(), not(is(emptyOrNullString())));
    }
}