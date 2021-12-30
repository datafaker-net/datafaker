package com.github.javafaker;

import com.github.javafaker.repeating.Repeat;
import org.junit.Test;

import static com.github.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class BossaNovaTest extends AbstractFakerTest {

    @Test
    @Repeat(times = 10)
    public void artists() {
        assertThat(faker.bossaNova().artist(), matchesRegularExpression("[A-Za-z .-]+"));
    }

    @Test
    @Repeat(times = 10)
    public void songs() {
        assertThat(faker.bossaNova().song(), not(is(emptyOrNullString())));
    }
}