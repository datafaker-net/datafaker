package com.github.javafaker;

import org.junit.Test;

import static com.github.javafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class ApplianceTest extends AbstractFakerTest {

    @Test
    public void brand() {
        assertThat(faker.appliance().brand(), matchesRegularExpression("[A-Za-z .-]+"));
    }

    @Test
    public void equipment() {
        assertThat(faker.appliance().equipment(), not(is(emptyOrNullString())));
    }


}